package org.davidtiago.genaitriviagame

import android.app.Application
import org.davidtiago.genaitriviagame.di.androidMainModule
import org.davidtiago.genaitriviagame.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AndroidApp)
            modules(
                androidMainModule,
                mainModule,
            )
        }
    }
}
