package org.davidtiago.genaitriviagame.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

//TODO: Change this from composable state handling to a navigation pattern
@Composable
fun GameUi(
    viewModel: GameViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    val currentQuestion = if (viewModel.questions.isNotEmpty()) {
        viewModel.getCurrentQuestion()
    } else {
        null
    }
    Column(modifier) {
        when {
            viewModel.isLoading -> {
                LoadingScreen()
            }

            viewModel.questions.isEmpty() -> {
                ErrorScreen(onRetry = viewModel::loadQuestions)
            }

            viewModel.isGameFinished -> {
                GameResultsUi(
                    score = viewModel.score,
                    totalQuestions = viewModel.totalQuestions,
                    onRestart = viewModel::restartGame
                )
            }

            viewModel.isSubmitted -> {
                ResultCardUi(
                    selectedAnswer = viewModel.selectedAnswer,
                    question = currentQuestion!!,
                    onNextQuestion = viewModel::onNextQuestion,
                    hasMoreQuestions = viewModel.hasMoreQuestions
                )
            }

            else -> {
                QuestionComposable(
                    question = currentQuestion!!,
                    selectedAnswer = viewModel.selectedAnswer,
                    onAnswerSelected = viewModel::onAnswerSelected,
                    onSubmit = viewModel::onSubmit,
                )
            }
        }
    }
}
