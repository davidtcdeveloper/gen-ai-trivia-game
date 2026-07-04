package org.davidtiago.genaitriviagame.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.davidtiago.genaitriviagame.model.Question

@Composable
internal fun QuestionComposable(
    question: Question,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuestionText(questionText = question.text)

        AnswerOptions(
            options = question.options,
            selectedAnswer = selectedAnswer,
            onAnswerSelected = onAnswerSelected,
            modifier = Modifier.weight(1f)
        )
        SubmitButton(
            selectedAnswer = selectedAnswer,
            onSubmit = onSubmit
        )
    }
}

@Composable
internal fun QuestionText(questionText: String) {
    Text(
        text = questionText,
        style = MaterialTheme.typography.h6,
        modifier = Modifier.padding(bottom = 16.dp)
    )
}

@Composable
internal fun AnswerOptions(
    options: List<String>,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(options, key = { it }) { option ->
            val isSelected = selectedAnswer == option
            val onClick = remember(option, onAnswerSelected) {
                { onAnswerSelected(option) }
            }

            AnswerOptionRow(
                option = option,
                isSelected = isSelected,
                onClick = onClick
            )
        }
    }
}

@Composable
internal fun AnswerOptionRow(
    option: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(
                width = 1.dp,
                color = if (isSelected) {
                    MaterialTheme.colors.primary
                } else {
                    MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick
        )
        Text(
            text = option,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
internal fun SubmitButton(
    selectedAnswer: String?,
    onSubmit: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Button(
            onClick = onSubmit,
            enabled = selectedAnswer != null,
            modifier = Modifier.padding(top = 16.dp),
        ) {
            Text("Submit Answer")
        }
    }
}

@Preview
@Composable
fun QuestionComposablePreview() {
    MaterialTheme {
        QuestionComposable(
            question = Question(
                text = "What is the capital of France?",
                options = listOf("London", "Berlin", "Paris", "Madrid"),
                correctAnswer = "Paris"
            ),
            selectedAnswer = null,
            onAnswerSelected = {},
            onSubmit = {}
        )
    }
}
