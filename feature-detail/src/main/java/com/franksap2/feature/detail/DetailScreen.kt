@file:OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)

package com.franksap2.feature.detail

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.franksap2.data.places.model.Place
import com.franksap2.feature.detail.components.Calendar
import com.franksap2.travelbooking.core.ui.components.LocationText
import com.franksap2.travelbooking.core.ui.components.TravelBookingButton
import com.franksap2.travelbooking.core.ui.extensions.formatCurrency
import com.franksap2.travelbooking.core.ui.extensions.overlay
import com.google.android.material.math.MathUtils

private val sheetContentVerticalPadding = 24.dp
private val sheetContentHorizontalPadding = 16.dp
private const val ALPHA_REVEAL_PERCENT = 0.3f
private const val OVERLAY_ALPHA = 0.25f

@Composable
fun DetailScreen(
    onBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val placeUiState by viewModel.uiState.collectAsState()

    if (placeUiState.place == null) return

    Box(modifier = Modifier.fillMaxSize()) {
        DetailScreenContent(placeUiState, viewModel::onBooking)

        IconButton(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .background(Color.Black.copy(0.7f), CircleShape),
            onClick = onBack
        ) {
            Icon(
                painter = painterResource(id = com.franksap2.travelbooking.core.ui.R.drawable.ic_back),
                contentDescription = null,
                tint = Color.White
            )
        }
    }

}

@Composable
private fun DetailScreenContent(uiState: DetailUiState, onBooking: () -> Unit) {

    var sheetPeekHeight by remember { mutableStateOf(0.dp) }

    val bookingBottomSheet = rememberTravelBookingBottomSheet()

    val progress by remember {
        derivedStateOf {
            val fraction = bookingBottomSheet.progress.fraction
            if (bookingBottomSheet.progress.to == TravelBookingBottomSheetValue.Expanded) fraction else 1 - fraction
        }
    }

    TravelBookingBottomSheet(
        state = bookingBottomSheet,
        sheetPeekHeight = sheetPeekHeight,

        sheetContent = {
            SheetContent(
                modifier = Modifier.fillMaxWidth(),
                uiState = uiState,
                sheetPeekHeight = { sheetPeekHeight = it },
                progressProvider = { progress },
                onBooking = onBooking
            )

        }, content = {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .overlay(alpha = OVERLAY_ALPHA * progress)
                    .graphicsLayer {
                        val scale = MathUtils.lerp(1f, 1.3f, progress)
                        scaleX = scale
                        scaleY = scale
                    },
                model = uiState.place?.img,
                contentDescription = uiState.place?.img,
                contentScale = ContentScale.Crop
            )
        }
    )
}

@Composable
private fun SheetContent(
    sheetPeekHeight: (Dp) -> Unit,
    modifier: Modifier = Modifier,
    progressProvider: () -> Float,
    onBooking: () -> Unit,
    uiState: DetailUiState
) {

    val navigationBarsPadding = WindowInsets.navigationBars.asPaddingValues()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(navigationBarsPadding)
            .padding(horizontal = sheetContentHorizontalPadding, vertical = sheetContentVerticalPadding)
    ) {
        Header(place = uiState.place, navigationBarsPadding = navigationBarsPadding, sheetPeekHeight = sheetPeekHeight)
        Content(uiState = uiState, progressProvider = progressProvider, onBooking = onBooking)
    }

}

@Composable
private fun Header(
    place: Place?,
    navigationBarsPadding: PaddingValues,
    sheetPeekHeight: (Dp) -> Unit,
) {


    val density = LocalDensity.current.density

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged {
                val targetPeekHeight = (it.height / density).dp + sheetContentVerticalPadding + 20.dp
                sheetPeekHeight(targetPeekHeight + navigationBarsPadding.calculateBottomPadding())
            }
    ) {

        Divider(
            modifier = Modifier
                .width(50.dp)
                .align(Alignment.CenterHorizontally)
                .clip(MaterialTheme.shapes.small),
            thickness = 6.dp
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = place?.place.orEmpty(),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground,
        )
        Text(
            modifier = Modifier,
            text = place?.price?.formatCurrency().orEmpty(),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
private fun Content(
    progressProvider: () -> Float,
    onBooking: () -> Unit,
    uiState: DetailUiState
) {

    AnimatedContent(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha = (progressProvider() / ALPHA_REVEAL_PERCENT).coerceIn(0f, 1f)),
        targetState = uiState.detailType,
    ) { targetState ->
        when (targetState) {
            DetailType.Info -> PlaceInfo(place = uiState.place)
            DetailType.Booking -> Calendar()
        }
    }

    TravelBookingButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp),
        text = stringResource(id = R.string.book_now),
        onClick = onBooking,
        style = MaterialTheme.typography.h6
    )
}

@Composable
private fun PlaceInfo(place: Place?) {
    Column {
        LocationText(
            modifier = Modifier.padding(top = 12.dp),
            text = place?.place.orEmpty(),
            secondText = place?.country.orEmpty(),
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            modifier = Modifier.padding(top = 12.dp),
            text = place?.description.orEmpty(),
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
        )
    }
}