package org.davidtiago.genaitriviagame.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.davidtiago.genaitriviagame.model.Question
import org.davidtiago.genaitriviagame.repository.QuestionRepository

class GameViewModel(
    private val questionRepository: QuestionRepository
) : ViewModel() {
    private var questions: List<Question> = emptyList()
    private var isLoading = true
    private var currentQuestionIndex = 0
    private var selectedAnswer: String? = null
    private var isSubmitted = false
    private var isGameFinished = false
    private var correctAnswers = 0

    private val _gameState = MutableStateFlow<GameState>(GameState.Loading)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    init {
        loadQuestions()
    }

    fun loadQuestions() {
        viewModelScope.launch {
            isLoading = true
            updateState()
            
            questions = questionRepository.getQuestions()
            isLoading = false
            updateState()
        }
    }

    private val hasMoreQuestions: Boolean
        get() = currentQuestionIndex < questions.size - 1

    private fun getCurrentQuestion(): Question = questions[currentQuestionIndex]

    private fun updateState() {
        _gameState.value = when {
            isLoading -> GameState.Loading
            questions.isEmpty() -> GameState.Error()
            isGameFinished -> GameState.Finished(
                score = correctAnswers,
                totalQuestions = questions.size
            )
            isSubmitted -> {
                val currentQuestion = getCurrentQuestion()
                GameState.AnswerResult(
                    question = currentQuestion,
                    selectedAnswer = selectedAnswer,
                    isCorrect = selectedAnswer == currentQuestion.correctAnswer,
                    hasMoreQuestions = hasMoreQuestions
                )
            }
            else -> GameState.QuestionActive(
                question = getCurrentQuestion(),
                selectedAnswer = selectedAnswer,
                currentQuestionIndex = currentQuestionIndex,
                totalQuestions = questions.size
            )
        }
    }

    fun onAnswerSelected(answer: String) {
        selectedAnswer = answer
        updateState()
    }

    fun onSubmit() {
        isSubmitted = true
        if (selectedAnswer == getCurrentQuestion().correctAnswer) {
            correctAnswers++
        }
        updateState()
    }

    fun onNextQuestion() {
        if (hasMoreQuestions) {
            currentQuestionIndex++
            selectedAnswer = null
            isSubmitted = false
        } else {
            isGameFinished = true
        }
        updateState()
    }

    fun restartGame() {
        currentQuestionIndex = 0
        selectedAnswer = null
        isSubmitted = false
        isGameFinished = false
        correctAnswers = 0
        updateState()
    }
}
