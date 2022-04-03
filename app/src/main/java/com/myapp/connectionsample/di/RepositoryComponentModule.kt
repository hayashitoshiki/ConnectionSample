package com.myapp.connectionsample.di

import com.myapp.connectionsample.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryComponentModule {

    @Binds
    abstract fun bindHttpRepository(httpRepositoryImpl: HttpRepositoryImpl): HttpRepository
    @Binds
    abstract fun bindSocketRepository(socketRepositoryImpl: SocketRepositoryImpl): SocketRepository
    @Binds
    abstract fun bindGrpcRepository(grpcRepositoryImpl: GrpcRepositoryImpl): GrpcRepository
}