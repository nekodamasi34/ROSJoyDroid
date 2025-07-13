package jp.eyrin.rosjoydroid.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import jp.eyrin.rosjoydroid.activity.GamepadActivity

@Composable
fun rememberGamepadActivityState(gamepadActivity: GamepadActivity): GamepadActivity {
    return remember { gamepadActivity }
}