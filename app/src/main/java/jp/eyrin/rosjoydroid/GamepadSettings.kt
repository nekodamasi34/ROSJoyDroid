// GamepadSettings.kt
package jp.eyrin.rosjoydroid

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

data class GamepadSettings(
    val namespace: String = "",
    val domainId: Int = 0,
    val period: Long = 20,
    val deadZone: Float = 0.05f,
    val invertLeftX: Boolean = false,
    val invertLeftY: Boolean = false,
    val invertRightX: Boolean = false,
    val invertRightY: Boolean = false,
    val invertLeftTrigger: Boolean = false,
    val invertRightTrigger: Boolean = false
) {
    companion object {
        fun loadFromPrefs(prefs: SharedPreferences): GamepadSettings {
            return GamepadSettings(
                namespace = prefs.getString("ns", "") ?: "",
                domainId = prefs.getInt("domainId", 0),
                period = prefs.getLong("period", 20),
                deadZone = prefs.getFloat("deadZone", 0.05f),
                invertLeftX = prefs.getBoolean("invertLeftX", false),
                invertLeftY = prefs.getBoolean("invertLeftY", false),
                invertRightX = prefs.getBoolean("invertRightX", false),
                invertRightY = prefs.getBoolean("invertRightY", false),
                invertLeftTrigger = prefs.getBoolean("invertLeftTrigger", false),
                invertRightTrigger = prefs.getBoolean("invertRightTrigger", false)
            )
        }

        fun saveToPrefs(prefs: SharedPreferences, settings: GamepadSettings) {
            prefs.edit().apply {
                putString("ns", settings.namespace)
                putInt("domainId", settings.domainId)
                putLong("period", settings.period)
                putFloat("deadZone", settings.deadZone)
                putBoolean("invertLeftX", settings.invertLeftX)
                putBoolean("invertLeftY", settings.invertLeftY)
                putBoolean("invertRightX", settings.invertRightX)
                putBoolean("invertRightY", settings.invertRightY)
                putBoolean("invertLeftTrigger", settings.invertLeftTrigger)
                putBoolean("invertRightTrigger", settings.invertRightTrigger)
                apply()
            }
        }
    }
}

@Composable
fun rememberGamepadSettings(prefs: SharedPreferences): MutableState<GamepadSettings> {
    return remember { mutableStateOf(GamepadSettings.loadFromPrefs(prefs)) }
}