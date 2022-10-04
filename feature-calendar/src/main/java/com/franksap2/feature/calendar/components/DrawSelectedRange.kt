package com.franksap2.feature.calendar.components

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import com.franksap2.feature.calendar.CalendarState
import com.franksap2.travelbooking.core.ui.theme.Orange700


internal fun DrawScope.drawSelectedRange(
    selectRange: DateRange,
    day: Int,
    cellSize: Int,
    previousHeight: Int,
    previousLeft: Int,
    column: Int,
    rangePath: Path,
    calendarState: CalendarState
) {


    val isFirstDaySelected = day == calendarState.fromDay
    val isLastDaySelected = day == calendarState.toDay
    val isDaysSelected = isFirstDaySelected || isLastDaySelected

    if (isDaysSelected || day in calendarState.fromDay..calendarState.toDay) {

        val animProgress =
            ((selectRange.to - calendarState.fromDay) / (calendarState.toDay - calendarState.fromDay)).coerceIn(0f, 1f)

        val dayOffset =
            ((day - calendarState.fromDay) / ((calendarState.toDay + 1) - calendarState.fromDay).toFloat()).coerceIn(0f, 1f)

        val percentPerSlot = 1 / (calendarState.toDay + 1 - calendarState.fromDay.toFloat())


        val adjustedProgress = ((animProgress - dayOffset) / (percentPerSlot)).coerceIn(0f, 1f)


        val rowCenter = cellSize / 2f
        val rectHeight = cellSize * 0.5f

        if (isDaysSelected) {
            val direction = if (isFirstDaySelected) rowCenter else 0f

            if (selectRange.to != selectRange.from && calendarState.toDay != 0) {
                drawRect(
                    Orange700.copy(alpha = 0.7f),
                    topLeft = Offset(previousLeft.toFloat() + direction, previousHeight.toFloat() + rectHeight / 2),
                    size = Size(rowCenter * adjustedProgress, rectHeight)
                )
            }

            drawCircle(
                color = Orange700,
                radius = rowCenter,
                center = Offset(previousLeft.toFloat() + rowCenter, previousHeight.toFloat() + rowCenter),
            )
        } else {

            val isCornerLeft = column == 0
            val isCornerRight = column == DAYS_IN_WEEK - 1


            rangePath.apply {
                reset()
                addRoundRect(
                    getRoundRect(
                        offset = Offset(previousLeft.toFloat(), previousHeight.toFloat() + rectHeight / 2),
                        size = Size(cellSize.toFloat() * adjustedProgress, rectHeight),
                        isCornerRight = isCornerRight,
                        isCornerLeft = isCornerLeft
                    )
                )
                close()
            }

            drawPath(rangePath, color = Orange700.copy(alpha = 0.7f))
        }
    }
}

private fun DrawScope.getRoundRect(
    offset: Offset,
    size: Size,
    isCornerRight: Boolean,
    isCornerLeft: Boolean
): RoundRect {
    val cornerRadius = CornerRadius(20.dp.toPx())

    return RoundRect(
        rect = Rect(offset, size),
        topLeft = if (isCornerLeft) cornerRadius else CornerRadius.Zero,
        bottomLeft = if (isCornerLeft) cornerRadius else CornerRadius.Zero,
        topRight = if (isCornerRight) cornerRadius else CornerRadius.Zero,
        bottomRight = if (isCornerRight) cornerRadius else CornerRadius.Zero,
    )
}