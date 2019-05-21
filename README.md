# DodgeBox

Dodgebox is a personal mobile game app that is currently available for download on the [Google PlayStore](https://play.google.com/store/apps/details?id=com.mygdx.aljawahari)
for free. The game was written in Java in the Android Studio IDE with the help of [libgdx](https://libgdx.badlogicgames.com/), a free and open-source game-development application.

## Playing

### Android Device
To play the game, it is best to download it from the [Google PlayStore](https://play.google.com/store/apps/details?id=com.mygdx.aljawahari).

### Through Android Studio

Building the game through Android Studio is straightforward:

1. `git clone https://github.com/aMoonEar/DodgeBoxes.git`.
2. `cd DodgeBoxes`
3. Now you can choose to either build for `desktop` or `android`:
   1. For desktop, use `./gradlew desktop:dist`
   2. For Android, use `./gradlew android:assembleRelease`
4. You're done! The generated files are under `build`:
   1. Desktop build is under `desktop/build/libs/*.jar`
   2. Android build is under `android/build/outputs/apk/*.apk`

## Contributing

There is room for the app to improve, such as:
*Adding sound effects and background music
*Adding powerups for the player to collect that alter the state of the game
*Add an end screen/continuation when the player reaches the maximum size
