package com.myapp.connectionsample.di

import com.myapp.connectionsample.MyApplication
import com.myapp.connectionsample.data.remote.SocketManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppComponentModule {

    // coroutine
    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return MyApplication.shared.applicationScope
    }

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    // Database
    @Provides
    @Singleton
    fun provideSocketManager(
        coroutineScope: CoroutineScope,
        CoroutineDispatcher: CoroutineDispatcher
    ): SocketManager {
        return SocketManager(coroutineScope, CoroutineDispatcher)
    }

}
