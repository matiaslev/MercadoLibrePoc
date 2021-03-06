package com.matiaslev.mercadolibrepoc

import androidx.lifecycle.ViewModel
import com.matiaslev.mercadolibrepoc.data.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    fun searchItems(query: String) = itemsRepository.searchItems()

}