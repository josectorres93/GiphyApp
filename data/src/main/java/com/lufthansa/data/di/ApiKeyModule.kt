package com.lufthansa.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiKeyModule {
    private const val API_KEY = "F0c62RChLtOm5o7FXKYQBWXrX0YdCyB8"

    @Provides
    @Singleton
    @Named("GiphyApiKey")
    fun provideApiKey(): String = API_KEY
}