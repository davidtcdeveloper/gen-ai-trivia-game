package org.davidtiago.genaitriviagame.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface GameStateMachine {
    val state: StateFlow<GameState>
    fun transition(event: GameEvent)
}

class GameStateMachineImpl : GameStateMachine {

    private val _state = MutableStateFlow<GameState>(GameState.Loading)
    override val state: StateFlow<GameState> = _state.asStateFlow()

    override fun transition(event: GameEvent) {
        _state.value = reduce(_state.value, event)
    }

    private fun reduce(currentState: GameState, event: GameEvent): GameState {
        return when (event) {
            is GameEvent.LoadStarted -> {
                GameState.Loading
            }
            is GameEvent.LoadSuccess -> {
                GameState.QuestionActive(
                    questions = event.questions,
                    currentQuestionIndex = 0,
                    correctAnswers = 0,
                    selectedAnswer = null
                )
            }
            is GameEvent.LoadFailure -> {
                GameState.Error(message = event.message)
            }
            is GameEvent.SelectAnswer -> {
                if (currentState is GameState.QuestionActive) {
                    currentState.copy(selectedAnswer = event.answer)
                } else {
                    currentState
                }
            }
            is GameEvent.SubmitAnswer -> {
                if (currentState is GameState.QuestionActive) {
                    val selected = currentState.selectedAnswer
                    val isCorrect = selected == currentState.question.correctAnswer
                    val newCorrectAnswers = if (isCorrect) currentState.correctAnswers + 1 else currentState.correctAnswers
                    GameState.AnswerResult(
                        questions = currentState.questions,
                        currentQuestionIndex = currentState.currentQuestionIndex,
                        correctAnswers = newCorrectAnswers,
                        selectedAnswer = selected,
                        isCorrect = isCorrect
                    )
                } else {
                    currentState
                }
            }
            is GameEvent.NextQuestion -> {
                if (currentState is GameState.AnswerResult) {
                    if (currentState.hasMoreQuestions) {
                        val nextIndex = currentState.currentQuestionIndex + 1
                        GameState.QuestionActive(
                            questions = currentState.questions,
                            currentQuestionIndex = nextIndex,
                            correctAnswers = currentState.correctAnswers,
                            selectedAnswer = null
                        )
                    } else {
                        GameState.Finished(
                            questions = currentState.questions,
                            score = currentState.correctAnswers
                        )
                    }
                } else {
                    currentState
                }
            }
            is GameEvent.Reset -> {
                val questionsToUse = when (currentState) {
                    is GameState.QuestionActive -> currentState.questions
                    is GameState.AnswerResult -> currentState.questions
                    is GameState.Finished -> currentState.questions
                    else -> emptyList()
                }

                if (questionsToUse.isNotEmpty()) {
                    GameState.QuestionActive(
                        questions = questionsToUse,
                        currentQuestionIndex = 0,
                        correctAnswers = 0,
                        selectedAnswer = null
                    )
                } else {
                    GameState.Loading
                }
            }
        }
    }
}
