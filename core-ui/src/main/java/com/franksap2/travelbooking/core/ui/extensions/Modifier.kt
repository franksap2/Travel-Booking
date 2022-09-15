package com.franksap2.travelbooking.core.ui.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color

fun Modifier.overlay(
    alpha: Float,
    color: Color = Color.Black
) = drawWithCache {
    onDrawWithContent {
        drawContent()
        drawRect(color = color, alpha = alpha)
    }
}