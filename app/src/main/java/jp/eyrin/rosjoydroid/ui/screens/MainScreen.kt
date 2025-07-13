package jp.eyrin.rosjoydroid.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.eyrin.rosjoydroid.model.ScreenMode
import jp.eyrin.rosjoydroid.activity.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    ga: MainActivity,
    extraAxes: MutableStateFlow<FloatArray>,
    extraButtons: MutableStateFlow<IntArray>,
    extraEnabled: Boolean,
    onEnabledChange: (Boolean) -> Unit,
    onSettingsClick: () -> Unit
) {
    var mode by remember { mutableStateOf(ScreenMode.STATUS) }

//    val manualAxes    = remember { mutableStateOf(FloatArray(6)) }
//    val manualButtons = remember { mutableStateOf(IntArray(GamepadButton.entries.size)) }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text("ROSJoyDroid v2.0") },
            modifier = Modifier.height(40.dp),
            actions = {
                IconButton(onClick = onSettingsClick) {
                    Icon(
                        Icons.Default.Settings, null
                    )
                }
            })
    }) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(
                selectedTabIndex = mode.ordinal, modifier = Modifier.height(50.dp)
            ) {
                ScreenMode.entries.forEach { t ->
                    Tab(
                        selected = mode == t,
                        onClick = { mode = t },
                        modifier = Modifier
                            .height(48.dp)
                            .padding(vertical = 4.dp)
                    ) {
                        Text(t.label)
                    }
                }
            }
            Box(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when (mode) {
                    ScreenMode.STATUS -> StatusPane(ga)
                    ScreenMode.MANUAL -> ManualPane(ga.manualAxes, ga.manualButtons)
                    ScreenMode.EXTRA -> ExtraPane(
                        axes = extraAxes,
                        btns = extraButtons,
                        enabled = extraEnabled,
                        baseAxisOffset = ga.axes.value.size,
                        baseBtnOffset = ga.buttons.value.size,
                        onEnabledChange = onEnabledChange
                    )
                }
            }
        }
    }

    // LaunchedEffect for syncing ga.currentAxes/currentButtons + extraEnabled…
    /* Sync to MainActivity */
    LaunchedEffect(mode, ga.manualAxes.value, ga.manualButtons.value, extraEnabled) {
        if (ga is MainActivity) {
            ga.currentAxes = if (mode == ScreenMode.MANUAL) ga.manualAxes.value else ga.axes.value
            ga.currentButtons =
                if (mode == ScreenMode.MANUAL) ga.manualButtons.value else ga.buttons.value
            ga.extraEnabled = extraEnabled
        }
    }
}
