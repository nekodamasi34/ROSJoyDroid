// SettingsScreen.kt
package jp.eyrin.rosjoydroid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(
    navController: NavController,
    settings: GamepadSettings,
    onSettingsChange: (GamepadSettings) -> Unit
) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Connection Settings
            SettingsSection(title = "Connection Settings") {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = settings.namespace,
                    onValueChange = { newValue -> onSettingsChange(settings.copy(namespace = newValue)) },
                    label = { Text("Namespace") },
                    singleLine = true
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = settings.domainId.toString(),
                    onValueChange = {
                        val newValue = it.toIntOrNull() ?: 0
                        if (newValue in 0..101) {
                            onSettingsChange(settings.copy(domainId = newValue))
                        }
                    },
                    label = { Text("Domain ID (0-101)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // 修正部分
                    singleLine = true
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = settings.period.toString(),
                    onValueChange = {
                        val newValue = it.toLongOrNull() ?: 20L
                        if (newValue > 0) {
                            onSettingsChange(settings.copy(period = newValue))
                        }
                    },
                    label = { Text("Update Period (ms)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // 修正部分
                    singleLine = true
                )

            }

            // Input Settings
            SettingsSection(title = "Input Settings") {
                // Dead Zone Settings
                Text("Dead Zone", style = MaterialTheme.typography.labelLarge)
                Slider(
                    value = settings.deadZone,
                    onValueChange = { onSettingsChange(settings.copy(deadZone = it)) },
                    valueRange = 0f..1f,
                    steps = 99
                )
                Text(
                    "${(settings.deadZone * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall
                )

                // Axis Inversion Settings
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Left Stick", style = MaterialTheme.typography.labelLarge)
                        Row {
                            Checkbox(
                                checked = settings.invertLeftX,
                                onCheckedChange = { onSettingsChange(settings.copy(invertLeftX = it)) }
                            )
                            Text("Invert X")
                        }
                        Row {
                            Checkbox(
                                checked = settings.invertLeftY,
                                onCheckedChange = { onSettingsChange(settings.copy(invertLeftY = it)) }
                            )
                            Text("Invert Y")
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Right Stick", style = MaterialTheme.typography.labelLarge)
                        Row {
                            Checkbox(
                                checked = settings.invertRightX,
                                onCheckedChange = { onSettingsChange(settings.copy(invertRightX = it)) }
                            )
                            Text("Invert X")
                        }
                        Row {
                            Checkbox(
                                checked = settings.invertRightY,
                                onCheckedChange = { onSettingsChange(settings.copy(invertRightY = it)) }
                            )
                            Text("Invert Y")
                        }
                    }
                }

                // Trigger Settings
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Triggers", style = MaterialTheme.typography.labelLarge)
                        Row {
                            Checkbox(
                                checked = settings.invertLeftTrigger,
                                onCheckedChange = { onSettingsChange(settings.copy(invertLeftTrigger = it)) }
                            )
                            Text("Invert L2")
                        }
                        Row {
                            Checkbox(
                                checked = settings.invertRightTrigger,
                                onCheckedChange = { onSettingsChange(settings.copy(invertRightTrigger = it)) }
                            )
                            Text("Invert R2")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            content()
        }
    }
}