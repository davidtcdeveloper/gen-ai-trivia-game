package org.davidtiago.genaitriviagame.gemini

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import org.davidtiago.genaitriviagame.repository.gemini.GeminiApi

class AndroidGeminiApi(
    override val model: String,
    override val prompt: String
) : GeminiApi {
    override suspend fun getQuestions(): String {
        val model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel(model)
        val response = model.generateContent(prompt)
        print(response.text)
        return response.text ?: ""
    }
}
