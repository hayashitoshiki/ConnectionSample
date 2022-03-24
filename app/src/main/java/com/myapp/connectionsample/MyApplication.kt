package com.myapp.connectionsample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.MainScope

@HiltAndroidApp
class MyApplication : Application() {
    companion object {
        lateinit var shared: MyApplication
    }

    init {
        shared = this
    }

    // Global Scope
    val applicationScope = MainScope()
}
