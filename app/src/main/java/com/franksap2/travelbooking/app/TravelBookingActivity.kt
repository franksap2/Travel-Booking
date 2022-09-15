package com.franksap2.travelbooking.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.franksap2.travelbooking.core.ui.theme.TravelBookingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TravelBookingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            TravelBookingTheme {
                TravelBookingApp()
            }
        }
    }
}