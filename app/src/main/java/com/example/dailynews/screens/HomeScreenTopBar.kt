package com.example.dailynews.screens

import androidx.compose.material3.ExperimentalMaterial3Api
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
    currentSortMethod: String
) {
    TopAppBar(
        title = { Text("Daily News") },
        actions = {
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