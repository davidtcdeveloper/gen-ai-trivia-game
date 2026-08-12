package org.davidtiago.genaitriviagame.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.davidtiago.genaitriviagame.presentation.GameState.*

interface GameStateMachine {
    val state: StateFlow<GameState>
    fun transition(event: GameEvent)
}

class GameStateMachineImpl : GameStateMachine {

    private val _state = MutableStateFlow<GameState>(LaunchingApp)
    override val state: StateFlow<GameState> = _state.asStateFlow()

    override fun transition(event: GameEvent) {
        _state.value = reduce(_state.value, event)
    }

    private fun reduce(currentState: GameState, event: GameEvent): GameState {
        return when (event) {
            GameEvent.AppLaunched -> {
                LaunchingApp
            }

            is GameEvent.QuestionLoadStarted -> {
                LoadingQuestions
            }

            is GameEvent.LoadSuccess -> {
                QuestionActive(
                    questions = event.questions,
                    currentQuestionIndex = 0,
                    correctAnswers = 0,
                    selectedAnswer = null
                )
            }

            is GameEvent.LoadFailure -> {
                Error(message = event.message)
            }

            is GameEvent.SelectAnswer -> {
                if (currentState is QuestionActive) {
                    currentState.copy(selectedAnswer = event.answer)
                } else {
                    currentState
                }
            }

            is GameEvent.SubmitAnswer -> {
                if (currentState is QuestionActive) {
                    val selected = currentState.selectedAnswer
                    val isCorrect = selected == currentState.question.correctAnswer
                    val newCorrectAnswers =
                        if (isCorrect) currentState.correctAnswers + 1 else currentState.correctAnswers
                    AnswerResult(
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
                if (currentState is AnswerResult) {
                    if (currentState.hasMoreQuestions) {
                        val nextIndex = currentState.currentQuestionIndex + 1
                        QuestionActive(
                            questions = currentState.questions,
                            currentQuestionIndex = nextIndex,
                            correctAnswers = currentState.correctAnswers,
                            selectedAnswer = null
                        )
                    } else {
                        Finished(
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
                    is QuestionActive -> currentState.questions
                    is AnswerResult -> currentState.questions
                    is Finished -> currentState.questions
                    else -> emptyList()
                }

                if (questionsToUse.isNotEmpty()) {
                    QuestionActive(
                        questions = questionsToUse,
                        currentQuestionIndex = 0,
                        correctAnswers = 0,
                        selectedAnswer = null
                    )
                } else {
                    LoadingQuestions
                }
            }

            is GameEvent.InitialConfigLoaded -> {
                DefiningAiConfiguration(
                    initialConfig = event.firebaseAiConfig,
                )
            }
        }
    }
}
