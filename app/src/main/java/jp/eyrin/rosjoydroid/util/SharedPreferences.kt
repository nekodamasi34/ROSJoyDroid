package jp.eyrin.rosjoydroid.util

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.edit

/**
 * util/SharedPreferences.kt
 *
 * This file provides utility extensions for integrating Android's SharedPreferences
 * with Jetpack Compose state management.
 *
 * Features:
 *  - Defines an inline extension function for SharedPreferences to create a MutableState<T>
 *    that automatically synchronizes with persistent storage
 *  - Supports automatic two-way binding for all basic types (String, Int, Long, Float, Boolean)
 *  - Any change to the MutableState value is instantly reflected in SharedPreferences, and vice versa
 *  - Enables Compose UI components to seamlessly read and write app preferences or settings
 *  - Throws an exception if an unsupported type is used, ensuring type safety
 *
 * Designed to simplify preference handling in Compose-based apps, making persistent state
 * as easy to use as local state variables.
 */

inline fun <reified T> SharedPreferences.mutableStateOf(
    key: String, defValue: T
): MutableState<T> {
    val state = mutableStateOf(
        when (T::class) {
            String::class -> getString(key, defValue as String) as T
            Int::class -> getInt(key, defValue as Int) as T
            Long::class -> getLong(key, defValue as Long) as T
            Float::class -> getFloat(key, defValue as Float) as T
            Boolean::class -> getBoolean(key, defValue as Boolean) as T
            else -> throw IllegalArgumentException("\"${T::class.simpleName}\" is not supported.")
        }
    )

    return object : MutableState<T> by state {
        override var value: T
            get() = state.value
            set(value) {
                state.value = value
                edit {
                    when (T::class) {
                        String::class -> putString(key, value as String)
                        Int::class -> putInt(key, value as Int)
                        Long::class -> putLong(key, value as Long)
                        Float::class -> putFloat(key, value as Float)
                        Boolean::class -> putBoolean(key, value as Boolean)
                    }
                }
            }
    }
}
