package com.matiaslev.mercadolibrepoc.screens

import android.widget.RatingBar
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.matiaslev.mercadolibrepoc.MainViewModel
import com.matiaslev.mercadolibrepoc.R
import com.matiaslev.mercadolibrepoc.data.ItemsRepository
import com.matiaslev.mercadolibrepoc.domain.CardItem
import dev.chrisbanes.accompanist.coil.CoilImage

@ExperimentalAnimationApi
@Composable
fun Home(navController: NavController, model: MainViewModel) {
    Scaffold(
        topBar = { HomeTopBar { navController.navigate("search") } },
        content = { HomeContent(navController, model) }
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

            TopBarTextField(action)

            Icon(
                painter = painterResource(id = R.drawable.ic_shopping_cart),
                contentDescription = "shop"
            )
        }
    }
}

@Composable
fun TopBarTextField(action: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.White)
            .fillMaxWidth(0.9f)
            .padding(5.dp)
            .clickable { action() }
    ) {
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
fun HomeContent(navController: NavController, model: MainViewModel) {
    val data by model.itemsState

    when (data) {
        ItemsRepository.ApiResponse.Loading -> {
            LoadingView()
        }

        is ItemsRepository.ApiResponse.Success -> {
            LazyColumn {
                items((data as ItemsRepository.ApiResponse.Success).items) {
                    CardItem(navController, it)
                }
            }
        }

        is ItemsRepository.ApiResponse.Error -> {
            ErrorView()
        }

        ItemsRepository.ApiResponse.NotInitialized -> {
            model.searchItems("ofertas")
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
fun CardItem(navController: NavController, item: CardItem) {
    Row(
        modifier = Modifier.clickable { navController.navigate("details") }
    ) {
        CoilImage(
            modifier = Modifier
                .weight(2f),
            data = item.thumbnail,
            contentDescription = item.title,
            loading = {
                Box(Modifier.matchParentSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            },
            error = {
                Icon(
                    modifier = Modifier
                        .size(80.dp),
                    painter = painterResource(id = R.drawable.ic_wifi_off),
                    contentDescription = "Error"
                )
            }
        )

        Column(
            modifier = Modifier
                .weight(3f)
                .padding(10.dp)
        ) {
            Text(
                text = item.title,
                fontFamily = FontFamily.SansSerif
            )

            Text(
                modifier = Modifier
                    .padding(top = 5.dp),
                text = "\$ ${item.price}",
                style = typography.h6.copy(fontWeight = FontWeight.Bold),
                fontFamily = FontFamily.SansSerif
            )

            ComposableRatingBar()
        }

        Text(
            modifier = Modifier
                .weight(0.6f)
                .padding(10.dp)
                .size(80.dp),
            text = "♡"
        )
    }
}

@Composable
fun ComposableRatingBar() {
    AndroidView(
        modifier = Modifier,
        factory = { context ->
            RatingBar(context).apply {
                numStars = 5
                rating = 3.5f
                isEnabled = false
            }
        }
    )
}
