package com.franksap2.travelbooking.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.franksap2.travelbooking.Onboarding
import com.franksap2.travelbooking.destinations.Destinations

@Composable
fun TravelBookingApp() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destinations.ONBOARDING) {
        composable(Destinations.ONBOARDING) {
            Onboarding()
        }


    }

}