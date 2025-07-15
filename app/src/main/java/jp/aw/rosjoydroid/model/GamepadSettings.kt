package jp.aw.rosjoydroid.model

import android.content.SharedPreferences

/**
 * model/GamepadSettings.kt
 *
 * This file defines the GamepadSettings data class for storing all gamepad configuration parameters,
 * and provides extension functions for persistent storage.
 *
 * Features:
 *  - Encapsulates all configuration options related to gamepad operation (namespace, domain ID, publish period, dead zone, axis and trigger inversion, etc.)
 *  - Provides extension functions to easily save and load settings to and from Android's SharedPreferences,
 *    allowing seamless persistence and restoration of user or device preferences
 *  - Makes it easy to pass, copy, and update configuration across different screens and layers of the app
 *
 * Used as the central model for controller and ROS2 communication settings throughout the application.
 */

data class GamepadSettings(
    val namespace: String = "",
    val domainId: Int = 0,
    val period: Long = 20L,
    val deadZone: Float = 0.05f,
    val invertLeftX: Boolean = false,
    val invertLeftY: Boolean = false,
    val invertRightX: Boolean = false,
    val invertRightY: Boolean = false,
    val invertLeftTrigger: Boolean = false,
    val invertRightTrigger: Boolean = false,
) {
    companion object
}

// ここから拡張関数を追記！！！

// 保存
fun GamepadSettings.saveToPrefs(prefs: SharedPreferences) {
    prefs.edit().apply {
        putString("namespace", namespace)
        putInt("domainId", domainId)
        putLong("period", period)
        putFloat("deadZone", deadZone)
        putBoolean("invertLeftX", invertLeftX)
        putBoolean("invertLeftY", invertLeftY)
        putBoolean("invertRightX", invertRightX)
        putBoolean("invertRightY", invertRightY)
        putBoolean("invertLeftTrigger", invertLeftTrigger)
        putBoolean("invertRightTrigger", invertRightTrigger)
        apply()
    }
}

// 読み込み
fun GamepadSettings.Companion.loadFromPrefs(prefs: SharedPreferences): GamepadSettings {
    return GamepadSettings(
        namespace = prefs.getString("namespace", "") ?: "",
        domainId  = prefs.getInt("domainId", 0),
        period    = prefs.getLong("period", 20L),
        deadZone  = prefs.getFloat("deadZone", 0.05f),
        invertLeftX = prefs.getBoolean("invertLeftX", false),
        invertLeftY = prefs.getBoolean("invertLeftY", false),
        invertRightX = prefs.getBoolean("invertRightX", false),
        invertRightY = prefs.getBoolean("invertRightY", false),
        invertLeftTrigger = prefs.getBoolean("invertLeftTrigger", false),
        invertRightTrigger = prefs.getBoolean("invertRightTrigger", false)
    )
}
