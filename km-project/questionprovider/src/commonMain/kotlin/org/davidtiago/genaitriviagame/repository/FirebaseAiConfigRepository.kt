package org.davidtiago.genaitriviagame.repository

import org.davidtiago.genaitriviagame.model.FirebaseAiConfig

interface FirebaseAiConfigRepository {
    suspend fun setConfig(config: FirebaseAiConfig)
    suspend fun getConfig(): FirebaseAiConfig
}
