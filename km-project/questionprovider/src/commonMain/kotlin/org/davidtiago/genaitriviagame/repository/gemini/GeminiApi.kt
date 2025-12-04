package org.davidtiago.genaitriviagame.repository.gemini

import org.davidtiago.genaitriviagame.model.Question

interface GeminiApi {
    val model: String
    val prompt: String
    suspend fun getQuestions(): String
}