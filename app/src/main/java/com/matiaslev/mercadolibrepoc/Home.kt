package com.matiaslev.mercadolibrepoc

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.matiaslev.mercadolibrepoc.data.ItemsRepository
import com.matiaslev.mercadolibrepoc.ui.theme.MercadoLibrePocTheme

@ExperimentalAnimationApi
@Composable
fun Home(navController: NavController, model: MainViewModel) {
    Scaffold(
        topBar = { HomeTopBar { navController.navigate("details") } },
        content = { HomeContent(model) }
    )
}

@Composable
fun HomeTopBar(action: () -> Unit) {
    TopAppBar(backgroundColor = MaterialTheme.colors.primary,) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "menu"
            )

            TextField(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape),
                value = "",
                label = { topBarTextFieldLabel() },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedLabelColor = Color.Gray
                ),
                onValueChange = { action() }
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_shopping_cart),
                contentDescription = "menu"
            )
        }
    }
}

@Composable
fun HomeContent(model: MainViewModel) {
    val data by model.itemsState

    when (data) {
        ItemsRepository.ApiResponse.Loading -> {

        }

        is ItemsRepository.ApiResponse.Success -> {
            LazyColumn {
                items((data as ItemsRepository.ApiResponse.Success).items) {
                    Text(text = it.title)
                }
            }
        }

        is ItemsRepository.ApiResponse.Error -> {

        }

        ItemsRepository.ApiResponse.NotInitialized -> {

        }
    }
}

@Composable
fun topBarTextFieldLabel() {
    Row {
        Image(
            modifier = Modifier.padding(end = 5.dp),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "search",
            colorFilter = ColorFilter.tint(Color.Gray)
        )
        Text(text = "Buscar en Mercado Libre")
    }
}

/*@ExperimentalAnimationApi
@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    val navController = rememberNavController()
    val model = MainViewModel(ItemsRepository())

    MercadoLibrePocTheme {
        Home(navController, model)
    }
}

@ExperimentalAnimationApi
@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    val navController = rememberNavController()
    val model = MainViewModel(ItemsRepository())

    MercadoLibrePocTheme(darkTheme = true) {
        Home(navController, model)
    }
}*/