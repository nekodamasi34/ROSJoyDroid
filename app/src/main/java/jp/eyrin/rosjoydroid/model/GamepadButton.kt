package jp.eyrin.rosjoydroid.model

import android.view.KeyEvent

/**
 * model/GamepadButton.kt
 *
 * This file defines the GamepadButton enum, representing all supported gamepad buttons for the app.
 *
 * Features:
 *  - Enumerates all logical gamepad button names used throughout the project (e.g., ABXY, shoulder, stick, DPAD, etc.)
 *  - Includes a companion object with a utility function to convert Android KeyEvent key codes into GamepadButton values,
 *    making platform event handling and abstraction easier
 *  - Ensures consistent and type-safe button references across the UI, logic, and platform layers
 *
 * This enum is essential for mapping physical gamepad events to internal application state and logic.
 */

enum class GamepadButton {
    A,
    B,
    X,
    Y,
    L1,
    R1,
    BACK,
    START,
    GUIDE,
    LEFT_STICK,
    RIGHT_STICK,
    DPAD_UP,
    DPAD_DOWN,
    DPAD_LEFT,
    DPAD_RIGHT;

    companion object {
        fun fromKeyCode(keyCode: Int): GamepadButton? = when (keyCode) {
            KeyEvent.KEYCODE_BUTTON_A -> A
            KeyEvent.KEYCODE_BUTTON_B -> B
            KeyEvent.KEYCODE_BUTTON_X -> X
            KeyEvent.KEYCODE_BUTTON_Y -> Y
            KeyEvent.KEYCODE_BUTTON_L1 -> L1
            KeyEvent.KEYCODE_BUTTON_R1 -> R1
            KeyEvent.KEYCODE_BUTTON_SELECT -> BACK
            KeyEvent.KEYCODE_BUTTON_START -> START
            KeyEvent.KEYCODE_BUTTON_MODE -> GUIDE
            KeyEvent.KEYCODE_BUTTON_THUMBL -> LEFT_STICK
            KeyEvent.KEYCODE_BUTTON_THUMBR -> RIGHT_STICK
            KeyEvent.KEYCODE_DPAD_UP -> DPAD_UP
            KeyEvent.KEYCODE_DPAD_DOWN -> DPAD_DOWN
            KeyEvent.KEYCODE_DPAD_LEFT -> DPAD_LEFT
            KeyEvent.KEYCODE_DPAD_RIGHT -> DPAD_RIGHT
            else -> null
        }
    }
}