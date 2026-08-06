package org.davidtiago.genaitriviagame.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.davidtiago.genaitriviagame.repository.QuestionRepository
import org.davidtiago.genaitriviagame.presentation.*

class GameViewModel(
    private val stateMachine: GameStateMachine,
    private val questionRepository: QuestionRepository
) : ViewModel() {

    val gameState: StateFlow<GameState> = stateMachine.state

    init {
        loadQuestions()
    }

    fun loadQuestions() {
        viewModelScope.launch {
            stateMachine.transition(GameEvent.LoadStarted)
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
        val questionsToUse = when (val currentState = stateMachine.state.value) {
            is GameState.QuestionActive -> currentState.questions
            is GameState.AnswerResult -> currentState.questions
            is GameState.Finished -> emptyList() // Resets the questions to force a reload
            is GameState.Error, // In case of error or loading, returns without state change
            GameState.Loading -> return
        }

        if (questionsToUse.isNotEmpty()) {
            stateMachine.transition(GameEvent.Reset)
        } else {
            loadQuestions()
        }
    }
}
