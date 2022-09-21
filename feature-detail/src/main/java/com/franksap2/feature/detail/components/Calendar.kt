package com.franksap2.feature.detail.components

import android.graphics.Paint
import android.text.TextPaint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import kotlin.math.ceil
import java.util.Calendar as JavaCalendar


private const val DAYS_IN_WEEK = 7

@Composable
fun Calendar() {

    val textPaint = rememberTextPaint(textSize = 16.sp, color = MaterialTheme.colors.onBackground)
    val calendar = rememberCalendar()
    val startOffset = calendar.findDayOffset()
    val maxDaysOffset = startOffset + calendar.getActualMaximum(android.icu.util.Calendar.DAY_OF_MONTH)

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
                .height(contentHeight.dp),
            onDraw = {
                var previousHeight = cellSize
                var previousLeft = startOffset * cellSize
                var column = startOffset

                val rowCenter = cellSize / 2

                for (i in startOffset until maxDaysOffset) {
                    drawCalendar(
                        i,
                        rowCenter,
                        previousHeight,
                        previousLeft,
                        startOffset,
                        textPaint
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
        )
    }
}



private fun DrawScope.drawCalendar(
    index: Int,
    rowCenter: Int,
    previousHeight: Int,
    previousLeft: Int,
    startOffset: Int,
    textPaint: TextPaint
) {

    val day = index - startOffset + 1

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

@Composable
private fun rememberCalendar() = remember { JavaCalendar.getInstance() }

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