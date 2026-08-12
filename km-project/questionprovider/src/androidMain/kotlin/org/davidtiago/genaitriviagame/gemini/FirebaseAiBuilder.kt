package org.davidtiago.genaitriviagame.gemini

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.davidtiago.genaitriviagame.model.FirebaseAiConfig
import org.davidtiago.genaitriviagame.repository.FirebaseAiConfigRepository

class FirebaseAiBuilder(
    private val firebaseAiConfigRepository: FirebaseAiConfigRepository,
    private val context: Context,
) {
    suspend fun getFirebaseAi(): FirebaseApp {
        val config = firebaseAiConfigRepository.getConfig()
        val options = FirebaseOptions.Builder()
            .setApiKey(config.apiKey)
            .setProjectId(config.projectId)
            .setApplicationId(config.appId)
            .build()

        return try {
            FirebaseApp.getInstance(config.appName)
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            FirebaseApp.initializeApp(context, options, config.appName)
        }
    }
}
