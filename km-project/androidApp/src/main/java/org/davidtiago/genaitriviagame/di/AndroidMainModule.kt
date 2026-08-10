package org.davidtiago.genaitriviagame.di

import org.davidtiago.genaitriviagame.defaultGeminiModel
import org.davidtiago.genaitriviagame.defaultQuestionsPrompt
import org.davidtiago.genaitriviagame.gemini.AndroidGeminiApi
import org.davidtiago.genaitriviagame.gemini.FirebaseAiBuilder
import org.davidtiago.genaitriviagame.repository.gemini.GeminiApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val androidMainModule = module {

    factory<FirebaseAiBuilder> {
        FirebaseAiBuilder(
            firebaseAiConfigRepository = get(),
            context = androidContext(),
        ) }

    single<AndroidGeminiApi> {
        AndroidGeminiApi(
            model = defaultGeminiModel,
            prompt = defaultQuestionsPrompt,
            aiBuilder = get()
        )
    } bind GeminiApi::class

}