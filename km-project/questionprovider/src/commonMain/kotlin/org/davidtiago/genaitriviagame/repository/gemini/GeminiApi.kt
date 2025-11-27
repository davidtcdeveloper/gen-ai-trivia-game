package org.davidtiago.genaitriviagame.repository.gemini

import org.davidtiago.genaitriviagame.model.Question

interface GeminiApi {
    fun getQuestions(): String
}