package jp.eyrin.rosjoydroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.eyrin.rosjoydroid.activity.GamepadActivity
import jp.eyrin.rosjoydroid.ui.components.GamepadStatus

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
