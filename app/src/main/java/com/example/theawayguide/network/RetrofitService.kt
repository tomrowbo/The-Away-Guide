package com.example.theawayguide.network

import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("api/place/nearbysearch/json")
    suspend fun nearbyPlaceSearch(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("key") key: String
    ): NearbyPlacesDTO

    @GET("api/place/details/json")
    suspend fun placeDetailsSearch(
        @Query("place_id") placeId: String,
        @Query("key") key: String
    ): PlaceDetailsDTO
}
