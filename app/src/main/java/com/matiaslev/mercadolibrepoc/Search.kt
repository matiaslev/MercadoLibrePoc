package com.matiaslev.mercadolibrepoc

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.matiaslev.mercadolibrepoc.ui.theme.MercadoLibrePocTheme

@Composable
fun Search(navController: NavController) {
    Scaffold(
        topBar = { SearchTopBar { navController.popBackStack() } },
        content = { SearchContent { navController.popBackStack() } }
    )
}

@Composable
fun SearchTopBar(action: () -> Unit) {
    var input by mutableStateOf("")

    TopAppBar(backgroundColor = Color.White) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .clickable { action() },
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "back navigation button"
            )

            TextField(
                modifier = Modifier,
                value = input,
                label = { Text(text = "Buscar en Mercado Libre") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedLabelColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        action()
                    }
                ),
                onValueChange = { input = it }
            )
        }
    }
}

@Composable
fun SearchContent(action: () -> Unit) {
    val randomList = listOf("Heladeras", "Bicicletas", "Microondas", "Macbook Pro")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(randomList) {
            Row(modifier = Modifier
                .clickable { action() }) {
                Icon(
                    modifier = Modifier
                        .padding(end = 10.dp),
                    painter = painterResource(id = R.drawable.ic_searched),
                    contentDescription = "back navigation button"
                )

                Text(text = it)
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun SearchLightPreview() {
    val navController = rememberNavController()
    MercadoLibrePocTheme {
        Search(navController)
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun SearchDarkPreview() {
    val navController = rememberNavController()

    MercadoLibrePocTheme(darkTheme = true) {
        Search(navController)
    }
}