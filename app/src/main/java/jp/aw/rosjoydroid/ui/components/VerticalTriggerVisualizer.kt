package jp.aw.rosjoydroid.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * ui/components/VerticalTriggerVisualizer.kt
 *
 * This file defines a composable component that visually displays the current value of a trigger input in a vertical bar format.
 *
 * Features:
 *  - Draws a bar to indicate the trigger's position in real time
 *  - Shows the numeric value alongside the visualization
 *  - Useful for monitoring and debugging analog trigger inputs
 */

@SuppressLint("DefaultLocale")
@Composable
fun VerticalTriggerVisualizer(
    label: String,
    value: Float,
    modifier: Modifier = Modifier
) {

    val primaryColor = MaterialTheme.colorScheme.primary

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = label)
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(80.dp)
                .border(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Background
                drawRect(
                    color = Color.Gray.copy(alpha = 0.3f),
                    topLeft = Offset(0f, 0f),
                    size = Size(size.width, size.height)
                )

                // Value bar
                val barHeight = size.height * value
                drawRect(
                    color = primaryColor,
                    topLeft = Offset(0f, size.height - barHeight),
                    size = Size(size.width, barHeight)
                )
            }
        }
        Row(
            modifier = Modifier.padding(start = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Value: ${value.format(2)}")
        }

    }
}