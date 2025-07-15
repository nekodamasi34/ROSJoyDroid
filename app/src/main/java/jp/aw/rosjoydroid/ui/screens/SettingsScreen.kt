package jp.aw.rosjoydroid.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import jp.aw.rosjoydroid.model.GamepadSettings
import jp.aw.rosjoydroid.ui.components.AxisInvertSetting
import jp.aw.rosjoydroid.ui.components.TriggerInvertSetting

/**
 * ui/screens/SettingsScreen.kt
 *
 * This file defines the settings screen for the app, providing user controls for ROS2 and input configuration.
 *
 * Features:
 *  - Allows adjustment of ROS2 connection settings (namespace, domain ID, update period)
 *  - Provides UI to configure dead zone, axis inversion, and trigger inversion
 *  - Organized into clear sections for connection and input settings
 *  - Integrates navigation with a top bar and back button for seamless UX
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    settings: GamepadSettings,
    onSettingsChange: (GamepadSettings) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Settings", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "戻る")
                    }
                }
            )
        }
    ) { padding ->
        Row(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
                .fillMaxHeight()
        ) {
            // 左
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                SettingsSection(title = "Connection settings (ROS2)", icon = Icons.Default.Send) {
                    OutlinedTextField(
                        value = settings.namespace,
                        onValueChange = { onSettingsChange(settings.copy(namespace = it)) },
                        label = { Text("Namespace") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = settings.domainId.toString(),
                        onValueChange = {
                            val id = it.toIntOrNull() ?: 0
                            if (id in 0..101) onSettingsChange(settings.copy(domainId = id))
                        },
                        label = { Text("Domain ID (0-101)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = settings.period.toString(),
                        onValueChange = {
                            val v = it.toLongOrNull() ?: 20L
                            if (v > 0) onSettingsChange(settings.copy(period = v))
                        },
                        label = { Text("Update Period (ms)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // 右
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                SettingsSection(title = "Input Settings", icon = Icons.Default.Build) {
                    Text("Dead Zone", style = MaterialTheme.typography.labelMedium)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Slider(
                            value = settings.deadZone,
                            onValueChange = { onSettingsChange(settings.copy(deadZone = it)) },
                            valueRange = 0f..1f,
                            steps = 99,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "${(settings.deadZone * 100).toInt()}%",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Text("Invert", style = MaterialTheme.typography.labelMedium)
                    AxisInvertSetting(
                        label = "Left",
                        invertX = settings.invertLeftX,
                        invertY = settings.invertLeftY,
                        onInvertXChange = { onSettingsChange(settings.copy(invertLeftX = it)) },
                        onInvertYChange = { onSettingsChange(settings.copy(invertLeftY = it)) }
                    )
                    AxisInvertSetting(
                        label = "Right",
                        invertX = settings.invertRightX,
                        invertY = settings.invertRightY,
                        onInvertXChange = { onSettingsChange(settings.copy(invertRightX = it)) },
                        onInvertYChange = { onSettingsChange(settings.copy(invertRightY = it)) }
                    )
                    TriggerInvertSetting(
                        label = "Trigger",
                        invertL2 = settings.invertLeftTrigger,
                        invertR2 = settings.invertRightTrigger,
                        onInvertL2Change = { onSettingsChange(settings.copy(invertLeftTrigger = it)) },
                        onInvertR2Change = { onSettingsChange(settings.copy(invertRightTrigger = it)) }
                    )
                }
            }
        }
    }
}
