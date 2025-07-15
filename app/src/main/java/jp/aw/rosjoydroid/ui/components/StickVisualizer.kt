package jp.aw.rosjoydroid.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.min

/**
 * ui/components/StickVisualizer.kt
 *
 * This file defines a composable component to visually represent the position of a gamepad analog stick on a 2D plane.
 *
 * Features:
 *  - Draws a unit circle with crosshairs to represent possible stick positions
 *  - Highlights current stick location and displays X/Y values numerically
 *  - Useful for manual operation, calibration, and diagnostics of analog sticks
 */

@Composable
fun StickVisualizer(
    label: String,
    x: Float,
    y: Float,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label)
        Box(
            modifier = Modifier
                .size(120.dp)
                .border(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = min(size.width, size.height) / 2

                // Draw unit circle
                drawCircle(
                    color = Color.Gray.copy(alpha = 0.3f),
                    radius = radius,
                    center = center,
                    style = Stroke(width = 1f)
                )

                // Draw crosshairs
                drawLine(
                    color = Color.Gray.copy(alpha = 0.3f),
                    start = Offset(center.x, 0f),
                    end = Offset(center.x, size.height)
                )
                drawLine(
                    color = Color.Gray.copy(alpha = 0.3f),
                    start = Offset(0f, center.y),
                    end = Offset(size.width, center.y)
                )

                // Draw stick position
                val stickX = center.x + (x * radius)
                val stickY = center.y + (y * radius)
                drawCircle(
                    color = primaryColor,
                    radius = 8f,
                    center = Offset(stickX, stickY)
                )
            }
        }
        Row(
            modifier = Modifier.padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("X: ${x.format(2)}")
            Text("Y: ${y.format(2)}")
        }
    }
}

fun Float.format(digits: Int) = "%.${digits}f".format(this)