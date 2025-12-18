package org.davidtiago.genaitriviagame.di

import org.davidtiago.genaitriviagame.defaultGeminiModel
import org.davidtiago.genaitriviagame.defaultQuestionsPrompt
import org.davidtiago.genaitriviagame.gemini.AndroidGeminiApi
import org.davidtiago.genaitriviagame.repository.gemini.GeminiApi
import org.koin.dsl.bind
import org.koin.dsl.module

val androidMainModule = module {
    single<AndroidGeminiApi> {
        AndroidGeminiApi(
            model = defaultGeminiModel,
            prompt = defaultQuestionsPrompt,
        )
    } bind GeminiApi::class
}