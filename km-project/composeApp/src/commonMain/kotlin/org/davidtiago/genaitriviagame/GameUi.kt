package org.davidtiago.genaitriviagame

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.davidtiago.genaitriviagame.model.Question

@Composable
private fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Loading questions...",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun ErrorScreen(onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Failed to load questions",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
fun QuestionGame() {
    val viewModel = remember { GameViewModel() }
    
    when {
        viewModel.isLoading -> {
            LoadingScreen()
        }
        viewModel.questions.isEmpty() -> {
            ErrorScreen(onRetry = viewModel::loadQuestions)
        }
        viewModel.isGameFinished -> {
            GameResults(
                score = viewModel.score,
                totalQuestions = viewModel.totalQuestions,
                onRestart = viewModel::restartGame
            )
        }
        else -> {
            val question = viewModel.getCurrentQuestion()
            
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                QuestionText(question)
                AnimatedVisibility(
                    visible = !viewModel.isSubmitted,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column {
                        AnswerOptions(
                            question = question,
                            selectedAnswer = viewModel.selectedAnswer,
                            onAnswerSelected = viewModel::onAnswerSelected
                        )
                        SubmitButton(
                            selectedAnswer = viewModel.selectedAnswer,
                            onSubmit = viewModel::onSubmit
                        )
                    }
                }
                ResultCard(
                    isSubmitted = viewModel.isSubmitted,
                    selectedAnswer = viewModel.selectedAnswer,
                    question = question,
                    onNextQuestion = viewModel::onNextQuestion,
                    hasMoreQuestions = viewModel.hasMoreQuestions
                )
            }
        }
    }
}

@Composable
internal fun QuestionText(question: Question) {
    Text(
        text = question.text,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
internal fun AnswerOptions(
    question: Question,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit
) {
    question.options.forEach { option ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(
                    width = 1.dp,
                    color = if (selectedAnswer == option) {
                        MaterialTheme.colors.primary
                    } else MaterialTheme.colors.onSurface.copy(
                        alpha = 0.12f
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                    onAnswerSelected(option)
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedAnswer == option,
                onClick = { onAnswerSelected(option) },

                )
            Text(
                text = option,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
internal fun SubmitButton(
    selectedAnswer: String?,
    onSubmit: () -> Unit
) {
    Button(
        onClick = onSubmit,
        enabled = selectedAnswer != null,
        modifier = Modifier.padding(top = 16.dp),
    ) {
        Text("Submit Answer")
    }
}

@Composable
internal fun ResultCard(
    isSubmitted: Boolean,
    selectedAnswer: String?,
    question: Question,
    onNextQuestion: () -> Unit,
    hasMoreQuestions: Boolean
) {
    if (isSubmitted) {
        val isCorrect = selectedAnswer == question.correctAnswer
        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + expandVertically()
        ) {
            Column {
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
                }
            }
        }
    }
}

@Composable
internal fun CorrectAnswerCard(selectedAnswer: String?) {
    Card(
        backgroundColor = Color.Green.copy(alpha = 0.1f),
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Congratulations! '$selectedAnswer' is correct!",
                color = Color.Green.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
internal fun WrongAnswerCard(selectedAnswer: String?, correctAnswer: String) {
    Card(
        backgroundColor = Color.Red.copy(alpha = 0.1f),
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Sorry, '$selectedAnswer' is incorrect. The correct answer is '$correctAnswer'.",
                color = Color.Red.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun GameResults(
    score: Int,
    totalQuestions: Int,
    onRestart: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Quiz Complete!",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Your Score",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    "$score out of $totalQuestions",
                    style = MaterialTheme.typography.h3,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    "Correct Answers: $score",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    "Incorrect Answers: ${totalQuestions - score}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                Button(
                    onClick = onRestart,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Play Again")
                }
            }
        }
    }
}
