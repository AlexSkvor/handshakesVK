package ru.lingstra.avitocopy.system.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.lingstra.avitocopy.data.SearchCarsResultList

interface NetworkApi {

    @GET("search/cars")
    fun getCars(
        @Query("skip") skip: Int,
        @Query("take") take: Int,
        @Query("colors") colors: List<String>,
        @Query("bodyTypes") bodyTypes: List<String>,
        @Query("cities") cities: List<String>,
        @Query("sources") sources: List<String>,
        @Query("minPrice") minPrice: Int,
        @Query("maxPrice") maxPrice: Int,
        @Query("minYear") minYear: Int,
        @Query("maxYear") maxYear: Int,
        @Query("filterResellers") filterResellers: Boolean,
        @Query("sort") sort: String
    ): Single<SearchCarsResultList>
    /**
    @RequestParam(value = "tradeMarksRequest", required = false, defaultValue = "[]") tradeMarksRequest: String
     */
}