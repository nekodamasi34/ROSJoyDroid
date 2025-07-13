package jp.eyrin.rosjoydroid.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.eyrin.rosjoydroid.model.GamepadButton
import jp.eyrin.rosjoydroid.model.GamepadSettings
import jp.eyrin.rosjoydroid.model.loadFromPrefs
import jp.eyrin.rosjoydroid.model.rememberGamepadActivityState
import jp.eyrin.rosjoydroid.model.saveToPrefs
import jp.eyrin.rosjoydroid.ui.screens.MainScreen
import jp.eyrin.rosjoydroid.ui.screens.SettingsScreen
import jp.eyrin.rosjoydroid.ui.theme.ROSJoyDroidTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

/*
 * MainActivity – クリーンリファクタ版
 *  - 3 タブ (Status / Manual / Extra)
 *  - Extra は ON/OFF スイッチ付き
 *  - publishJoy は currentAxes / currentButtons (+ extra*) を送信
 */

class MainActivity : GamepadActivity() {
    /* 追加入力 */
    val extraAxes = MutableStateFlow(FloatArray(4))
    val extraButtons = MutableStateFlow(IntArray(8))

    val manualAxes = mutableStateOf(FloatArray(6))
    val manualButtons = mutableStateOf(IntArray(GamepadButton.entries.size))

    /* Publish バッファ */
    var currentAxes: FloatArray = FloatArray(6)
    var currentButtons: IntArray = IntArray(GamepadButton.entries.size)
    var extraEnabled: Boolean = true

    /* 内部 */
    private lateinit var publishTimer: Timer
    private var axesJob: Job? = null
    private var btnJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getPreferences(Context.MODE_PRIVATE)

        setContent {
            val nav = rememberNavController()
            val settings = remember { mutableStateOf(GamepadSettings.loadFromPrefs(prefs)) }
            val gaState = rememberGamepadActivityState(this)

            // 設定を適用 & 保存
            LaunchedEffect(settings.value) {
                with(settings.value) {
                    super@MainActivity.deadZone = deadZone
                    super@MainActivity.invertLeftX = invertLeftX
                    super@MainActivity.invertLeftY = invertLeftY
                    super@MainActivity.invertRightX = invertRightX
                    super@MainActivity.invertRightY = invertRightY
                    super@MainActivity.invertLeftTrigger = invertLeftTrigger
                    super@MainActivity.invertRightTrigger = invertRightTrigger
                }
                settings.value.saveToPrefs(prefs)
            }

            ROSJoyDroidTheme {
                NavHost(nav, startDestination = "main") {
                    composable("main") {
                        MainScreen(
//                            ga = gaState,
                            ga = this@MainActivity,
                            extraAxes = extraAxes,
                            extraButtons = extraButtons,
                            extraEnabled = extraEnabled,
                            onEnabledChange = { extraEnabled = it },
                            onSettingsClick = { nav.navigate("settings") }
                        )

                        DisposableEffect(settings.value) {
                            startPublishLoop(settings.value)
                            onDispose { stopPublishLoop() }
                        }
                    }
                    composable("settings") {
                        SettingsScreen(
                            navController = nav,
                            settings = settings.value,
                            onSettingsChange = { settings.value = it }
                        )
                    }
                }
            }
        }
    }

    /* ---------------- Publish Loop ---------------- */
    private fun startPublishLoop(cfg: GamepadSettings) {
        stopPublishLoop()
        createJoyPublisher(cfg.domainId, cfg.namespace)

        axesJob = lifecycleScope.launch { axes.collect { currentAxes = it } }
        btnJob = lifecycleScope.launch { buttons.collect { currentButtons = it } }

        publishTimer = Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    val axesOut = if (extraEnabled) currentAxes + extraAxes.value else currentAxes
                    val btnOut =
                        if (extraEnabled) currentButtons + extraButtons.value else currentButtons
                    publishJoy(axesOut, btnOut)
                }
            }, 0, cfg.period)
        }
    }

    private fun stopPublishLoop() {
        runCatching { if (::publishTimer.isInitialized) publishTimer.cancel() }
        axesJob?.cancel(); btnJob?.cancel()
        destroyJoyPublisher()
    }

    /* JNI */
    private external fun createJoyPublisher(domainId: Int, ns: String)
    private external fun destroyJoyPublisher()
    private external fun publishJoy(axes: FloatArray, buttons: IntArray)

    companion object { init {
        System.loadLibrary("rosjoydroid")
    }
    }
}


