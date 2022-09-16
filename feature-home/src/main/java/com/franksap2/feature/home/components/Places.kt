package com.franksap2.feature.home.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.franksap2.data.places.model.Place
import com.franksap2.feature.home.R
import com.franksap2.travelbooking.core.ui.theme.extraExtraLarge
import com.franksap2.travelbooking.core.ui.theme.extraLarge


@Composable
fun Places(item: List<Place>) {

    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .height(500.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        content = {
            items(item) {
                PlaceItem(item = it)
            }
        })

}

@Composable
private fun PlaceItem(item: Place) {

    Card(
        modifier = Modifier
            .fillMaxHeight()
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

                Text(
                    modifier = Modifier.padding(12.dp),
                    text = buildPlaceLocationText(item = item),
                    style = MaterialTheme.typography.h6,
                    inlineContent = buildInlineContent(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }

    }
}

@Composable
private fun buildPlaceLocationText(item: Place) = buildAnnotatedString {
    appendInlineContent("icon")
    append(item.place)
    append(", ")
    withStyle(
        style = SpanStyle(
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
            fontWeight = FontWeight.Light
        )
    ) {
        append(item.country)
    }
}

private fun buildInlineContent() = mapOf(
    "icon" to InlineTextContent(
        placeholder = Placeholder(30.sp, 30.sp, PlaceholderVerticalAlign.TextCenter),
        children = {
            Image(
                painter = painterResource(id = R.drawable.ic_location),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary)
            )
        }
    )
)
