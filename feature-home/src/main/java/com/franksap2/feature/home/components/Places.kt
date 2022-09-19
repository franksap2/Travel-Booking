package com.franksap2.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.franksap2.data.places.model.Place
import com.franksap2.travelbooking.core.ui.components.LocationText
import com.franksap2.travelbooking.core.ui.theme.extraExtraLarge
import com.franksap2.travelbooking.core.ui.theme.extraLarge


@Composable
fun Places(item: List<Place>, onClick: (String) -> Unit) {

    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .height(400.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        content = {
            items(item, key = { it.id }) {
                PlaceItem(item = it, onClick = onClick)
            }
        })
}

@Composable
private fun PlaceItem(item: Place, onClick: (String) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxHeight()
            .clickable { onClick(item.id) }
            .aspectRatio(0.62f),
        elevation = 4.dp,
        shape = MaterialTheme.shapes.extraExtraLarge
    ) {

        Box(modifier = Modifier.fillMaxSize()) {

            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = item.img,
                contentDescription = item.place,
                contentScale = ContentScale.Crop
            )


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .align(Alignment.BottomCenter),
                elevation = 8.dp,
                shape = MaterialTheme.shapes.extraLarge
            ) {

                LocationText(
                    modifier = Modifier.padding(12.dp),
                    text = item.place,
                    secondText = item.country,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }

    }
}
