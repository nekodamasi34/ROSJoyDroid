package jp.aw.rosjoydroid.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.aw.rosjoydroid.model.GamepadButton

/**
 * ui/components/ButtonVisualizer.kt
 *
 * This file provides composable UI components to visualize and display gamepad button states.
 *
 * Features:
 *  - Dynamically shows the status of each gamepad button (pressed/released)
 *  - Visualizes button layout including ABXY, DPAD, L1/R1, Start/Select, etc.
 *  - Useful for monitoring input status and debugging controller mappings
 */

@Composable
fun GamepadButtonsDisplay(
    buttons: State<IntArray>, showButtons: Boolean, modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = showButtons, enter = fadeIn(), exit = fadeOut()
    ) {
        Card(
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Button Inputs", style = MaterialTheme.typography.titleMedium
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .padding(8.dp)
                ) {
                    // L1/R1ボタン（上部）
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .align(Alignment.TopCenter),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // L1
                        FilledTonalButton(
                            onClick = { },
                            modifier = Modifier
                                .width(64.dp)
                                .height(32.dp),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = if (buttons.value[GamepadButton.L1.ordinal] == 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                "L1",
                                color = if (buttons.value[GamepadButton.L1.ordinal] == 1) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.secondary
                            )
                        }

                        // R1
                        FilledTonalButton(
                            onClick = { },
                            modifier = Modifier
                                .width(64.dp)
                                .height(32.dp),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = if (buttons.value[GamepadButton.R1.ordinal] == 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                "R1",
                                color = if (buttons.value[GamepadButton.R1.ordinal] == 1) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.secondary
                            )
                        }
                    }

                    // ABXYボタン群（右側）
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(120.dp)
                    ) {
                        // Yボタン（上）
                        FilledTonalButton(
                            onClick = { },
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.TopCenter),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = if (buttons.value[GamepadButton.Y.ordinal] == 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = CircleShape
                        ) {
                            Text(
                                "Y",
                                color = if (buttons.value[GamepadButton.Y.ordinal] == 1) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.secondary
                            )
                        }

                        // Xボタン（左）
                        FilledTonalButton(
                            onClick = { },
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.CenterStart),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = if (buttons.value[GamepadButton.X.ordinal] == 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = CircleShape
                        ) {
                            Text(
                                "X",
                                color = if (buttons.value[GamepadButton.X.ordinal] == 1) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.secondary
                            )
                        }

                        // Bボタン（右）
                        FilledTonalButton(
                            onClick = { },
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.CenterEnd),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = if (buttons.value[GamepadButton.B.ordinal] == 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = CircleShape
                        ) {
                            Text(
                                "B",
                                color = if (buttons.value[GamepadButton.B.ordinal] == 1) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.secondary
                            )
                        }

                        // Aボタン（下）
                        FilledTonalButton(
                            onClick = { },
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.BottomCenter),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = if (buttons.value[GamepadButton.A.ordinal] == 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = CircleShape
                        ) {
                            Text(
                                "A",
                                color = if (buttons.value[GamepadButton.A.ordinal] == 1) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.secondary
                            )
                        }
                    }

                    // 方向パッド（左側）
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(120.dp)
                    ) {
                        // 上
                        FilledTonalButton(
                            onClick = { },
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.TopCenter),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = if (buttons.value[GamepadButton.DPAD_UP.ordinal] == 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = CircleShape
                        ) {
                            Text(
                                "↑",
                                color = if (buttons.value[GamepadButton.DPAD_UP.ordinal] == 1) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.secondary
                            )
                        }

                        // 左
                        FilledTonalButton(
                            onClick = { },
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.CenterStart),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = if (buttons.value[GamepadButton.DPAD_LEFT.ordinal] == 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = CircleShape
                        ) {
                            Text(
                                "←",
                                color = if (buttons.value[GamepadButton.DPAD_LEFT.ordinal] == 1) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.secondary
                            )
                        }

                        // 右
                        FilledTonalButton(
                            onClick = { },
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.CenterEnd),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = if (buttons.value[GamepadButton.DPAD_RIGHT.ordinal] == 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = CircleShape
                        ) {
                            Text(
                                "→",
                                color = if (buttons.value[GamepadButton.DPAD_RIGHT.ordinal] == 1) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.secondary
                            )
                        }

                        // 下
                        FilledTonalButton(
                            onClick = { },
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.BottomCenter),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = if (buttons.value[GamepadButton.DPAD_DOWN.ordinal] == 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = CircleShape
                        ) {
                            Text(
                                "↓",
                                color = if (buttons.value[GamepadButton.DPAD_DOWN.ordinal] == 1) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.secondary
                            )
                        }
                    }

                    // スタート/セレクトボタン（中央）
                    Row(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        FilledTonalButton(
                            onClick = { },
                            modifier = Modifier.size(32.dp),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = if (buttons.value[GamepadButton.BACK.ordinal] == 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = "Select",
                                modifier = Modifier.size(16.dp),
                                tint = if (buttons.value[GamepadButton.BACK.ordinal] == 1) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.secondary
                            )
                        }

                        FilledTonalButton(
                            onClick = { },
                            modifier = Modifier.size(32.dp),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = if (buttons.value[GamepadButton.START.ordinal] == 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PlayArrow,
                                contentDescription = "Start",
                                modifier = Modifier.size(16.dp),
                                tint = if (buttons.value[GamepadButton.START.ordinal] == 1) MaterialTheme.colorScheme.onPrimary
                                else MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
                }
            }
        }
    }
}