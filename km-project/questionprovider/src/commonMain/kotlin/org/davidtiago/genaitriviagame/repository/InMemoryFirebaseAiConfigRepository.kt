package org.davidtiago.genaitriviagame.repository

import org.davidtiago.genaitriviagame.model.FirebaseAiConfig

class InMemoryFirebaseAiConfigRepository: FirebaseAiConfigRepository {
    override fun getConfig(): FirebaseAiConfig =
        FirebaseAiConfig(
            apiKey = "",
            projectId = "",
            appId = "",
            appName = "",
        )
}
