package com.example.theawayguide

import com.example.theawayguide.domain.Attraction
import com.example.theawayguide.network.*
import com.example.theawayguide.repository.TeamRepositoryImpl
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class TeamRepositoryImplTest {

    private lateinit var teamRepositoryImpl: TeamRepositoryImpl

    @MockK
    private lateinit var retrofitService: RetrofitService

    @MockK
    private lateinit var firebaseService: FirebaseService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        teamRepositoryImpl = TeamRepositoryImpl(firebaseService, retrofitService)
    }

    @Test
    fun WHEN_getRestaurants_THEN_returnListAttractions() {
        runBlocking {
            // GIVEN
            coEvery {
                retrofitService.nearbyPlaceSearch(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns getMockMapsDTO()

            // WHEN
            val attractions = teamRepositoryImpl.getRestaurants(LATITUDE, LONGITUDE, RADIUS)

            // THEN
            assertEquals(attractions, getMockAttractionsList())
        }
    }

    @Test
    fun WHEN_getPubs_THEN_returnListAttractions() {
        runBlocking {
            // GIVEN
            coEvery {
                retrofitService.nearbyPlaceSearch(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns getMockMapsDTO()

            // WHEN
            val attractions = teamRepositoryImpl.getPubs(LATITUDE, LONGITUDE, RADIUS)

            // THEN
            assertEquals(attractions, getMockAttractionsList())
        }
    }

    @Test
    fun WHEN_getHotels_THEN_returnListAttractions() {
        runBlocking {
            // GIVEN
            coEvery {
                retrofitService.nearbyPlaceSearch(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns getMockMapsDTO()

            // WHEN
            val attractions = teamRepositoryImpl.getHotels(LATITUDE, LONGITUDE, RADIUS)

            // THEN
            assertEquals(attractions, getMockAttractionsList())
        }
    }

    private fun getMockMapsDTO(): NearbyPlacesDTO {
        return NearbyPlacesDTO(
            NEXT_PAGE_TOKEN,
            listOf(
                PlaceDTO(
                    name = PLACE_NAME,
                    photos = listOf(PhotoDTO(PHOTO_REFERENCE)),
                    rating = RATING,
                    userRatingsTotal = TOTAL_RATINGS,
                    vicinity = VICINITY
                ),
                PlaceDTO(
                    name = PLACE_NAME2,
                    photos = listOf(PhotoDTO(PHOTO_REFERENCE)),
                    rating = RATING,
                    userRatingsTotal = TOTAL_RATINGS,
                    vicinity = VICINITY
                )
            )
        )
    }

    private fun getMockAttractionsList(): List<Attraction> {
        return listOf(
            Attraction(
                name = PLACE_NAME2,
                imageUrl = PHOTO_REFERENCE,
                address = VICINITY,
                rating = RATING,
                placeId = "",
                totalRatings = TOTAL_RATINGS,
                priceLevel = PRICE_LEVEL
            ),
            Attraction(
                name = PLACE_NAME,
                imageUrl = PHOTO_REFERENCE,
                address = VICINITY,
                rating = RATING,
                placeId = "",
                totalRatings = TOTAL_RATINGS,
                priceLevel = PRICE_LEVEL
            )
        )
    }

    companion object {
        const val PLACE_NAME = "place_name"
        const val PLACE_NAME2 = "place_name2"
        const val PHOTO_REFERENCE = "photo_reference"
        const val VICINITY = "vicinity"
        const val RATING = 3.0
        const val PRICE_LEVEL = 1
        const val LATITUDE = 1.0
        const val LONGITUDE = 2.0
        const val TOTAL_RATINGS = 456
        const val NEXT_PAGE_TOKEN = "next_page_token"
        const val RADIUS = 999
    }
}
