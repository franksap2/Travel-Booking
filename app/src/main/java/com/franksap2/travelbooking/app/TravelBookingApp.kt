package com.franksap2.travelbooking.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.franksap2.travelbooking.Onboarding
import com.franksap2.travelbooking.destinations.Destinations

@Composable
fun TravelBookingApp() {

    val navController = rememberNavController()

    var showOnboarding by rememberSaveable { mutableStateOf(true) }

    NavHost(navController = navController, startDestination = Destinations.ONBOARDING) {
        composable(Destinations.ONBOARDING) {
            Onboarding{

            }
        }
    }

}