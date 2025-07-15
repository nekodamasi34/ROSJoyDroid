package jp.eyrin.rosjoydroid.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * ui/components/TriggerInvertSetting.kt
 *
 * This file provides a composable UI component to configure inversion settings for gamepad triggers (L2 and R2).
 *
 * Features:
 *  - Displays checkboxes to toggle inversion for left and right triggers
 *  - Integrates into broader gamepad settings screens for flexible input configuration
 */

@Composable
fun TriggerInvertSetting(
    label: String,
    invertL2: Boolean,
    invertR2: Boolean,
    onInvertL2Change: (Boolean) -> Unit,
    onInvertR2Change: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = invertL2, onCheckedChange = onInvertL2Change)
            Text("L2", modifier = Modifier.padding(end = 8.dp))
            Checkbox(checked = invertR2, onCheckedChange = onInvertR2Change)
            Text("R2")
        }
    }
}
