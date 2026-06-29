# Gen AI Trivia Game

This is a Kotlin Multiplatform project that implements a simple Trivia Game.

## Project Structure

The project is structured as a multi-module Kotlin Multiplatform (KMP) application, consisting of several shared and platform-specific modules:

```text
├── km-project/
│   ├── androidApp/          # Native Android App (Koin / Bootstrap / MainActivity)
│   ├── iosApp/              # Native iOS Xcode Project (SwiftUI / Koin Bootstrap)
│   ├── questionprovider/    # Shared KMP Business Logic Module (Retrieves trivia questions)
│   │   ├── src/
│   │   │   ├── commonMain/  # Repository interfaces (QuestionRepository, GeminiApi), models, prompt setup
│   │   │   └── androidMain/ # Android Gemini API implementation using Vertex AI for Firebase
│   └── shared/              # Shared UI Module (Compose Multiplatform)
│       ├── src/
│       │   ├── commonMain/  # Navigation, ViewModels, State Machine, and all UI Screens (GameUi, etc.)
│       │   └── iosMain/     # UIViewController bridge and Koin DI setup for iOS
```

### 1. `questionprovider`
A shared Kotlin Multiplatform library module responsible for managing and providing trivia questions.
*   **`commonMain`**: 
    *   Defines the core `Question` data model (serialized using `kotlinx.serialization`).
    *   Exposes the `QuestionRepository` interface.
    *   Defines the `GeminiApi` interface, representing the contract for communicating with the generative model.
    *   Defines default prompt constraints (`Prompt.kt`) for Gemini to ensure questions are generated as valid, randomized JSON matching our expectations.
    *   `GeminiQuestionRepository` parses the JSON response fetched via the `GeminiApi` interface into structured `Question` model instances.
*   **`androidMain`**:
    *   Contains the `AndroidGeminiApi` implementation which accesses the [Vertex AI for Firebase SDK](https://firebase.google.com/docs/vertex-ai) via `Firebase.ai(backend = GenerativeBackend.googleAI()).generativeModel(...)` to generate trivia questions natively.

### 2. `shared`
The main shared UI and state management module built using **Compose Multiplatform**.
*   **`commonMain`**:
    *   **Architecture**: Uses an event-driven State Machine pattern (`GameStateMachine`, `GameState`, `GameEvent`) to manage trivia game flows cleanly.
    *   **UI Components**: Implements modular Compose screens and subcomponents:
        *   `GameUi.kt`: Primary UI entry point.
        *   `QuestionUi.kt`: Visualizes the current question and the progress indicator.
        *   `AnswerCardsUi.kt`: Interactive selection cards for trivia choices.
        *   `GameResultsUi.kt`: Displays final score, completion messages, and retry button.
        *   `ResultCardUi.kt`: Visual representation of each answer outcome.
        *   `LoadingUi.kt` & `ErrorUi.kt`: Handle the loading states and API/network error states gracefully.
    *   **Navigation**: Powered by Jetpack Navigation 3 (`libs.navigation3.multiplatform`).
    *   **Dependency Injection**: Uses Koin (`koin-core` and `koin-compose-viewmodel`) to declare and wire the `mainModule` (including `GameViewModel`).
*   **`iosMain`**:
    *   Provides `MainViewController.kt`, which wraps the Compose `App()` entry point into a Swift-compatible UIViewController.
    *   Provides `KoinInit.kt` to bootstrap Koin dependency injection from Swift, accepting an externally injected implementation of `GeminiApi`.

### 3. `androidApp`
A thin Android application wrapper.
*   Initializes the Android application class `AndroidApp.kt`, setting up Koin with the android-specific (`androidMainModule`) and shared (`mainModule`) DI modules.
*   The `MainActivity.kt` bootstraps edge-to-edge support and loads the Compose Multiplatform `App()` screen.
*   Contains the `google-services.json` configuration required to authenticate Vertex AI for Firebase.

### 4. `iosApp`
A native Swift and SwiftUI Xcode project that serves as the entry point for iOS.
*   Imports the `Shared` framework exported by Kotlin Multiplatform.
*   **`iOSApp.swift`**: Configures Firebase and initializes Kotlin's Koin framework using `KoinInitKt.doInitKoin(...)`, injecting a Swift implementation of `GeminiApi` (`IosGeminiApi.swift`).
*   **`IosGeminiApi.swift`**: Swift-native implementation of Kotlin's `GeminiApi` using the iOS `FirebaseAILogic` SDK, maintaining symmetry with the Android Vertex AI initialization.
*   **`ContentView.swift`**: Displays the shared Compose-based user interface using a `UIViewControllerRepresentable` bridge to `MainViewController`.
*   Contains `GoogleService-Info.plist` for Firebase SDK setup on iOS.

## Setup

This project requires Android Studio in the latest version with Kotlin Multiplatform plugin installed.
It also relies on [Firebase AI Logic](https://firebase.google.com/docs/ai-logic?authuser=0) to generate the questions. In order to build successfully, a project must be configured in Firebase Console and the json files with API keys for Android and iOS must be downloaded and placed on `androidApp` and `iosApp` folders. 
