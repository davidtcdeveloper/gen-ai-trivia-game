package org.davidtiago.genaitriviagame.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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

@Preview
@Composable
private fun CorrectAnswerCardPreview() {
    MaterialTheme {
        CorrectAnswerCard(selectedAnswer = "Paris")
    }
}

@Preview
@Composable
private fun WrongAnswerCardPreview() {
    MaterialTheme {
        WrongAnswerCard(selectedAnswer = "London", correctAnswer = "Paris")
    }
}
