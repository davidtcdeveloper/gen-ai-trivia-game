package org.davidtiago.genaitriviagame.presentation

import org.davidtiago.genaitriviagame.model.FirebaseAiConfig
import org.davidtiago.genaitriviagame.model.Question

sealed interface GameEvent {
    /**
     * Triggered when app was launched and game must be configured.
     * */
    object AppLaunched : GameEvent

    /**
     * The initial configuration of the game was loaded.
     * */
    data class InitialConfigLoaded(val firebaseAiConfig: FirebaseAiConfig) : GameEvent

    /**
     * Triggered when loading has initiated.
     */
    object QuestionLoadStarted : GameEvent

    /**
     * Triggered when questions have successfully loaded.
     */
    data class LoadSuccess(val questions: List<Question>) : GameEvent

    /**
     * Triggered when questions fail to load.
     */
    data class LoadFailure(val message: String?) : GameEvent

    /**
     * Triggered when an answer is selected.
     */
    data class SelectAnswer(val answer: String) : GameEvent

    /**
     * Triggered when the user submits their answer.
     */
    object SubmitAnswer : GameEvent

    /**
     * Triggered when advancing to the next question.
     */
    object NextQuestion : GameEvent

    /**
     * Triggered when restarting the game.
     */
    object Reset : GameEvent
}
