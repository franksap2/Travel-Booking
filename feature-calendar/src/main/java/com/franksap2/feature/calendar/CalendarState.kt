package com.franksap2.feature.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun rememberCalendarState() = rememberSaveable(saver = CalendarState.Saver) { CalendarState() }

@Stable
class CalendarState(
    fromDay: Int = 0,
    toDay: Int = 0,
    month: Int = 0
) {

    companion object {
        val Saver: Saver<CalendarState, *> = listSaver(
            save = { listOf(it.fromDay, it.toDay, it.month) },
            restore = {
                CalendarState(fromDay = it[0], toDay = it[1], month = it[2])
            }
        )
    }

    var fromDay by mutableStateOf(fromDay)
        private set

    var toDay by mutableStateOf(toDay)
        private set

    var month by mutableStateOf(month)
        private set

    fun setDay(day: Int, month: Int) {
        this.month = month

        when {
            fromDay == 0 || toDay != 0 -> {
                fromDay = day
                toDay = 0
            }
            toDay == 0 -> toDay = day
        }
    }
}