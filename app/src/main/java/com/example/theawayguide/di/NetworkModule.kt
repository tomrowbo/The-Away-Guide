package com.example.theawayguide.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

//    @Singleton
//    @Provides
//    fun provideRecipeMapper(): RecipeDtoMapper {
//        return RecipeDtoMapper()
//    }
//
//    @Singleton
//    @Provides
//    fun provideRecipeService(): RecipeService {
//        return Retrofit.Builder()
//            .baseUrl("https://food2fork.ca/api/recipe/")
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(RecipeService::class.java)
//    }
//
//    /**
//     * I might include proper authentication later on food2fork.ca
//     * For now just hard code a token.
//     */
//    @Singleton
//    @Provides
//    @Named("auth_token")
//    fun provideAuthToken(): String{
//        return "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
//    }
}
