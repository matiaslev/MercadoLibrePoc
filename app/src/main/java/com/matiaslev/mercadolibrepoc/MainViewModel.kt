package com.matiaslev.mercadolibrepoc

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matiaslev.mercadolibrepoc.data.ItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val itemsRepository: ItemsRepository
) : ViewModel() {

    /*private val _itemsLiveData = MutableLiveData<ItemsRepository.ApiResponse>()
    val itemsLiveData = MutableLiveData<ItemsRepository.ApiResponse>() */

    val itemsState = mutableStateOf<ItemsRepository.ApiResponse>(ItemsRepository.ApiResponse.NotInitialized)

    fun searchItems(query: String) = viewModelScope.launch {
        itemsRepository.searchItems(query).collect {
            itemsState.value = it
        }
    }

}