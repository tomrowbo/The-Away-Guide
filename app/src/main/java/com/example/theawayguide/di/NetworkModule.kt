package com.example.theawayguide.di

import android.content.Context
import com.example.theawayguide.network.FirebaseService
import com.example.theawayguide.network.RetrofitService
import com.example.theawayguide.network.hasNetwork
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
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
    fun provideOkHttpClient(context: Context): OkHttpClient {
        val cacheSize = (50 * 1024 * 1024).toLong() //50MiB - $0.05 worth of phone storage
        val cache = Cache(context.cacheDir, cacheSize)
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(context)!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 30).build()
                chain.proceed(request)
            }
            .build()
    }
}
