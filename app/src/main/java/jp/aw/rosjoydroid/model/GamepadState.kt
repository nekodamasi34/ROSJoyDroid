package jp.aw.rosjoydroid.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import jp.aw.rosjoydroid.activity.GamepadActivity

/**
 * model/GamepadState.kt
 *
 * This file provides a helper composable function to create and retain the state of a GamepadActivity within Compose.
 *
 * Features:
 *  - Defines rememberGamepadActivityState(), which ensures the same GamepadActivity instance is used across recompositions
 *  - Enables stable state management for activity-scoped gamepad logic within Compose UIs
 *
 * Intended for use in Compose screens that interact with GamepadActivity properties or behaviors.
 */

@Composable
fun rememberGamepadActivityState(gamepadActivity: GamepadActivity): GamepadActivity {
    return remember { gamepadActivity }
}