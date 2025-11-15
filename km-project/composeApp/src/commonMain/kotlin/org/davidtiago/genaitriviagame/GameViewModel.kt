package org.davidtiago.genaitriviagame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.davidtiago.genaitriviagame.model.Question
import org.davidtiago.genaitriviagame.repository.GeminiQuestionRepository

class GameViewModel {
    // TODO: Dependency Injection
    private val questionRepository = GeminiQuestionRepository()
    var questions by mutableStateOf<List<Question>>(emptyList())
        private set
    var isLoading by mutableStateOf(true)
        private set

    //TODO improve overall coroutines handling
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private var currentQuestionIndex by mutableStateOf(0)
    var selectedAnswer by mutableStateOf<String?>(null)
    var isSubmitted by mutableStateOf(false)
    var isGameFinished by mutableStateOf(false)
    private var correctAnswers by mutableStateOf(0)

    init {
        loadQuestions()
    }

    fun loadQuestions() {
        scope.launch {
            isLoading = true
            questions = questionRepository.getQuestions()
            isLoading = false
        }
    }

    val hasMoreQuestions: Boolean
        get() = currentQuestionIndex < questions.size - 1

    val totalQuestions: Int
        get() = questions.size

    val score: Int
        get() = correctAnswers

    fun getCurrentQuestion(): Question = questions[currentQuestionIndex]

    fun onAnswerSelected(answer: String) {
        selectedAnswer = answer
    }

    fun onSubmit() {
        isSubmitted = true
        if (selectedAnswer == getCurrentQuestion().correctAnswer) {
            correctAnswers++
        }
        if (!hasMoreQuestions) {
            isGameFinished = true
        }
    }

    fun onNextQuestion() {
        if (hasMoreQuestions) {
            currentQuestionIndex++
            selectedAnswer = null
            isSubmitted = false
        }
    }

    fun restartGame() {
        currentQuestionIndex = 0
        selectedAnswer = null
        isSubmitted = false
        isGameFinished = false
        correctAnswers = 0
    }

    fun onDestroy() {
        //TODO cancel the scope
    }
}