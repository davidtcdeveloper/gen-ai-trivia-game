package org.davidtiago.genaitriviagame.repository

import org.davidtiago.genaitriviagame.model.FirebaseAiConfig

class InMemoryFirebaseAiConfigRepository : FirebaseAiConfigRepository {
    private var currentConfig = FirebaseAiConfig(
        apiKey = "",
        projectId = "",
        appId = "",
        appName = "",
    )

    override suspend fun setConfig(config: FirebaseAiConfig) {
        currentConfig = config
    }

    override suspend fun getConfig(): FirebaseAiConfig = currentConfig
}
