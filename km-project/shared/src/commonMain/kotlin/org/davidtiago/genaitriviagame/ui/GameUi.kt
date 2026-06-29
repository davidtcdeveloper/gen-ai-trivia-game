package org.davidtiago.genaitriviagame.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.davidtiago.genaitriviagame.presentation.GameState

//TODO: Change this from composable state handling to a navigation pattern
@Composable
fun GameUi(
    viewModel: GameViewModel = koinViewModel(),
    modifier: Modifier = Modifier.fillMaxSize(),
) {
    val state by viewModel.gameState.collectAsState()

    Column(modifier) {
        when (val currentState = state) {
            is GameState.Loading -> {
                LoadingScreen()
            }

            is GameState.Error -> {
                ErrorScreen(onRetry = viewModel::loadQuestions)
            }

            is GameState.Finished -> {
                GameResultsUi(
                    score = currentState.score,
                    totalQuestions = currentState.totalQuestions,
                    onRestart = viewModel::restartGame
                )
            }

            is GameState.AnswerResult -> {
                ResultCardUi(
                    selectedAnswer = currentState.selectedAnswer,
                    question = currentState.question,
                    onNextQuestion = viewModel::onNextQuestion,
                    hasMoreQuestions = currentState.hasMoreQuestions
                )
            }

            is GameState.QuestionActive -> {
                QuestionComposable(
                    question = currentState.question,
                    selectedAnswer = currentState.selectedAnswer,
                    onAnswerSelected = viewModel::onAnswerSelected,
                    onSubmit = viewModel::onSubmit,
                )
            }
        }
    }
}
