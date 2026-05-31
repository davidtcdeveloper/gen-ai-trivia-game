package org.davidtiago.genaitriviagame.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.davidtiago.genaitriviagame.model.Question

@Composable
internal fun ResultCardUi(
    selectedAnswer: String?,
    question: Question,
    onNextQuestion: () -> Unit,
    hasMoreQuestions: Boolean
) {
    val isCorrect = selectedAnswer == question.correctAnswer
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
            if (hasMoreQuestions) {
                Button(
                    onClick = onNextQuestion,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Next Question")
                }
            } else {
                Button(
                    onClick = onNextQuestion,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("View Results")
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