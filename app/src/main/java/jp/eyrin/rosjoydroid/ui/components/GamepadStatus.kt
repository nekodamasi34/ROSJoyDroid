package jp.eyrin.rosjoydroid.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.eyrin.rosjoydroid.activity.GamepadActivity

@Composable
fun GamepadStatus(
    gamepadActivity: GamepadActivity,
    modifier: Modifier = Modifier,
    showAnalogInputs: Boolean = true,
    showButtons: Boolean = true
) {
    val axes = gamepadActivity.axes.collectAsState()
    val buttons = gamepadActivity.buttons.collectAsState()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (showAnalogInputs) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Analog Inputs",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StickVisualizer(
                            label = "Left Stick",
                            x = axes.value[0],
                            y = axes.value[1]
                        )
                        StickVisualizer(
                            label = "Right Stick",
                            x = axes.value[2],
                            y = axes.value[3]
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        VerticalTriggerVisualizer(
                            label = "Left Trigger",
                            value = axes.value[4]
                        )
                        VerticalTriggerVisualizer(
                            label = "Right Trigger",
                            value = axes.value[5]
                        )
                    }
                }
            }
        }

        if (showButtons) {
            GamepadButtonsDisplay(
                buttons = buttons,
                showButtons = showButtons,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
    }
}