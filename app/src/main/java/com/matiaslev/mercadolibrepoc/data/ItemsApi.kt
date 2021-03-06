package com.matiaslev.mercadolibrepoc.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ItemsApi {

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
    ): ItemsResponse

}
