package org.davidtiago.genaitriviagame.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.davidtiago.genaitriviagame.model.FirebaseAiConfig
import org.davidtiago.genaitriviagame.presentation.GameEvent
import org.davidtiago.genaitriviagame.presentation.GameState
import org.davidtiago.genaitriviagame.presentation.GameStateMachine
import org.davidtiago.genaitriviagame.repository.QuestionRepository
import org.davidtiago.genaitriviagame.repository.FirebaseAiConfigRepository

class GameViewModel(
    private val stateMachine: GameStateMachine,
    private val questionRepository: QuestionRepository,
    private val firebaseAiConfigRepository: FirebaseAiConfigRepository,
) : ViewModel() {

    val gameState: StateFlow<GameState> = stateMachine.state

    init {
        viewModelScope.launch {
            gameState.collect { state ->
                if (state == GameState.LaunchingApp) {
                    stateMachine.transition(
                        GameEvent.InitialConfigLoaded(
                           firebaseAiConfigRepository.getConfig()
                        )
                    )
                }
            }
        }
    }

    fun saveAiConfig(firebaseAiConfig: FirebaseAiConfig) {
        viewModelScope.launch {
            firebaseAiConfigRepository.setConfig(firebaseAiConfig)
            loadNewQuestions()
        }
    }

    private fun loadNewQuestions() {
        viewModelScope.launch {
            stateMachine.transition(GameEvent.QuestionLoadStarted)
            try {
                val questions = questionRepository.getQuestions()
                if (questions.isEmpty()) {
                    stateMachine.transition(GameEvent.LoadFailure("No questions available"))
                } else {
                    stateMachine.transition(GameEvent.LoadSuccess(questions))
                }
            } catch (e: Exception) {
                stateMachine.transition(GameEvent.LoadFailure(e.message))
            }
        }
    }

    fun onAnswerSelected(answer: String) {
        stateMachine.transition(GameEvent.SelectAnswer(answer))
    }

    fun onSubmit() {
        stateMachine.transition(GameEvent.SubmitAnswer)
    }

    fun onNextQuestion() {
        stateMachine.transition(GameEvent.NextQuestion)
    }

    fun restartGame() {
        // Only the Finished or Error states ar valid for a restart.
        // Other states are ignored for now.
        when (stateMachine.state.value) {
            is GameState.QuestionActive,
            is GameState.AnswerResult,
            is GameState.DefiningAiConfiguration,
            GameState.LaunchingApp,
            GameState.LoadingQuestions -> return

            is GameState.Error,
            is GameState.Finished -> loadNewQuestions()
        }
    }
}
