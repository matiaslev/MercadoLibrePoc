package com.matiaslev.mercadolibrepoc.screens

import android.content.res.Configuration
import android.widget.RatingBar
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
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
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
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
            Column {
                repeat(5) {
                    LoadingView()
                }
            }
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
    // Creates an `InfiniteTransition` that runs infinite child animation values.
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        // `infiniteRepeatable` repeats the specified duration-based `AnimationSpec` infinitely.
        animationSpec = infiniteRepeatable(
            // The `keyframes` animates the value by specifying multiple timestamps.
            animation = keyframes {
                // One iteration is 1000 milliseconds.
                durationMillis = 1000
                // 0.7f at the middle of an iteration.
                0.7f at 500
            },
            // When the value finishes animating from 0f to 1f, it repeats by reversing the
            // animation direction.
            repeatMode = RepeatMode.Reverse
        )
    )
    Row(
        modifier = Modifier
            .heightIn(min = 64.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RectangleShape)
                .background(Color.LightGray.copy(alpha = alpha))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(Color.LightGray.copy(alpha = alpha))
            )
            Spacer(modifier = Modifier.height(5.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(Color.LightGray.copy(alpha = alpha))
            )
            Spacer(modifier = Modifier.height(5.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(Color.LightGray.copy(alpha = alpha))
            )
        }
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
        modifier = Modifier
            .testTag("CardItem")
            .background(Color.White)
            .clickable { navController.navigate("details") }
    ) {

        val height = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
            100.dp
        } else {
            200.dp
        }

        BoxWithConstraints(modifier = Modifier
            .fillMaxSize()
            .weight(2f)
            .height(height)
            .padding(15.dp)
            .background(Color.White)
            .animateContentSize()
        ) {
            CoilImage(
                modifier = Modifier
                    .background(Color.White),
                data = item.thumbnail,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                error = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.LightGray)
                    ) {

                    }
                }
            )
        }

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
            text = "♡",
            color = Color.Blue
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
