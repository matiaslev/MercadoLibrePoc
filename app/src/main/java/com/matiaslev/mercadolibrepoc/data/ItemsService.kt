package com.matiaslev.mercadolibrepoc.data

import retrofit2.Retrofit
import javax.inject.Inject

class ItemsService @Inject constructor(
    retrofit: Retrofit
) {

    private val itemsApi = retrofit.create(ItemsApi::class.java)

    suspend fun searchItems(query: String) = itemsApi.search(query)

}