package com.example.theawayguide.di

import com.example.theawayguide.network.FirebaseService
import com.example.theawayguide.network.RetrofitService
import com.example.theawayguide.repository.TeamRepository
import com.example.theawayguide.repository.TeamRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTeamRepository(
        firebaseService: FirebaseService,
        retrofitService: RetrofitService
    ): TeamRepository {
        return TeamRepositoryImpl(
            firebaseService,
            retrofitService
        )
    }
}
