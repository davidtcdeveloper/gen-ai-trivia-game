package org.davidtiago.genaitriviagame.repository.gemini

import org.davidtiago.genaitriviagame.model.Question

internal actual class GeminiApi actual constructor() {
    actual suspend fun getQuestions(): List<Question> {
        // TODO: Implement the iOS version of the Gemini API call
        return emptyList()
    }
}
