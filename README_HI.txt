BGMI PRIME MAX - Android Java/XML starter project

Device target:
- Android 15
- 6 GB RAM
- compileSdk 35 / targetSdk 35
- minSdk 23
- Java 17
- Android Gradle Plugin 8.6.1 / Gradle 8.7

Included:
- 1-100 animated loading bar
- Engine sound toggle and alternating audio slots
- Facebook login setup placeholder (does not collect Facebook passwords)
- Button to launch BGMI if installed and package name matches
- Restart loading animation

Audio:
Add car_engine.wav and superbike_engine.wav to:
app/src/main/res/raw/

Build:
1. Open the project in an Android Gradle-compatible IDE.
2. Use JDK 17 and Gradle 8.7 with Android Gradle Plugin 8.6.1.
3. Allow Gradle to sync/download dependencies.
4. Build > Build APK(s), or run the app on the connected phone.
5. Install the generated debug APK.

Phone-only note:
AIDE versions differ in support for modern Android Gradle Plugin projects. If AIDE cannot sync this project, use an Android IDE that supports AGP 8.6.1 / Gradle 8.7, or use a cloud build service. Do not paste the project into a random APK-builder website.

Facebook:
A real Facebook login requires a Meta developer app, OAuth configuration, package name,
key hashes and the official Facebook Login SDK. This starter deliberately contains only
a setup placeholder and never asks for a Facebook password.

Safety:
This is a standalone launcher UI only. It does not contain ESP, aimbot, magic bullet,
game-memory access, code injection, anti-cheat bypass or anti-ban claims. No third-party
launcher can guarantee that a BGMI account will never be banned.
