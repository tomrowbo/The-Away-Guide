package com.example.theawayguide.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.example.theawayguide.BuildConfig
import com.example.theawayguide.R
import com.example.theawayguide.network.RetrofitService
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRetrofit()
    }

    private fun setupRetrofit(){
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client : OkHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
        }.build()


        val service = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
            .create(RetrofitService::class.java)

        CoroutineScope(IO).launch{
            val response = service.nearbyPlaceSearch(
                "53.463100,-2.291300",
                1500,
                "restaurant",
                BuildConfig.MAPS_API_KEY
            )
        }
    }
}
