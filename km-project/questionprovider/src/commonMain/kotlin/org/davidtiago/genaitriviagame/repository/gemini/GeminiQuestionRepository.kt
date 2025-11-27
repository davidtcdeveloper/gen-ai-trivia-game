package org.davidtiago.genaitriviagame.repository.gemini

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.davidtiago.genaitriviagame.model.Question
import org.davidtiago.genaitriviagame.repository.QuestionRepository

class GeminiQuestionRepository(
    private val geminiApi: GeminiApi
) : QuestionRepository {

    override suspend fun getQuestions(): List<Question> {
        return parseQuestions(
            geminiApi.getQuestions()
        )
    }

    private fun parseQuestions(text: String?): List<Question> {
        if (text.isNullOrBlank()) {
            return emptyList()
        }
        return try {
            Json.decodeFromString<List<Question>>(text)
        } catch (e: SerializationException) {
            e.printStackTrace()
            emptyList()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            emptyList()
        }
    }
}
