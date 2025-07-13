package jp.eyrin.rosjoydroid.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * ui/components/AxisInvertSetting.kt
 *
 * This file provides a composable UI component for configuring axis inversion settings (X and Y) for a gamepad.
 *
 * Features:
 *  - Displays checkboxes to toggle inversion for X and Y axes
 *  - Allows users to adjust input direction settings per axis
 *  - Designed for integration into settings or configuration screens
 */

@Composable
fun AxisInvertSetting(
    label: String,
    invertX: Boolean,
    invertY: Boolean,
    onInvertXChange: (Boolean) -> Unit,
    onInvertYChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = invertX, onCheckedChange = onInvertXChange)
            Text("X", modifier = Modifier.padding(end = 8.dp))
            Checkbox(checked = invertY, onCheckedChange = onInvertYChange)
            Text("Y")
        }
    }
}
