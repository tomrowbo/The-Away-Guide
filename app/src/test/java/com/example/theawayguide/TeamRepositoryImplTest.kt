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

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        // TODO: Broken as cannot mock firebase
        teamRepositoryImpl = TeamRepositoryImpl(mockk(), retrofitService)
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

    fun getMockMapsDTO(): MapsDTO {
        return MapsDTO(
            NEXT_PAGE_TOKEN,
            listOf(
                PlaceDTO(
                    name = PLACE_NAME,
                    photos = listOf(PhotoDTO(PHOTO_REFERENCE)),
                    geometry = GeometryDTO(LocationDTO(lat = 1.0, lng = 2.0)),
                    rating = 3.0,
                    priceLevel = 1,
                    userRatingsTotal = 465,
                    types = emptyList(),
                    vicinity = VICINITY
                ),
                PlaceDTO(
                    name = PLACE_NAME2,
                    photos = listOf(PhotoDTO(PHOTO_REFERENCE)),
                    geometry = GeometryDTO(LocationDTO(lat = 1.0, lng = 2.0)),
                    rating = 3.0,
                    priceLevel = 1,
                    userRatingsTotal = 465,
                    types = emptyList(),
                    vicinity = VICINITY
                )
            )
        )
    }

    fun getMockAttractionsList(): List<Attraction> {
        return listOf(
            Attraction(
                name = PLACE_NAME2,
                imageUrl = PHOTO_REFERENCE,
                rating = RATING,
                priceLevel = PRICE_LEVEL,
                address = VICINITY,
                tags = emptyList(),
                latitude = LATITUDE,
                longitude = LONGITUDE,
                totalRatings = TOTAL_RATINGS
            ),
            Attraction(
                name = PLACE_NAME,
                imageUrl = PHOTO_REFERENCE,
                rating = RATING,
                priceLevel = PRICE_LEVEL,
                address = VICINITY,
                tags = emptyList(),
                latitude = LATITUDE,
                longitude = LONGITUDE,
                totalRatings = TOTAL_RATINGS
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
