package com.myapp.connectionsample.di

import com.myapp.connectionsample.data.repository.HttpRepository
import com.myapp.connectionsample.data.repository.HttpRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryComponentModule {

    @Binds
    abstract fun bindHttpRepository(httpRepositoryImpl: HttpRepositoryImpl): HttpRepository
}