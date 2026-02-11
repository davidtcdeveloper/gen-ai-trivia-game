# Gen AI Trivia Game

This is a Kotlin Multiplatform project that implements a simple Trivia Game.

## Project Structure

*   `composeApp`: Contains the shared UI, written in Compose Multiplatform.
*   `androidApp`: The Android application.
*   `iosApp`: The iOS application.
*   `questionprovider`: A shared module for providing trivia questions.

## Setup

This project requires Android Studio in the latest version with Kotlin Multiplatform plugin installed.
It also relies on [Firebase AI Logic](https://firebase.google.com/docs/ai-logic?authuser=0) to generate the questions. In order to build successfully, a project must be configured in Firebase Console and the json files with API keys for Android and iOS must be downloaded and placed on `androidApp` and `iosApp` folders. 
