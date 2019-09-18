package ru.lingstra.avitocopy.system.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.lingstra.avitocopy.domain.hand_shakes.SimpleServerAnswer

interface NetworkApi {

    @GET("friends.get")
    fun getFriendsForUser(
        @Query("user_id") userId: Int,
        @Query("access_token") token: String,
        @Query("count") count: Int = 15000,
        @Query("v") version: String = "5.8"
    ): Single<SimpleServerAnswer>
}