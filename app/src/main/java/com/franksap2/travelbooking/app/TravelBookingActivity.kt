@file:OptIn(ExperimentalAnimationApi::class)

package com.franksap2.travelbooking.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.franksap2.travelbooking.Onboarding
import com.franksap2.travelbooking.core.ui.theme.TravelBookingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TravelBookingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TravelBookingTheme {

                var showOnboarding by rememberSaveable { mutableStateOf(true) }

                AnimatedContent(
                    modifier = Modifier.fillMaxSize(),
                    targetState = showOnboarding,
                    transitionSpec = { travelBookingInitialTransition() }
                ) { targetState -> if (targetState) Onboarding { showOnboarding = false } else TravelBookingApp() }
            }
        }
    }
}


private fun travelBookingInitialTransition(): ContentTransform {
    val enterTransition = fadeIn(tween(1000)) +
            scaleIn(initialScale = 0.8f, animationSpec = spring()) +
            slideInVertically(tween(800)) { (it * 0.2f).toInt() }

    val exitTransition = slideOutVertically(tween(500)) { it }

    return enterTransition with exitTransition
}