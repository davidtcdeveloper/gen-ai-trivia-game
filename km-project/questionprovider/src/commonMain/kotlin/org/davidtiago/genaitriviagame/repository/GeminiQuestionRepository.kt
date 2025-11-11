package org.davidtiago.genaitriviagame.repository

import org.davidtiago.genaitriviagame.gemini.GeminiApi
import org.davidtiago.genaitriviagame.model.Question

class GeminiQuestionRepository : QuestionRepository {
    private val geminiApi = GeminiApi()
    override suspend fun getQuestions(): List<Question> {
        return geminiApi.getQuestions()
    }
}