package jp.eyrin.rosjoydroid.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import jp.eyrin.rosjoydroid.ui.components.ExtraControls

/**
 * ui/screens/ExtraPane.kt
 *
 * This file provides a composable pane for configuring and monitoring extra gamepad axes and buttons.
 *
 * Features:
 *  - Displays a toggle switch to enable or disable extra controls
 *  - Integrates the ExtraControls component for direct manipulation of additional axes and buttons
 *  - Intended for scenarios where extended controller functionality or custom input mapping is required
 *  - Designed to fit seamlessly within a multi-pane Compose UI
 */

@Composable
fun ExtraPane(
    axes: MutableStateFlow<FloatArray>,
    btns: MutableStateFlow<IntArray>,
    enabled: Boolean,
    baseAxisOffset: Int,
    baseBtnOffset: Int,
    onEnabledChange: (Boolean) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("Extra:")
            Switch(enabled, onEnabledChange, Modifier.padding(start = 8.dp))
        }
        ExtraControls(
            axesState = axes,
            btnState = btns,
            baseAxisOffset = baseAxisOffset,
            baseButtonOffset = baseBtnOffset,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}
