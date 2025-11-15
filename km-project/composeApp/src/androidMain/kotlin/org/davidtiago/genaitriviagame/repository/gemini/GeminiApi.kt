package org.davidtiago.genaitriviagame.repository.gemini

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import org.davidtiago.genaitriviagame.model.Question
import org.davidtiago.genaitriviagame.repository.defaultQuestionsPrompt

internal actual class GeminiApi actual constructor() {
    actual suspend fun getQuestions(): List<Question> {
        val model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash")
        val response = model.generateContent(defaultQuestionsPrompt)
        print(response.text)
        return parseQuestions(response.text)
    }

    private fun parseQuestions(text: String?): List<Question> {
        if (text.isNullOrBlank()) {
            return emptyList()
        }
        return try {
            Json.decodeFromString<List<Question>>(text)
        } catch (_: SerializationException) {
            emptyList()
        } catch (_: IllegalArgumentException) {
            emptyList()
        }
    }
}