package com.franksap2.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.franksap2.data.places.model.TopPlace

@Composable
fun TopPlaces(topPlaces: List<TopPlace>) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .height(82.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(topPlaces, key = { it.id }) { TopPlaceItem(it) }
    }
}

@Composable
private fun TopPlaceItem(topPlace: TopPlace) {

    Card(
        elevation = 4.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .size(82.dp),
                model = topPlace.img,
                contentDescription = topPlace.place,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    text = topPlace.place,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.subtitle1
                )

                Text(
                    text = topPlace.country,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }

    }

}