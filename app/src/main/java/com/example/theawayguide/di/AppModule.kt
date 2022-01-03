package com.example.theawayguide.di

import android.content.Context
import android.content.SharedPreferences
import com.example.theawayguide.R
import com.example.theawayguide.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Provides
    @Singleton
    fun provideContext(application: BaseApplication): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(application: BaseApplication, context: Context): SharedPreferences? {
        return application.getSharedPreferences(context.getString(R.string.prefence_key), Context.MODE_PRIVATE)
    }
}
