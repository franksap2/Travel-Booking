package com.franksap2.feature.calendar.components

import android.graphics.Paint
import android.icu.text.DateFormatSymbols
import android.text.TextPaint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.franksap2.feature.calendar.CalendarState
import com.franksap2.feature.calendar.rememberCalendar
import kotlin.math.ceil
import kotlin.math.floor
import java.util.Calendar as JavaCalendar

private const val RANGE_DURATION = 300
internal const val DAYS_IN_WEEK = 7

@Composable
internal fun CalendarItem(
    month: Int,
    calendarState: CalendarState
) {

    val rangePath = remember { Path() }
    val selectedDayAnimation = remember { Animatable(DateRange.EMPTY, DateRangeToVector, DateRange.VisibilityThreshold) }

    //TODO: Rework day range select animation, pending to add reverse animation and move logic to state
    LaunchedEffect(calendarState.fromDay, calendarState.toDay) {
        with(calendarState) {
            if (month == this.month) {
                selectedDayAnimation.snapTo(fromDay.toFloat() rangeTo fromDay.toFloat())
                if (fromDay != toDay && toDay != 0) {
                    val target =
                        if (toDay < fromDay) toDay.toFloat() rangeTo fromDay.toFloat() else fromDay.toFloat() rangeTo toDay.toFloat()
                    selectedDayAnimation.animateTo(target, tween(RANGE_DURATION))
                }
            }
        }
    }

    val weekDayTextPaintPaint = rememberTextPaint(textSize = 16.sp, color = MaterialTheme.colors.onBackground.copy(alpha = 0.3f))
    val textPaint = rememberTextPaint(textSize = 16.sp, color = MaterialTheme.colors.onBackground)
    val calendar = rememberCalendar(month)
    val startOffset = calendar.findDayOffset()
    val maxDaysOffset = startOffset + calendar.getActualMaximum(android.icu.util.Calendar.DAY_OF_MONTH)

    val dayLabels = rememberDayLabels(calendar)

    BoxWithConstraints {

        val density = LocalDensity.current
        val (contentHeight, cellSize) = remember(constraints.maxWidth) {
            val rows = ceil(maxDaysOffset / DAYS_IN_WEEK.toFloat()).toInt() + 1
            val cellSize = constraints.maxWidth / DAYS_IN_WEEK
            (rows * cellSize) / density.density to cellSize
        }

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(contentHeight.dp)
                .pointerInput(Unit) {
                    detectTapGestures {
                        processTouch(it, cellSize, startOffset, maxDaysOffset) {
                            calendarState.setDay(it, month)
                        }
                    }
                },
            onDraw = {

                val daysRange = selectedDayAnimation.value

                drawCalendar(maxDaysOffset, startOffset, textPaint, cellSize, daysRange, rangePath, calendarState)
                drawDays(dayLabels, cellSize, weekDayTextPaintPaint)
            }
        )
    }
}

private fun processTouch(
    offset: Offset,
    cellSize: Int,
    startOffset: Int,
    maxDaysOffset: Int,
    onDaySelected: (Int) -> Unit
) {


    val rowTouched = floor((offset.y - cellSize) / cellSize).toInt()
    val columnTouched = floor(offset.x / cellSize).toInt()

    val index = columnTouched + rowTouched * DAYS_IN_WEEK

    if (index in startOffset until maxDaysOffset) {
        val normalizedDay = index - startOffset + 1
        onDaySelected(normalizedDay)
    }
}

private fun DrawScope.drawDays(
    dayLabels: Array<String>,
    cellSize: Int,
    textPaint: TextPaint
) {
    val rowCenter = cellSize / 2
    var previousLeft = 0
    dayLabels.forEach { day ->
        drawIntoCanvas {
            it.nativeCanvas.drawText(
                day,
                previousLeft + rowCenter.toFloat(),
                rowCenter - (textPaint.descent() + textPaint.ascent()) / 2,
                textPaint
            )
        }
        previousLeft += cellSize
    }
}


private fun DrawScope.drawCalendar(
    maxDaysOffset: Int,
    startOffset: Int,
    textPaint: TextPaint,
    cellSize: Int,
    selectRange: DateRange,
    rangePath: Path,
    calendarState: CalendarState
) {
    val rowCenter = cellSize / 2
    var previousHeight = cellSize
    var previousLeft = startOffset * cellSize
    var column = startOffset

    for (i in startOffset until maxDaysOffset) {

        val day = i - startOffset + 1

        drawSelectedRange(
            selectRange,
            day,
            cellSize,
            previousHeight,
            previousLeft,
            column,
            rangePath,
            calendarState
        )

        drawDate(
            rowCenter,
            previousHeight,
            previousLeft,
            textPaint,
            day
        )

        if (column == DAYS_IN_WEEK - 1) {
            previousHeight += cellSize
            previousLeft = 0
            column = 0
        } else {
            previousLeft += cellSize
            column++
        }
    }
}

private fun DrawScope.drawDate(
    rowCenter: Int,
    previousHeight: Int,
    previousLeft: Int,
    textPaint: TextPaint,
    day: Int
) {

    drawIntoCanvas {
        it.nativeCanvas.drawText(
            day.toString(),
            previousLeft + rowCenter.toFloat(),
            previousHeight.toFloat() + rowCenter - (textPaint.descent() + textPaint.ascent()) / 2,
            textPaint
        )
    }
}

@Composable
private fun rememberTextPaint(textSize: TextUnit, color: Color): TextPaint {
    val textSizePx = with(LocalDensity.current) { textSize.toPx() }
    val context = LocalContext.current
    return remember {
        TextPaint().apply {
            this.textSize = textSizePx
            this.color = color.toArgb()
            isAntiAlias = true
            typeface = ResourcesCompat.getFont(context, com.franksap2.travelbooking.core.ui.R.font.signika_semibold)
            textAlign = Paint.Align.CENTER
        }
    }

}

private fun JavaCalendar.findDayOffset(): Int {
    set(JavaCalendar.DAY_OF_MONTH, 1)
    val firstDayOfMonth = get(JavaCalendar.DAY_OF_WEEK)

    val offset: Int = firstDayOfMonth - firstDayOfWeek
    return if (firstDayOfMonth < firstDayOfWeek) {
        offset + DAYS_IN_WEEK
    } else {
        offset
    }
}

@Composable
private fun rememberDayLabels(calendar: JavaCalendar) = remember {

    val weekDays = DateFormatSymbols.getInstance().getWeekdays(
        DateFormatSymbols.FORMAT,
        DateFormatSymbols.NARROW
    )
    val firstDayOfWeek = calendar.firstDayOfWeek
    Array(DAYS_IN_WEEK) {
        weekDays[(firstDayOfWeek + it - 1) % DAYS_IN_WEEK + 1]
    }

}