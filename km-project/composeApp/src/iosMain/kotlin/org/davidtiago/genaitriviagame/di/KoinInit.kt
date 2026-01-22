package org.davidtiago.genaitriviagame.di

import org.davidtiago.genaitriviagame.repository.gemini.GeminiApi
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(geminiApi: GeminiApi) {
    startKoin {
        modules(
            mainModule,
            module {
                single { geminiApi }
            },
        )
    }
}
