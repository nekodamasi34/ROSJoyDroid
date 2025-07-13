package jp.eyrin.rosjoydroid.activity

import android.view.InputDevice
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import jp.eyrin.rosjoydroid.model.GamepadButton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * activity/GamepadActivity.kt
 *
 * This file defines the base activity class for handling gamepad input.
 *
 * Features:
 *  - Captures and manages all gamepad input (buttons, sticks, triggers, etc.)
 *  - Applies dead zone processing and axis inversion
 *  - Provides current axis and button states via Kotlin Flow
 *  - Supports directional pad (DPAD) input
 *
 * Intended to be subclassed for activities requiring gamepad functionality.
 */

open class GamepadActivity : ComponentActivity() {

    private val _axes = MutableStateFlow(floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f))
    private val _buttons = MutableStateFlow(IntArray(GamepadButton.entries.size))

    var deadZone = 0f
    var invertLeftX = false
    var invertLeftY = false
    var invertRightX = false
    var invertRightY = false
    var invertLeftTrigger = false
    var invertRightTrigger = false

    val axes: StateFlow<FloatArray> get() = _axes
    val buttons: StateFlow<IntArray> get() = _buttons

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (event.source and (InputDevice.SOURCE_GAMEPAD or InputDevice.SOURCE_JOYSTICK) != 0) {
            GamepadButton.fromKeyCode(keyCode)?.let {
                updateGamepadButton(it, 1)
                true
            } ?: super.onKeyDown(keyCode, event)
        } else super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        return if (event.source and (InputDevice.SOURCE_GAMEPAD or InputDevice.SOURCE_JOYSTICK) != 0) {
            GamepadButton.fromKeyCode(keyCode)?.let {
                updateGamepadButton(it, 0)
                true
            } ?: super.onKeyDown(keyCode, event)
        } else super.onKeyDown(keyCode, event)
    }

    override fun onGenericMotionEvent(event: MotionEvent): Boolean {
        return if (event.source and (InputDevice.SOURCE_GAMEPAD or InputDevice.SOURCE_JOYSTICK) != 0 && event.action == MotionEvent.ACTION_MOVE) {
            this._axes.value = floatArrayOf(
                processAxis(event.getAxisValue(MotionEvent.AXIS_X), invertLeftX),
                processAxis(event.getAxisValue(MotionEvent.AXIS_Y), invertLeftY),
                processAxis(event.getAxisValue(MotionEvent.AXIS_Z), invertRightX),
                processAxis(event.getAxisValue(MotionEvent.AXIS_RZ), invertRightY),
                processAxis(event.getAxisValue(MotionEvent.AXIS_BRAKE), invertLeftTrigger),
                processAxis(event.getAxisValue(MotionEvent.AXIS_GAS), invertRightTrigger),
            )

            val xAxis = event.getAxisValue(MotionEvent.AXIS_HAT_X)
            val yAxis = event.getAxisValue(MotionEvent.AXIS_HAT_Y)
            when (xAxis) {
                -1.0f -> updateGamepadButton(GamepadButton.DPAD_LEFT, 1)
                1.0f -> updateGamepadButton(GamepadButton.DPAD_RIGHT, 1)
                else -> {
                    updateGamepadButton(GamepadButton.DPAD_LEFT, 0)
                    updateGamepadButton(GamepadButton.DPAD_RIGHT, 0)
                }
            }
            when (yAxis) {
                -1.0f -> updateGamepadButton(GamepadButton.DPAD_UP, 1)
                1.0f -> updateGamepadButton(GamepadButton.DPAD_DOWN, 1)
                else -> {
                    updateGamepadButton(GamepadButton.DPAD_UP, 0)
                    updateGamepadButton(GamepadButton.DPAD_DOWN, 0)
                }
            }

            true
        } else {
            super.onGenericMotionEvent(event)
        }
    }

    private fun updateGamepadButton(key: GamepadButton, state: Int) {
        _buttons.update {
            it.clone().apply {
                this[key.ordinal] = state
            }
        }
    }

    private fun processAxis(value: Float, invert: Boolean): Float {
        val processedValue = if (value in -deadZone..deadZone) 0f else value
        return if (invert) -processedValue else processedValue
    }
}
