package org.davidtiago.genaitriviagame.repository

import org.davidtiago.genaitriviagame.model.Question

class InMemoryMockQuestionRepository : QuestionRepository {
    override suspend fun getQuestions(): List<Question> {
        return listOf(
            Question(
                text = "What is the capital of France?",
                options = listOf("London", "Berlin", "Paris", "Madrid"),
                correctAnswer = "Paris"
            ),
            Question(
                text = "Which planet is known as the Red Planet?",
                options = listOf("Venus", "Mars", "Jupiter", "Saturn"),
                correctAnswer = "Mars"
            ),
            Question(
                text = "What is the largest mammal in the world?",
                options = listOf("African Elephant", "Blue Whale", "Giraffe", "Polar Bear"),
                correctAnswer = "Blue Whale"
            ),
            Question(
                text = "Which element has the chemical symbol 'Au'?",
                options = listOf("Silver", "Copper", "Gold", "Aluminum"),
                correctAnswer = "Gold"
            ),
            Question(
                text = "Who painted the Mona Lisa?",
                options = listOf("Vincent van Gogh", "Pablo Picasso", "Leonardo da Vinci", "Michelangelo"),
                correctAnswer = "Leonardo da Vinci"
            )
        )
    }
} 