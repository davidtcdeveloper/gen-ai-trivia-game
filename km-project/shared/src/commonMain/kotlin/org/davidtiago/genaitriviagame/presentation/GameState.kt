package org.davidtiago.genaitriviagame.presentation

import org.davidtiago.genaitriviagame.model.FirebaseAiConfig
import org.davidtiago.genaitriviagame.model.Question

sealed interface GameState {

    /**
     * The app was just launched and the game must be configured before advancing.
     * */
    object LaunchingApp : GameState

    /**
     * Questions are being loaded from the source before the game is ready to be played.
     */
    object LoadingQuestions : GameState

    /**
     * State representing a failure to load questions.
     */
    data class Error(val message: String? = null) : GameState

    /**
     * State when a question is actively being displayed to the user and they can select an answer.
     */
    data class QuestionActive(
        val questions: List<Question>,
        val currentQuestionIndex: Int,
        val correctAnswers: Int,
        val selectedAnswer: String?
    ) : GameState {
        val question: Question get() = questions[currentQuestionIndex]
    }

    /**
     * State after the user has submitted their answer, showing feedback.
     */
    data class AnswerResult(
        val questions: List<Question>,
        val currentQuestionIndex: Int,
        val correctAnswers: Int,
        val selectedAnswer: String?,
        val isCorrect: Boolean
    ) : GameState {
        val question: Question get() = questions[currentQuestionIndex]
        val hasMoreQuestions: Boolean get() = currentQuestionIndex < questions.size - 1
    }

    /**
     * State when the game is finished and the final score is shown.
     */
    data class Finished(
        val questions: List<Question>,
        val score: Int
    ) : GameState {
        val totalQuestions: Int get() = questions.size
    }

    /**
     * User is defining the Ai configuration, including API keys.
     * */
    //TODO: Temporary state, will be migrated to a separated scree with navigation
    class DefiningAiConfiguration(
        val initialConfig: FirebaseAiConfig,
    ) : GameState
}
