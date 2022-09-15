package com.franksap2.travelbooking.core.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.franksap2.travelbooking.core.ui.R

private val signikaFamily = FontFamily(
    Font(R.font.signika_light, weight = FontWeight.Light),
    Font(R.font.signika_regular, weight = FontWeight.Normal),
    Font(R.font.signika_medium, weight = FontWeight.Medium),
    Font(R.font.signika_semibold, weight = FontWeight.SemiBold),
    Font(R.font.signika_bold, weight = FontWeight.SemiBold),
)

val Typography = Typography(defaultFontFamily = signikaFamily)