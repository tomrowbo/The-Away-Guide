package com.example.theawayguide.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PlaceDetailsDTO(
    @SerializedName("result")
    @Expose
    var result: Result? = null,
)

class Result(

    @SerializedName("formatted_address")
    @Expose
    var formattedAddress: String? = null,

    @SerializedName("formatted_phone_number")
    @Expose
    var formattedPhoneNumber: String? = null,

    @SerializedName("geometry")
    @Expose
    var geometry: Geometry? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("opening_hours")
    @Expose
    var openingHours: OpeningHours? = null,

    @SerializedName("photos")
    @Expose
    var photos: List<Photo>? = null,

    @SerializedName("place_id")
    @Expose
    var placeId: String? = null,

    @SerializedName("price_level")
    @Expose
    var priceLevel: Int? = null,

    @SerializedName("rating")
    @Expose
    var rating: Double? = null,

    @SerializedName("types")
    @Expose
    var types: List<String>? = null,

    @SerializedName("user_ratings_total")
    @Expose
    var userRatingsTotal: Int? = null,

    @SerializedName("website")
    @Expose
    var website: String? = null
)

class Geometry {
    @SerializedName("location")
    @Expose
    var location: Location? = null
}

class Location {
    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lng")
    @Expose
    var lng: Double? = null
}

class OpeningHours {
    @SerializedName("open_now")
    @Expose
    var openNow: Boolean? = null

    @SerializedName("weekday_text")
    @Expose
    var weekdayText: List<String>? = null
}

class Photo {
    @SerializedName("photo_reference")
    @Expose
    var photoReference: String? = null
}
