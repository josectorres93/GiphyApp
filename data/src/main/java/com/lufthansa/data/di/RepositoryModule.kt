package com.lufthansa.data.di

import com.lufthansa.data.repository.GiphyRepositoryImpl
import com.lufthansa.domain.repository.GiphyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGiphyRepository(
        impl: GiphyRepositoryImpl
    ): GiphyRepository
}