package org.davidtiago.genaitriviagame.gemini

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import org.davidtiago.genaitriviagame.defaultQuestionsPrompt
import org.davidtiago.genaitriviagame.repository.gemini.GeminiApi

class AndroidGeminiApi : GeminiApi {
    override suspend fun getQuestions(): String {
        val model = Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel("gemini-2.5-flash")
        val response = model.generateContent(defaultQuestionsPrompt)
        print(response.text)
        return response.text ?: ""
    }
}
