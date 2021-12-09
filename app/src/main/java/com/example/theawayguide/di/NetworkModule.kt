package com.example.theawayguide.di

import android.content.Context
import com.example.theawayguide.network.FirebaseService
import com.example.theawayguide.network.NetworkConnectionInterceptor
import com.example.theawayguide.network.RetrofitService
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideFirebaseService(): FirebaseService {
        val database = FirebaseDatabase.getInstance()
        database.setPersistenceEnabled(true)
        return FirebaseService(database)
    }

    @Singleton
    @Provides
    fun provideRetrofitService(client: OkHttpClient): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(networkConnectionInterceptor: NetworkConnectionInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkConnectionInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkConnectionInterceptor(context: Context): Interceptor {
        return NetworkConnectionInterceptor(context)
    }
}
