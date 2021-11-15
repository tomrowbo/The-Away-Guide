package com.example.theawayguide.di

import com.example.theawayguide.network.FirebaseService
import com.example.theawayguide.network.util.TeamMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideTeamMapper(): TeamMapper {
        return TeamMapper()
    }

    @Singleton
    @Provides
    fun provideFirebaseService(): FirebaseService {
        return FirebaseService
    }
}
