# ROSJoyDroid

[日本語版READMEはこちら (Japanese README here)](./README.md)

A modern Android app for sending gamepad/controller input to ROS2 (Humble) robots via the Joy message interface.  
Built with Kotlin, Jetpack Compose, and native JNI integration for fast and flexible robotics prototyping.



## Features

- 📡 **ROS2 Integration**: Publish [sensor_msgs/msg/Joy](https://github.com/ros2/common_interfaces/tree/humble/sensor_msgs/msg) messages directly to ROS2 Humble via JNI (C++ bridge)
- 🎮 **Gamepad Support**: Supports most modern gamepads; customizable dead zone, inversion, button/axis mapping
- 🖥️ **Jetpack Compose UI**: Responsive, modern UI with tabs for Status, Manual, and Extra control modes
- ⚙️ **Advanced Settings**: Per-axis and trigger inversion, connection configuration, real-time manual override
- 📝 **Persistent Settings**: Save/load user preferences via SharedPreferences



## Project Structure

```
rosjoydroid/
├── activity/ # MainActivity, GamepadActivity (core logic)
├── model/ # Data classes, enums (GamepadButton, Settings, etc.)
├── ui/
│ ├── components/ # UI components (AxisRow, ButtonVisualizer, etc.)
│ ├── screens/ # Top-level screens/panes (MainScreen, StatusPane, etc.)
│ └── theme/ # Material theme, color, typography settings
├── util/ # Utilities (SharedPreferences extensions, etc.)
```



## Build & Run

### Prerequisites

- Android Studio (Giraffe or newer recommended)
- Android device or emulator (API 31+)
- NDK (for native ROS2 communication)

### Steps

1. Clone this repo
2. Open in Android Studio
3. Ensure the NDK is installed (`File > Project Structure > SDK Location > Android NDK`)
4. Build the project (`Build > Make Project`)
5. Run on your Android device or emulator

**Note:** For real ROS2 integration, your device needs to be on the same network as your ROS2 master.



## Configuration

- Launch the app and use the **Settings** tab to:
    - Set ROS2 namespace & domain ID
    - Adjust update period (ms)
    - Configure dead zone, axis/trigger inversion
- Changes are saved automatically



## How It Works

- **GamepadActivity**: Captures Android gamepad events and exposes them as flows
- **MainActivity**: Connects the UI and handles Joy message publishing via JNI (C++ native library)
- **UI**: Compose-based, providing real-time feedback, manual controls, and advanced settings



## Development Notes

- JNI integration is via `librosjoydroid.so` (ensure native libs are built/linked)
- UI is fully modular: add new screens/panes by editing `ui/screens/` and updating `MainScreen.kt`
- All code is heavily documented with file-level headers (see source files for roles/features)

## Acknowledgments

This project is originally created by [eyr1n](https://github.com/eyr1n).  
Huge thanks for the awesome foundation and inspiration.  
Special thanks also to all contributors and the open source community!

## License

MIT License  
Copyright (c) 2024 Rin Iwai
Copyright (c) 2025 Shoichiro Aizawa



## Acknowledgments

- Built on [ROS2](https://docs.ros.org/en/humble/index.html), [Android Jetpack Compose](https://developer.android.com/jetpack/compose), and Kotlin
- Thanks to [OpenAI GPT-4o](https://openai.com/) for documentation support

---