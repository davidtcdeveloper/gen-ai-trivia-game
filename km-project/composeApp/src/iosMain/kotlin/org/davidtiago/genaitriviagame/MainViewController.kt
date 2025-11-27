package org.davidtiago.genaitriviagame

import androidx.compose.ui.window.ComposeUIViewController
import org.davidtiago.genaitriviagame.repository.QuestionRepository

fun MainViewController(questionRepository: QuestionRepository) =
    ComposeUIViewController {
        App(questionRepository)
    }