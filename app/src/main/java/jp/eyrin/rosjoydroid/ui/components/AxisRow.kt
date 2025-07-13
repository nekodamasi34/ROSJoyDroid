package jp.eyrin.rosjoydroid.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign

/**
 * ui/components/AxisRow.kt
 *
 * This file defines a composable UI component for displaying and adjusting a single axis value using a slider.
 *
 * Features:
 *  - Shows the axis index, label, and current value
 *  - Provides an interactive slider to modify the axis value in real-time
 *  - Intended for manual control, calibration, or diagnostics screens
 */

@SuppressLint("DefaultLocale")
@Composable
fun AxisRow(index: Int, name: String, value: Float, onChange: (Float) -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text("$index: $name", Modifier.width(70.dp), style = MaterialTheme.typography.bodySmall)
        Slider(
            value, onChange, valueRange = -1f..1f, modifier = Modifier
                .weight(1f)
                .height(24.dp)
        )
        Text(
            String.format("%.2f", value),
            Modifier
                .width(48.dp)
                .padding(start = 4.dp),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.End
        )
    }
}
