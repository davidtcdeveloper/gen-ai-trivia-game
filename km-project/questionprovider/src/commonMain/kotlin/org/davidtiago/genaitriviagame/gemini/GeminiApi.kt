package org.davidtiago.genaitriviagame.gemini

import org.davidtiago.genaitriviagame.model.Question

internal expect class GeminiApi() {
    suspend fun getQuestions(): List<Question>
}

