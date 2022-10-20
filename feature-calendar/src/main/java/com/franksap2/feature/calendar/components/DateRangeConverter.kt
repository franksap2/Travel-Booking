package com.franksap2.feature.calendar.components

import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.TwoWayConverter


val DateRangeToVector: TwoWayConverter<DateRange, AnimationVector2D> =
    TwoWayConverter(
        convertToVector = {
            AnimationVector2D(it.from, it.to)
        },
        convertFromVector = {
            DateRange(it.v1, it.v2)
        }
    )

class DateRange(val from: Float, val to: Float) : ClosedRange<Float> {
    override val endInclusive: Float get() = to
    override val start: Float get() = from

    companion object {
        val EMPTY: DateRange = DateRange(0f, 0f)
    }
}

infix fun Float.rangeTo(other: Float) = DateRange(this, other)

val DateRange.Companion.VisibilityThreshold: DateRange
    get() = DateRange(0.01f, 0.01f)