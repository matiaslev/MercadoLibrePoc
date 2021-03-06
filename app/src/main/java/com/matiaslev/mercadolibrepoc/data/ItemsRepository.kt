package com.matiaslev.mercadolibrepoc.data

import javax.inject.Inject

class ItemsRepository @Inject constructor() {

    fun searchItems() = listOf("Hola", "sarasa")

}