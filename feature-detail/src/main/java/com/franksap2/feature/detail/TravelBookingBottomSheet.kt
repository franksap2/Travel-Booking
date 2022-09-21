package com.franksap2.feature.detail

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.Velocity
import com.franksap2.travelbooking.core.ui.theme.topLarge
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TravelBookingBottomSheet(
    sheetPeekHeight: Dp,
    state: TravelBookingBottomSheet = rememberTravelBookingBottomSheet(),
    sheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {


    Box(contentAlignment = Alignment.BottomCenter) {

        var contentSize by remember { mutableStateOf(0f) }
        val peekHeightPx = with(LocalDensity.current) { sheetPeekHeight.toPx().takeIf { it > 0 } ?: -1 }

        content()

        Surface(
            modifier = Modifier
                .nestedScroll(state.connection)
                .swipeable(
                    state = state,
                    anchors = mapOf(
                        0f to TravelBookingBottomSheetValue.Expanded,
                        contentSize - peekHeightPx.toFloat() to TravelBookingBottomSheetValue.Collapsed
                    ),
                    orientation = Orientation.Vertical,
                    enabled = true,
                    resistance = null
                )
                .offset { IntOffset(0, state.offset.value.roundToInt()) }
                .onSizeChanged {
                    contentSize = it.height.toFloat()
                },
            color = MaterialTheme.colors.background,
            shape = MaterialTheme.shapes.topLarge
        ) {
            sheetContent()
        }


    }
}

@Composable
fun rememberTravelBookingBottomSheet() = remember { TravelBookingBottomSheet() }

@OptIn(ExperimentalMaterialApi::class)
class TravelBookingBottomSheet : SwipeableState<TravelBookingBottomSheetValue>(
    TravelBookingBottomSheetValue.Expanded
) {

    internal val connection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.toFloat()
            return if (delta < 0 && source == NestedScrollSource.Drag) {
                performDrag(delta).toOffset()
            } else {
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            return if (source == NestedScrollSource.Drag) {
                performDrag(available.toFloat()).toOffset()
            } else {
                Offset.Zero
            }
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            val toFling = Offset(available.x, available.y).toFloat()
            return if (toFling < 0 && offset.value > 0) {
                performFling(velocity = toFling)
                available
            } else {
                Velocity.Zero
            }
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            performFling(velocity = Offset(available.x, available.y).toFloat())
            return available
        }

        private fun Float.toOffset(): Offset = Offset(0f, this)

        private fun Offset.toFloat(): Float = this.y
    }

}

enum class TravelBookingBottomSheetValue {
    Collapsed,
    Expanded
}
