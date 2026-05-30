package org.davidtiago.genaitriviagame.di

import org.davidtiago.genaitriviagame.ui.GameViewModel
import org.davidtiago.genaitriviagame.repository.QuestionRepository
import org.davidtiago.genaitriviagame.repository.gemini.GeminiQuestionRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    //TODO: Separate this into a module for a different variant
    singleOf(::GeminiQuestionRepository){ bind<QuestionRepository>() }
//    singleOf(::InMemoryMockQuestionRepository){ bind<QuestionRepository>() }
    viewModelOf(::GameViewModel)
}
