package org.davidtiago.genaitriviagame.di

import org.davidtiago.genaitriviagame.presentation.GameStateMachine
import org.davidtiago.genaitriviagame.presentation.GameStateMachineImpl
import org.davidtiago.genaitriviagame.repository.FirebaseAiConfigRepository
import org.davidtiago.genaitriviagame.repository.InMemoryFirebaseAiConfigRepository
import org.davidtiago.genaitriviagame.repository.QuestionRepository
import org.davidtiago.genaitriviagame.repository.gemini.GeminiQuestionRepository
import org.davidtiago.genaitriviagame.ui.GameViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    //TODO: Separate this into a module for a different build variant
    singleOf(::GeminiQuestionRepository) {
        bind<QuestionRepository>()
    }
    //single<QuestionRepository> { InMemoryMockQuestionRepository() }
    //TODO: Create a repository model
    singleOf(::InMemoryFirebaseAiConfigRepository) {
        bind<FirebaseAiConfigRepository>()
    }

    factory<GameStateMachine> {
        GameStateMachineImpl()
    }
    viewModel {
        GameViewModel(
            stateMachine = get(),
            questionRepository = get(),
            firebaseAiConfigRepository = get()
        )
    }
}
