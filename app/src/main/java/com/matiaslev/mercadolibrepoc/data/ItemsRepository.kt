package com.matiaslev.mercadolibrepoc.data

import android.util.Log
import com.matiaslev.mercadolibrepoc.utils.Printer
import com.matiaslev.mercadolibrepoc.domain.CardItem
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItemsRepository @Inject constructor(
    private val itemsService: ItemsService
) {

    suspend fun searchItems(query: String) = flow {

        // Log.d(Printer.TAG,"Loading")
        emit(ApiResponse.Loading)

        // Log.d(Printer.TAG,"Fetching Items")
        val items = itemsService.searchItems(query).results.map {
            CardItem(
                title = it.title,
                thumbnail = it.thumbnail.replace("http://", "https://"),
                price = it.price
            )
        }

        // Log.d(Printer.TAG,"Fetching Items Success")
        emit(ApiResponse.Success(items))

    }.catch { exception ->
        // Log.e(Printer.TAG,"Fetching Items Error", exception)
        emit(ApiResponse.Error(exception))
    }

    sealed class ApiResponse {
        object NotInitialized : ApiResponse()
        object Loading : ApiResponse()
        data class Success(val items: List<CardItem>) : ApiResponse()
        data class Error(val exception: Throwable) : ApiResponse()
    }
}