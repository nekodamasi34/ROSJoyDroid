package jp.eyrin.rosjoydroid.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.eyrin.rosjoydroid.activity.MainActivity
import jp.eyrin.rosjoydroid.model.ScreenMode
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * ui/screens/MainScreen.kt
 *
 * This file defines the main composable screen that manages navigation between all primary panes (Status, Manual, Extra).
 *
 * Features:
 *  - Hosts the top app bar, navigation tabs, and dynamically displays each pane based on current screen mode
 *  - Handles communication and state sync with MainActivity, including extra axes/buttons and manual controls
 *  - Integrates settings navigation via the top bar
 *  - Central hub for switching between status display, manual control, and extra controls within the app UI
 */

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
        ga.currentAxes = if (mode == ScreenMode.MANUAL) ga.manualAxes.value else ga.axes.value
        ga.currentButtons =
            if (mode == ScreenMode.MANUAL) ga.manualButtons.value else ga.buttons.value
        ga.extraEnabled = extraEnabled
    }
}
