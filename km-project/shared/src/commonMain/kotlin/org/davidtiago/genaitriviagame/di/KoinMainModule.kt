package org.davidtiago.genaitriviagame.di

import org.davidtiago.genaitriviagame.presentation.GameStateMachine
import org.davidtiago.genaitriviagame.presentation.GameStateMachineImpl
import org.davidtiago.genaitriviagame.repository.QuestionRepository
import org.davidtiago.genaitriviagame.repository.gemini.GeminiQuestionRepository
import org.davidtiago.genaitriviagame.ui.GameViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    //TODO: Separate this into a module for a different variant
   singleOf(::GeminiQuestionRepository){ bind<QuestionRepository>() }
    
    // 1. Register InMemoryMockQuestionRepository explicitly
    //single<QuestionRepository> { InMemoryMockQuestionRepository() }
    
    // 2. Register GameStateMachine explicitly with factory scope
    factory<GameStateMachine> { GameStateMachineImpl() }
    
    // 3. Register GameViewModel explicitly via non-reflective DSL
    viewModel { GameViewModel(get(), get()) }
}
