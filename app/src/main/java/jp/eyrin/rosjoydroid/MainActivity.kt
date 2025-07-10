package jp.eyrin.rosjoydroid

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.eyrin.rosjoydroid.ui.theme.ROSJoyDroidTheme
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : GamepadActivity() {
    private lateinit var publishJoyTimer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getPreferences(Context.MODE_PRIVATE)

        setContent {
            val navController = rememberNavController()
            val settings = rememberGamepadSettings(prefs)
            val gamepadActivityState = rememberGamepadActivityState(this)

            // Apply settings to GamepadActivity
            LaunchedEffect(settings.value) {
                with(settings.value) {
                    super.deadZone = deadZone
                    super.invertLeftX = invertLeftX
                    super.invertLeftY = invertLeftY
                    super.invertRightX = invertRightX
                    super.invertRightY = invertRightY
                    super.invertLeftTrigger = invertLeftTrigger
                    super.invertRightTrigger = invertRightTrigger
                }
                // Save settings
                GamepadSettings.saveToPrefs(prefs, settings.value)
            }

            ROSJoyDroidTheme {
                NavHost(
                    navController = navController,
                    startDestination = "main"
                ) {
                    composable("main") {
                        MainScreen(
                            gamepadActivityState = gamepadActivityState,
                            onSettingsClick = { navController.navigate("settings") }
                        )

                        // Lifecycle management
                        val lifecycleOwner = LocalLifecycleOwner.current
                        DisposableEffect(lifecycleOwner, settings.value) {
                            with(settings.value) {
                                startPublishJoy(domainId, namespace, period)
                            }
                            onDispose {
                                stopPublishJoy()
                            }
                        }
                    }

                    composable("settings") {
                        SettingsScreen(
                            navController = navController,
                            settings = settings.value,
                            onSettingsChange = { newSettings ->
                                settings.value = newSettings
                            }
                        )
                    }
                }
            }
        }
    }

    private var currentAxes: FloatArray = FloatArray(0)
    private var currentButtons: IntArray = IntArray(0)

    private fun startPublishJoy(domainId: Int, ns: String, period: Long) {
        stopPublishJoy()
        createJoyPublisher(domainId, ns)

        // StateFlowの監視を設定
        lifecycleScope.launch {
            axes.collect { newAxes ->
                currentAxes = newAxes
            }
        }

        lifecycleScope.launch {
            buttons.collect { newButtons ->
                currentButtons = newButtons
            }
        }

        publishJoyTimer = Timer().also {
            it.schedule(object : TimerTask() {
                override fun run() {
                    // 現在の値を使用してpublish
                    publishJoy(currentAxes, currentButtons)
                }
            }, 0, period)
        }
    }

    private fun stopPublishJoy() {
        runCatching {
            if (::publishJoyTimer.isInitialized) {
                publishJoyTimer.cancel()
            }
        }
        destroyJoyPublisher()
    }

    private external fun createJoyPublisher(domainId: Int, ns: String)
    private external fun destroyJoyPublisher()
    private external fun publishJoy(axes: FloatArray, buttons: IntArray)  // StateFlowを直接渡さないように変更


    companion object {
        init {
            System.loadLibrary("rosjoydroid")
        }
    }
}

@Composable
private fun MainScreen(
    gamepadActivityState: GamepadActivity,
    onSettingsClick: () -> Unit
) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("ROSJoyDroid") },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Default.Settings, "Settings")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                // 左側のカラム（アナログ入力）
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    GamepadStatus(
                        gamepadActivity = gamepadActivityState,
                        showAnalogInputs = true,
                        showButtons = false
                    )
                }

                // 右側のカラム（ボタン表示）
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    GamepadStatus(
                        gamepadActivity = gamepadActivityState,
                        showAnalogInputs = false,
                        showButtons = true
                    )
                }
            }
        }
    }
}

