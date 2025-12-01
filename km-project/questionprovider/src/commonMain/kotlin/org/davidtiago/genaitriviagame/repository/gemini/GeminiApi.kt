package org.davidtiago.genaitriviagame.repository.gemini

import org.davidtiago.genaitriviagame.model.Question

interface GeminiApi {
    suspend fun getQuestions(): String
}