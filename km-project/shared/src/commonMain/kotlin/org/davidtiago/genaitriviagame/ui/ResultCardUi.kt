package org.davidtiago.genaitriviagame.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.davidtiago.genaitriviagame.model.Question

@Composable
internal fun ResultCardUi(
    selectedAnswer: String?,
    question: Question,
    onNextQuestion: () -> Unit,
    hasMoreQuestions: Boolean
) {
    val isCorrect = selectedAnswer == question.correctAnswer
    var timeLeft by remember(question) { mutableStateOf(10) }

    LaunchedEffect(question) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
        onNextQuestion()
    }

    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + expandVertically()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(all = 16.dp)
        ) {
            if (isCorrect) {
                CorrectAnswerCard(selectedAnswer)
            } else {
                WrongAnswerCard(selectedAnswer, question.correctAnswer)
            }
            
            val buttonText = if (hasMoreQuestions) "Next Question" else "View Results"
            Button(
                onClick = onNextQuestion,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(buttonText)
                    Box(contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            progress = timeLeft / 10f,
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colors.onPrimary,
                            strokeWidth = 2.dp
                        )
                        Text(
                            text = "$timeLeft",
                            style = MaterialTheme.typography.caption.copy(
                                color = MaterialTheme.colors.onPrimary
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ResultCardUiCorrectPreview() {
    MaterialTheme {
        ResultCardUi(
            selectedAnswer = "Paris",
            question = Question(
                text = "What is the capital of France?",
                options = listOf("London", "Berlin", "Paris", "Madrid"),
                correctAnswer = "Paris"
            ),
            onNextQuestion = {},
            hasMoreQuestions = true
        )
    }
}

@Preview
@Composable
private fun ResultCardUiIncorrectPreview() {
    MaterialTheme {
        ResultCardUi(
            selectedAnswer = "London",
            question = Question(
                text = "What is the capital of France?",
                options = listOf("London", "Berlin", "Paris", "Madrid"),
                correctAnswer = "Paris"
            ),
            onNextQuestion = {},
            hasMoreQuestions = false
        )
    }
}