package com.matiaslev.mercadolibrepoc

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.matiaslev.mercadolibrepoc.data.ItemsRepository
import com.matiaslev.mercadolibrepoc.domain.CardItem
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
                label = { TopBarTextFieldLabel() },
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
fun TopBarTextFieldLabel() {
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

@Composable
fun HomeContent(model: MainViewModel) {
    val data by model.itemsState

    when (data) {
        ItemsRepository.ApiResponse.Loading -> {
            LoadingView()
        }

        is ItemsRepository.ApiResponse.Success -> {
            LazyColumn {
                items((data as ItemsRepository.ApiResponse.Success).items) {
                    CardItem(it)
                }
            }
        }

        is ItemsRepository.ApiResponse.Error -> {
            ErrorView()
        }

        ItemsRepository.ApiResponse.NotInitialized -> {

        }
    }
}

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            backgroundColor = Color.LightGray
        )
    }
}

@Composable
fun ErrorView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .size(80.dp),
            painter = painterResource(id = R.drawable.ic_wifi_off),
            contentDescription = "Error"
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = "¡Parece que no hay internet!",
            color = Color.DarkGray,
            textAlign = TextAlign.Center,
            style = typography.h6.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Revisa tu conexión para seguir\nnavegando.",
            color = Color.LightGray,
            textAlign = TextAlign.Center,
            style = typography.h6
        )
    }
}

@Composable
fun CardItem(item: CardItem) {
    Text(text = item.title)
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