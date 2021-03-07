package com.matiaslev.mercadolibrepoc

import app.cash.turbine.test
import com.matiaslev.mercadolibrepoc.data.Item
import com.matiaslev.mercadolibrepoc.data.ItemsRepository
import com.matiaslev.mercadolibrepoc.data.ItemsResponse
import com.matiaslev.mercadolibrepoc.data.ItemsService
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import kotlin.time.ExperimentalTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalTime
class ItemsRepositoryTest : BaseTest() {

    private lateinit var itemsRepository: ItemsRepository

    @MockK
    private lateinit var itemsService: ItemsService

    @Before
    fun init() {
        itemsRepository = ItemsRepository(itemsService)
    }

    @Test
    fun `searchItems Success`() = runBlockingTest {
        // arrange
        coEvery { itemsService.searchItems(query) } returns ItemsResponse(
            listOf(
                Item(empty_string, empty_string, empty_string)
            )
        )

        // act
        val flow = itemsRepository.searchItems(query)

        // assert
        flow.test {
            assertEquals(ItemsRepository.ApiResponse.Loading, expectItem())
            assert(expectItem() is ItemsRepository.ApiResponse.Success)
            expectComplete()
        }
    }

    @Test
    fun `searchItems Error`() = runBlockingTest {
        // arrange
        coEvery { itemsService.searchItems(query) } throws Exception(empty_string)

        // act
        val flow = itemsRepository.searchItems(query)

        // assert
        flow.test {
            assertEquals(ItemsRepository.ApiResponse.Loading, expectItem())
            assert(expectItem() is ItemsRepository.ApiResponse.Error)
            expectComplete()
        }
    }

}