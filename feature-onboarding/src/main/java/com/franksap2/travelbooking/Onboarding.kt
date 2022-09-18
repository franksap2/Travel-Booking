package com.franksap2.travelbooking

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.franksap2.travelbooking.core.ui.components.TravelBookingButton
import com.franksap2.travelbooking.core.ui.extensions.overlay
import kotlinx.coroutines.launch


private const val REVEAL_TIME = 1_000
private const val REVEAL_DELAY = 350
private val titleRevealTranslation = 40.dp
private val buttonRevealTranslation = 20.dp
private const val OVERLAY_ALPHA = 0.2f

@Composable
fun Onboarding(onClickContinue: () -> Unit) {

    val revealAnimation = remember { Animatable(0f) }
    val buttonRevealAnimation = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        launch {
            buttonRevealAnimation.animateTo(1f, tween(REVEAL_TIME, delayMillis = REVEAL_DELAY))
        }
        launch { revealAnimation.animateTo(1f, tween(REVEAL_TIME)) }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .overlay(OVERLAY_ALPHA * revealAnimation.value),
            model = R.drawable.ic_onboarding,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {


            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .graphicsLayer {
                        alpha = revealAnimation.value
                        translationY = titleRevealTranslation.toPx() * (1 - revealAnimation.value)
                    },
                text = stringResource(id = R.string.onboarding_title),
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color.White
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .graphicsLayer {
                        alpha = revealAnimation.value
                        translationY = titleRevealTranslation.toPx() * (1 - revealAnimation.value)
                    },
                text = stringResource(id = R.string.onboarding_subtitle),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color.White
            )

            TravelBookingButton(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .graphicsLayer {
                        alpha = buttonRevealAnimation.value
                        translationY = buttonRevealTranslation.toPx() * (1 - buttonRevealAnimation.value)
                    },
                text = stringResource(id = R.string.onboarding_button),
                onClick = onClickContinue
            )


            Spacer(modifier = Modifier.fillMaxHeight(0.2f))

        }
    }
}