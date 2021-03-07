package com.matiaslev.mercadolibrepoc

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HomeTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun home_topbar() {
        composeTestRule.onNodeWithContentDescription("menu").assertExists()
        composeTestRule.onNodeWithContentDescription("shop").assertExists()
    }

    @Test
    fun home_default_offers() {
        var condition = false
        GlobalScope.launch {
            delay(10000)
            condition = true
        }

        composeTestRule.waitUntil(15000) {
            condition
        }

        composeTestRule.onNodeWithContentDescription("card image").assertExists()
    }
}