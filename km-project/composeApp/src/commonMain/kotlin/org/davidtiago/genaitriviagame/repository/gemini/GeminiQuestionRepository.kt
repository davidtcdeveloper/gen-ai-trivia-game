package org.davidtiago.genaitriviagame.repository.gemini

import org.davidtiago.genaitriviagame.repository.gemini.GeminiApi
import org.davidtiago.genaitriviagame.model.Question
import org.davidtiago.genaitriviagame.repository.QuestionRepository

class GeminiQuestionRepository : QuestionRepository {
    private val geminiApi = GeminiApi()
    override suspend fun getQuestions(): List<Question> {
        return geminiApi.getQuestions()
    }
}