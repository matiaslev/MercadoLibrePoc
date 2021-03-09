package com.matiaslev.mercadolibrepoc.screens

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.matiaslev.mercadolibrepoc.MainViewModel
import com.matiaslev.mercadolibrepoc.R

@Composable
fun Search(navController: NavController, model: MainViewModel) {
    Scaffold(
        topBar = { SearchTopBar(model) { navController.popBackStack() } },
        content = { SearchContent(model) { navController.popBackStack() } }
    )
}

@Composable
fun SearchTopBar(model: MainViewModel, action: () -> Unit) {
    var input by mutableStateOf("")

    TopAppBar(backgroundColor = Color.White) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .weight(1f)
                    .clickable { action() },
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "back navigation button"
            )

            TextField(
                modifier = Modifier.weight(4f),
                value = input,
                label = { Text(text = "Buscar en Mercado Libre") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedLabelColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        model.searchItems(input)
                        action()
                    }
                ),
                onValueChange = { input = it }
            )
        }
    }
}

@Composable
fun SearchContent(model: MainViewModel, action: () -> Unit) {
    val randomList = remember { model.getLastSearchs() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(randomList) {
            Row(modifier = Modifier
                .padding(10.dp)
                .clickable {
                    model.searchItems(it)
                    action()
                }) {
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