package com.example.theawayguide.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MapsDTO(
    @SerializedName("next_page_token")
    @Expose
    var nextPageToken: String? = null,

    @SerializedName("results")
    @Expose
    var results: List<PlaceDTO>? = null
)

class PlaceDTO(

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("photos")
    @Expose
    val photos: List<PhotoDTO>? = null,

    @SerializedName("geometry")
    @Expose
    val geometry: GeometryDTO? = null,

    @SerializedName("rating")    
    @Expose    
    val rating: Double? = null,

    @SerializedName("price_level")
    @Expose
    val priceLevel: Int? = null,

    @SerializedName("user_ratings_total")
    @Expose
    val userRatingsTotal: Int? = null,

    @SerializedName("types")    
    @Expose    
    val types: kotlin.collections.List<String>? = null,

    @SerializedName("vicinity")    
    @Expose    
    val vicinity: String? = null
)

class GeometryDTO(
    @SerializedName("location")
    @Expose
    var location: LocationDTO? = null,
)

class LocationDTO(
    @SerializedName("lat")
    @Expose
    var lat: Double? = null,

    @SerializedName("lng")
    @Expose
    var lng: Double? = null
)

class PhotoDTO(

    @SerializedName("photo_reference")    
    @Expose    
    var photoReference: String? = null

)
