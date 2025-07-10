package jp.eyrin.rosjoydroid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberGamepadActivityState(gamepadActivity: GamepadActivity): GamepadActivity {
    return remember { gamepadActivity }
}