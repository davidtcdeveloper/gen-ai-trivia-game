package org.davidtiago.genaitriviagame.ui

import org.davidtiago.genaitriviagame.model.Question

sealed interface GameState {
    /**
     * Initial state when questions are being loaded from the repository.
     */
    object Loading : GameState

    /**
     * State representing a failure to load questions.
     */
    data class Error(val message: String? = null) : GameState

    /**
     * State when a question is actively being displayed to the user and they can select an answer.
     */
    data class QuestionActive(
        val question: Question,
        val selectedAnswer: String?,
        val currentQuestionIndex: Int,
        val totalQuestions: Int
    ) : GameState

    /**
     * State after the user has submitted their answer, showing feedback.
     */
    data class AnswerResult(
        val question: Question,
        val selectedAnswer: String?,
        val isCorrect: Boolean,
        val hasMoreQuestions: Boolean
    ) : GameState

    /**
     * State when the game is finished and the final score is shown.
     */
    data class Finished(
        val score: Int,
        val totalQuestions: Int
    ) : GameState
}
