package com.franksap2.travelbooking

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun Onboarding() {

    AsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = "https://source.unsplash.com/2ueUnL4CkV8",
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

}