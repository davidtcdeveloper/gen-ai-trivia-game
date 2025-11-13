package org.davidtiago.genaitriviagame.repository.gemini

import org.davidtiago.genaitriviagame.model.Question

internal expect class GeminiApi() {
    suspend fun getQuestions(): List<Question>
}