import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    android {
        namespace = "org.davidtiago.genaitriviagame.questionprovider"
        compileSdk = libs.versions.android.targetSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
        androidResources {
            enable = true
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "questionprovider"
        }
    }

    sourceSets {
        commonMain.dependencies {
            // KotlinX
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization)
            implementation(libs.kotlinx.serialization.json)

            // Dependency Injection
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
        }


        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.firebase.bom))
            implementation(libs.com.google.firebase.firebase.ai)

        }

        iosMain.dependencies {
            // No specific dependencies for iOS
        }
    }
}
