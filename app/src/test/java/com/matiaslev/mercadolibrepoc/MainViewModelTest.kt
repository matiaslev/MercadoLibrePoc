package com.matiaslev.mercadolibrepoc

import com.matiaslev.mercadolibrepoc.data.ItemsRepository
import com.matiaslev.mercadolibrepoc.domain.CardItem
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class MainViewModelTest : BaseTest() {

    private lateinit var mainViewModel: MainViewModel

    @MockK
    private lateinit var itemsRepository: ItemsRepository

    @Before
    fun init() {
        mainViewModel = MainViewModel(itemsRepository)
    }

    @Test
    fun `searchItems success`() = runBlockingTest{
        // arrange
        coEvery { itemsRepository.searchItems(query) } returns flow {

            emit(ItemsRepository.ApiResponse.Loading)
            emit(ItemsRepository.ApiResponse.Success(
                listOf(
                    CardItem(empty_string, empty_string, empty_string)
                )
            ))

        }

        // act
        mainViewModel.searchItems(query)

        // assert
        assert(mainViewModel.itemsState.value is ItemsRepository.ApiResponse.Success)
    }

    @Test
    fun `searchItems error`() = runBlockingTest {
        // arrange
        coEvery { itemsRepository.searchItems(query) } returns flow {

            emit(ItemsRepository.ApiResponse.Loading)
            emit(ItemsRepository.ApiResponse.Error(Exception(empty_string)))

        }

        // act
        mainViewModel.searchItems(query)

        // assert
        assert(mainViewModel.itemsState.value is ItemsRepository.ApiResponse.Error)
    }

    @Test
    fun `searchItems should add the query to lastSearchs`() = runBlockingTest {
        // act
        mainViewModel.searchItems(query)

        // assert
        assert(mainViewModel.getLastSearchs().contains(query))
    }

}