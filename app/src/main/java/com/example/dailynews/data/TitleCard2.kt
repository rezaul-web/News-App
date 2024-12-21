package com.example.dailynews.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.dailynews.model.Article

@Composable
fun TitleCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit,
    onLongClick: () -> Unit={}
) {
    Card(
        onClick = {
            onClick()
        }, modifier = Modifier.padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Row(modifier = modifier.fillMaxWidth()) {
                AsyncImage(
                    model = article.urlToImage,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .align(Alignment.CenterVertically),
                    contentScale = ContentScale.Crop
                )
                Column {
                    article.author?.let {
                        Text(
                            text = it,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                    Text(text = article.title, modifier = Modifier.padding(16.dp))
                }

            }
        }

    }

}