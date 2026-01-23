package org.davidtiago.genaitriviagame.di

import org.davidtiago.genaitriviagame.repository.gemini.GeminiApi
import org.koin.core.context.startKoin
import org.koin.dsl.module

// Invoked from Swift code on iOS
@Suppress("unused")
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
