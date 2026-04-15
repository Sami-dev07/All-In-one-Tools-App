# All In One Tools (Android)

An Android Studio project that bundles many everyday utilities into a single app (calculator, QR scanner, unit converters, image/PDF tools, voice recorder, compass/flashlight, speed meter, and more).

## Project overview

- **App module**: `:app`
- **Libraries**:
  - `:rulerview` (Android library module)
  - `:circleprogressbar` (Android library module)
- **Package / applicationId**: `com.alltools.toolbox.utility.calculator`
- **Launcher activity**: `com.alltools.toolbox.utility.calculator.GetStartedActivity.GetStarted` → opens `MainActivity`
- **Build system**: Gradle (wrapper included)

## Requirements

- **Android Studio**: recent version that supports **Android Gradle Plugin 8.2.x**
- **JDK**: **17** (the app is configured for Java/Kotlin target 17)
- **Android SDK**:
  - **minSdk**: 24
  - **targetSdk / compileSdk**: 36

## Setup (Android Studio)

1. Open the project folder in Android Studio.
2. Let Gradle sync finish.
3. Select a device (emulator or physical) and run the `app` configuration.




## Notable features (based on activities/services in the manifest)

- **Math & finance**: simple/scientific calculators, percentage/discount/fuel cost, random/password tools, unit converters (area/length/time/mass/volume/storage), rulers, periodic table, clothing/shoe sizes, stopwatch/countdown, calendar, tap counter
- **Essential tools**: QR code scanner, compass, flashlight, magnifier, speed meter (GPS), location/maps
- **Common tools**: image compressor/resizer, image → PDF, device details, text-to-speech, todo list, piano
- **Voice recorder**: recording service + saved recordings

## Configuration notes

### Google Maps API key

The app declares a Maps API key in `app/src/main/AndroidManifest.xml` via:

- `meta-data android:name="com.google.android.geo.API_KEY"`


### Permissions

The manifest requests several permissions (location, camera, microphone, storage/media, notifications, internet, foreground services). If you remove features, you may also want to remove the related permissions to keep the permission surface minimal.

