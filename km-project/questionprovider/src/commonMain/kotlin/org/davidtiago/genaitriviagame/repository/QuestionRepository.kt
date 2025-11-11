package org.davidtiago.genaitriviagame.repository

import org.davidtiago.genaitriviagame.model.Question

interface QuestionRepository {
    suspend fun getQuestions(): List<Question>
}
