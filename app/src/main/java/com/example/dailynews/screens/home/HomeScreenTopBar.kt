package com.example.dailynews.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.dailynews.viewmodels.HomeViewmodel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreenTopBar(
    viewModel: HomeViewmodel,
    currentSortMethod: String,
    onMenuClick: () -> Unit
) {
    TopAppBar(
        title = { Text("Daily News") },
        navigationIcon = { // Align the menu button to the start
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }, actions = {

            // Popularity Button
            TextButton(onClick = { viewModel.updateSortedBy("popularity") }) {
                Text(
                    text = if (currentSortMethod == "popularity") "Popularity ✓" else "Popularity",
                )
            }

            // PublishedAt Button
            TextButton(onClick = { viewModel.updateSortedBy("publishedAt") }) {
                Text(
                    text = if (currentSortMethod == "publishedAt") "PublishedAt ✓" else "PublishedAt",
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Cyan,
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Black,

            )
    )
}