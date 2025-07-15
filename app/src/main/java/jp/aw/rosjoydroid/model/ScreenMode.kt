package jp.aw.rosjoydroid.model

/**
 * model/ScreenMode.kt
 *
 * This file defines the ScreenMode enum for managing the current UI mode of the application (Status, Manual, Extra).
 *
 * Features:
 *  - Enumerates all available high-level app modes or screens
 *  - Associates each mode with a human-readable label for display in the UI (tab bar, etc.)
 *  - Centralizes navigation state for easy switching and consistent UI logic
 *
 * Used by the main screen to switch between different functional panes in the app.
 */

enum class ScreenMode(val label: String) {
    STATUS("Status"),
    MANUAL("Manual"),
    EXTRA("Extra")
}
