package com.franksap2.feature.calendar.components

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.franksap2.travelbooking.core.ui.theme.Orange700


internal fun DrawScope.drawSelectedRange(
    selectRange: IntRange,
    day: Int,
    cellSize: Int,
    previousHeight: Int,
    previousLeft: Int,
    column: Int,
) {

    val isFirstDaySelected = day == selectRange.first
    val isLastDaySelected = day == selectRange.last
    val isDaysSelected = isFirstDaySelected || isLastDaySelected
    val onlyOneDaySelected = selectRange.last == 0 && selectRange.first == day


    if (onlyOneDaySelected || day in selectRange) {

        val rowCenter = cellSize / 2f
        val rectHeight = cellSize * 0.5f

        if (isDaysSelected) {
            val direction = if (isFirstDaySelected) rowCenter else 0f

            if (!onlyOneDaySelected && selectRange.last != selectRange.first) {
                drawRect(
                    Orange700,
                    topLeft = Offset(previousLeft.toFloat() + direction, previousHeight.toFloat() + rectHeight / 2),
                    size = Size(rowCenter, rectHeight)
                )
            }


            drawCircle(
                color = Orange700, radius = rowCenter,
                center = Offset(previousLeft.toFloat() + rowCenter, previousHeight.toFloat() + rowCenter),
            )
        } else {

            val isCornerLeft = column == 0
            val isCornerRight = column == DAYS_IN_WEEK - 1

            val radius = if (isCornerRight || isCornerLeft) CornerRadius(25f, 25f) else CornerRadius.Zero

            val offsetX = when {
                isCornerLeft -> 25f
                isCornerRight -> -25f
                else -> 0f
            }

            drawRoundRect(
                color = Orange700,
                topLeft = Offset(previousLeft.toFloat() + offsetX, previousHeight.toFloat() + rectHeight / 2),
                size = Size(cellSize.toFloat(), rectHeight),
                cornerRadius = radius
            )

        }
    }
}