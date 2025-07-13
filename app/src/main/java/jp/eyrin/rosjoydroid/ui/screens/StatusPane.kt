package jp.eyrin.rosjoydroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.eyrin.rosjoydroid.activity.GamepadActivity
import jp.eyrin.rosjoydroid.ui.components.GamepadStatus

/**
 * ui/screens/StatusPane.kt
 *
 * This file defines a composable pane for displaying the current status of all gamepad inputs.
 *
 * Features:
 *  - Shows analog inputs and button states in separate columns for clarity
 *  - Integrates the GamepadStatus component for detailed input visualization
 *  - Designed as the main status overview within the app UI
 */

@Composable
fun StatusPane(ga: GamepadActivity) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        GamepadStatus(
            ga, showAnalogInputs = true, showButtons = false,
            modifier = Modifier.weight(1f)
        )
        GamepadStatus(
            ga, showAnalogInputs = false, showButtons = true,
            modifier = Modifier.weight(1f)
        )
    }
}
