package org.davidtiago.genaitriviagame.repository

import org.davidtiago.genaitriviagame.model.FirebaseAiConfig

interface FirebaseAiConfigRepository {
    fun getConfig(): FirebaseAiConfig
}
