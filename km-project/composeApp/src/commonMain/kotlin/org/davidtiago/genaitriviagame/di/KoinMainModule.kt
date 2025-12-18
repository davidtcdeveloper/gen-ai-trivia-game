package org.davidtiago.genaitriviagame.di

import org.davidtiago.genaitriviagame.GameViewModel
import org.davidtiago.genaitriviagame.repository.QuestionRepository
import org.davidtiago.genaitriviagame.repository.gemini.GeminiQuestionRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val mainModule = module {
    singleOf(::GeminiQuestionRepository){ bind<QuestionRepository>() }

    viewModelOf(::GameViewModel)
}
