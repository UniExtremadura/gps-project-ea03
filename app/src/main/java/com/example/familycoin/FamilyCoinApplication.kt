package com.example.familycoin

import android.app.Application
import com.example.familycoin.utils.AppContainer

class FamilyCoinApplication : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}