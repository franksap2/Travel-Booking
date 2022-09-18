package com.franksap2.travelbooking.core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)

val Shapes.extraLarge: RoundedCornerShape
    get() = RoundedCornerShape(28.dp)


val Shapes.topLarge: RoundedCornerShape
    get() = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)

val Shapes.extraExtraLarge: RoundedCornerShape
    get() = RoundedCornerShape(40.dp)