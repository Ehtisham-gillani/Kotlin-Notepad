package com.example.mynotepad

import android.app.Application
import com.example.mynotepad.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class NotepadApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NotepadApp)
            modules(appModule)
        }
    }
}