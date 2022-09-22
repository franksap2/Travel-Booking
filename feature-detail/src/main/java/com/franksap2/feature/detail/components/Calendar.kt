@file:OptIn(ExperimentalPagerApi::class)

package com.franksap2.feature.detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale
import java.util.Calendar as JavaCalendar

private const val MAX_MONTHS = 12
private const val FIVE_YEARS = MAX_MONTHS * 5


@Composable
fun Calendar() {

    val state = rememberPagerState()

    Column(modifier = Modifier.padding(top = 12.dp)) {

        MonthText(state = state)

        HorizontalPager(
            state = state,
            count = FIVE_YEARS,
            modifier = Modifier.animateContentSize(),
            verticalAlignment = Alignment.Top
        ) { month ->
            CalendarItem(month)
        }

    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MonthText(state: PagerState) {
    val months = rememberMonths()
    val calendar = rememberCalendar(month = state.currentPage)
    val year = calendar.get(JavaCalendar.YEAR)

    val currentMonth = remember { JavaCalendar.getInstance().get(JavaCalendar.MONTH) }

    AnimatedContent(
        targetState = state.currentPage,
        transitionSpec = {
            if (initialState> targetState) {
                slideInVertically { -it } + fadeIn() with slideOutVertically { it } + fadeOut()
            } else {
                slideInVertically { it } + fadeIn() with slideOutVertically { -it } + fadeOut()
            }.using(SizeTransform(clip = false))
        }
    ) { targetState ->
        val title = "${months[(currentMonth + targetState) % MAX_MONTHS]} $year"
        Text(text = title, color = MaterialTheme.colors.onBackground)
    }
}

@Composable
internal fun rememberCalendar(month: Int) = remember(month) {

    val instance = JavaCalendar.getInstance()

    val currentMonth = instance.get(JavaCalendar.MONTH) + month

    val year = instance.get(JavaCalendar.YEAR) + currentMonth / MAX_MONTHS.toFloat()

    instance.apply {
        set(JavaCalendar.YEAR, year.toInt())
        set(JavaCalendar.MONTH, currentMonth % MAX_MONTHS)
    }
}

@Composable
private fun rememberMonths() = remember {
    Array(MAX_MONTHS) {
        Month.of(it + 1).getDisplayName(TextStyle.FULL, Locale.getDefault())
    }
}