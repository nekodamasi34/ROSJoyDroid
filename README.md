# ROSJoyDroid


**English README is here → [README.en.md](./README.en.md)**

![GitHub repo size](https://img.shields.io/github/repo-size/nekodamasi34/ROSJoyDroid)
![GitHub stars](https://img.shields.io/github/stars/nekodamasi34/ROSJoyDroid?style=social)
![GitHub license](https://img.shields.io/github/license/nekodamasi34/ROSJoyDroid)
![Last commit](https://img.shields.io/github/last-commit/nekodamasi34/ROSJoyDroid)
![Issues](https://img.shields.io/github/issues/nekodamasi34/ROSJoyDroid)
![Platform](https://img.shields.io/badge/platform-Android-blue)
![Language](https://img.shields.io/badge/language-Kotlin-7f52ff)

---

ゲームパッドやコントローラー入力をROS2（Humble）ロボットへJoyメッセージとして送信する、モダンなAndroidアプリです。  
Kotlin・Jetpack Compose・JNI（C++連携）を活用し、柔軟＆高速なロボットプロトタイピングが可能です。

⭐ **Issue/スター いつでも大歓迎です！**  
📝 コントリビューションやバグ報告はお気軽に！


## 主な特徴

- 📡 **ROS2連携**：JNI（C++ネイティブ）経由で`sensor_msgs/msg/Joy`をROS2 Humbleに直接パブリッシュ
- 🎮 **ゲームパッド対応**：主要なゲームパッドに幅広く対応、デッドゾーンや反転・ボタン／軸マッピングもカスタマイズ可
- 🖥️ **Jetpack Compose UI**：モダンで使いやすいUI（ステータス／マニュアル／エクストラの各モードをタブ切替）
- ⚙️ **詳細設定**：各軸・トリガーごとに反転設定や接続先設定も可能、手動操作・リアルタイム反映OK
- 📝 **設定保存**：SharedPreferencesでユーザー設定を自動保存・復元



## ディレクトリ構成

```text
rosjoydroid/
├── activity/ # MainActivity, GamepadActivity（コアロジック）
├── model/ # データクラス・enum（GamepadButton, Settingsなど）
├── ui/
│ ├── components/ # UIコンポーネント（AxisRow, ButtonVisualizerなど）
│ ├── screens/ # 画面・ペイン（MainScreen, StatusPaneなど）
│ └── theme/ # マテリアルテーマ・色・フォント設定
├── util/ # ユーティリティ（SharedPreferences拡張など）
```



## ビルド・実行方法

### 前提

- Android Studio（Giraffe以降推奨）
- 実機またはエミュレータ（API 31以上）
- NDK（C++ネイティブビルド用）

### 手順

1. このリポジトリをクローン
2. Android Studioで開く
3. NDKがインストールされていることを確認（「File > Project Structure > SDK Location > Android NDK」）
4. プロジェクトをビルド（「Build > Make Project」）
5. 実機またはエミュレータで実行

**注意：**  
実際にROS2と連携するには、Android端末とROS2マスターが同じネットワーク上にある必要があります。



## 設定方法

- アプリ起動後、**Settings**タブから
    - ROS2ネームスペース・Domain IDの設定
    - 更新周期（ms）の調整
    - デッドゾーン、各軸・トリガー反転の設定
- 設定は自動保存されます



## 仕組み

- **GamepadActivity**：Androidのゲームパッドイベントをキャプチャし、Flowで状態を公開
- **MainActivity**：UIと連携し、JNI経由でJoyメッセージをROS2へ送信
- **UI**：Composeベース、リアルタイム状態表示・手動操作・詳細設定をサポート



## 開発メモ

- JNI連携は`librosjoydroid.so`を利用（ネイティブビルド必須）
- UIは完全にモジュール化されているため、新しい画面や機能は`ui/screens/`を編集＆`MainScreen.kt`に追加でOK
- 各ファイルには詳細なヘッダコメントあり（役割や特徴はソース内ヘッダ参照）

## 謝辞

本プロジェクトは [eyr1n](https://github.com/eyr1n) 様により開発されました。  
その素晴らしい基盤・発想に心より感謝します。
ご協力いただいた全てのコントリビューター・OSSコミュニティの皆さまにも心より感謝します。

## ライセンス

MIT License  
Copyright (c) 2024 Rin Iwai   
Copyright (c) 2025 Shoichiro Aizawa



## 参考

- [ROS2 Humble](https://docs.ros.org/ja/humble/index.html)
- [Android Jetpack Compose](https://developer.android.com/jetpack/compose?hl=ja)
- [OpenAI chatGPT](https://openai.com/)（ドキュメント生成協力）

---