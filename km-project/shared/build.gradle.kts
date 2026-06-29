import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}


kotlin {
    android {
        namespace = "org.davidtiago.genaitriviagame.shared"
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
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
            export(project(":questionprovider"))
        }
    }

    sourceSets {

        androidMain.dependencies {
            implementation(libs.ui.tooling.preview)
            implementation(libs.ui.tooling)
            implementation(libs.androidx.activity.compose)
            implementation(project.dependencies.platform(libs.firebase.bom))
        }
        commonMain.dependencies {
            // Jetpack Compose
            implementation(libs.runtime)
            implementation(libs.foundation)
            implementation(libs.material)
            implementation(libs.ui)
            implementation(libs.components.resources)
            implementation(libs.ui.tooling.preview)
            implementation(libs.navigation3.multiplatform)

            // Lifecycle
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            //KotlinX
            implementation(libs.kotlinx.coroutines.core)

            // Dependency Injection
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)

            //Module dependencies
            api(project(":questionprovider"))
        }
    }
}
