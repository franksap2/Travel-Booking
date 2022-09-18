@file:OptIn(ExperimentalMaterialApi::class)

package com.franksap2.feature.detail

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.franksap2.data.places.model.Place
import com.franksap2.travelbooking.core.ui.components.LocationText
import com.franksap2.travelbooking.core.ui.extensions.formatCurrency
import com.franksap2.travelbooking.core.ui.theme.topLarge
import com.google.android.material.math.MathUtils

private val sheetContentVerticalPadding = 24.dp
private val sheetContentHorizontalPadding = 16.dp
private const val ALPHA_REVEAL_PERCENT = 0.3f

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel()
) {
    val placeState by viewModel.placeState.collectAsState()
    placeState?.let { DetailScreenContent(it) }
}

@Composable
private fun DetailScreenContent(place: Place) {

    var sheetPeekHeight by remember { mutableStateOf(0.dp) }

    val state = rememberBottomSheetScaffoldState()

    val progress by remember {
        derivedStateOf {
            val fraction = state.bottomSheetState.progress.fraction
            val alpha = if (state.bottomSheetState.progress.to == BottomSheetValue.Expanded) fraction else 1 - fraction
            (alpha / ALPHA_REVEAL_PERCENT).coerceIn(0f, 1f)
        }
    }

    LaunchedEffect(key1 = Unit) {
        state.bottomSheetState.animateTo(
            BottomSheetValue.Expanded,
            tween(400)
        )
    }

    BottomSheetScaffold(
        scaffoldState = state,
        sheetPeekHeight = sheetPeekHeight,
        sheetShape = MaterialTheme.shapes.topLarge,

        sheetContent = {

            SheetContent(
                modifier = Modifier.fillMaxWidth(),
                place = place,
                sheetPeekHeight = { sheetPeekHeight = it },
                progressProvider = { progress }
            )

        }, content = {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        val scale = MathUtils.lerp(1f,1.3f,progress)
                        scaleX = scale
                        scaleY = scale
                    },
                model = place.img,
                contentDescription = place.place,
                contentScale = ContentScale.Crop
            )
        }
    )
}

@Composable
private fun SheetContent(
    place: Place,
    sheetPeekHeight: (Dp) -> Unit,
    modifier: Modifier = Modifier,
    progressProvider: () -> Float
) {

    val navigationBarsPadding = WindowInsets.navigationBars.asPaddingValues()


    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(navigationBarsPadding)
            .padding(horizontal = sheetContentHorizontalPadding, vertical = sheetContentVerticalPadding)
    ) {

        Box(modifier = Modifier.fillMaxSize(0.5f))

        Column {

            Header(place = place, navigationBarsPadding = navigationBarsPadding, sheetPeekHeight = sheetPeekHeight)

            LocationText(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .alpha(alpha = progressProvider()),
                text = place.place,
                secondText = place.country,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }


    }
}

@Composable
private fun Header(
    place: Place,
    navigationBarsPadding: PaddingValues,
    sheetPeekHeight: (Dp) -> Unit,
) {


    val density = LocalDensity.current.density

    Column(
        modifier = Modifier.onSizeChanged {
            val targetPeekHeight = (it.height / density).dp + sheetContentVerticalPadding + 20.dp
            sheetPeekHeight(targetPeekHeight + navigationBarsPadding.calculateBottomPadding())
        },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Divider(
            modifier = Modifier
                .width(50.dp)
                .align(Alignment.CenterHorizontally)
                .clip(MaterialTheme.shapes.small),
            thickness = 6.dp
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(1f),
                text = place.place,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onBackground,

                )
            Text(
                modifier = Modifier,
                text = place.price.formatCurrency(),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.secondary,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable
private fun buildLocationText(text: String) = buildAnnotatedString {
    appendInlineContent("icon")
    append(text)
}