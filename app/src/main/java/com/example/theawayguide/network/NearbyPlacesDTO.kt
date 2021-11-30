package com.example.theawayguide.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NearbyPlacesDTO(

    // Needed for future pagination
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

    @SerializedName("rating")
    @Expose
    val rating: Double? = null,

    @SerializedName("place_id")
    @Expose
    val placeId: String? = null,

    @SerializedName("user_ratings_total")
    @Expose
    val userRatingsTotal: Int? = null,

    @SerializedName("vicinity")
    @Expose
    val vicinity: String? = null
)

class PhotoDTO(

    @SerializedName("photo_reference")
    @Expose
    var photoReference: String? = null

)
