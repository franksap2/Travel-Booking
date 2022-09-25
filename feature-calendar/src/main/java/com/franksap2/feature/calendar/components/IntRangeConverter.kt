package com.franksap2.feature.calendar.components

import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.TwoWayConverter

val IntRangeToVector: TwoWayConverter<IntRange, AnimationVector2D> =
    TwoWayConverter(
        convertToVector = {
            AnimationVector2D(it.first.toFloat(), it.last.toFloat())
        },
        convertFromVector = {
            IntRange(it.v1.toInt(), it.v2.toInt())
        }
    )

val IntRange.Companion.VisibilityThreshold: IntRange
    get() = IntRange(1, 1)