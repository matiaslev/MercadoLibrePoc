package com.matiaslev.mercadolibrepoc.data

import com.google.gson.annotations.SerializedName

data class ItemsResponse(
    @SerializedName("results") val results: List<Item>
)

data class Item(
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("price") val price: String,
    @SerializedName("permalink") val link: String
)
