package com.matiaslev.mercadolibrepoc.data

import com.google.gson.annotations.SerializedName

data class ItemsResponse(
    @SerializedName("results") val results: List<Item>
)

data class Item(
    val title: String
)
