package com.franksap2.travelbooking.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.franksap2.feature.home.HomeScreen
import com.franksap2.travelbooking.destinations.Arguments
import com.franksap2.travelbooking.destinations.Destinations

@Composable
fun TravelBookingApp() {

    Scaffold(Modifier.fillMaxSize()) { innerPadding ->
        val navController = rememberNavController()

        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = navController,
            startDestination = Destinations.HOME
        ) {
            composable(Destinations.HOME) {
                HomeScreen(
                    onClickPlace = {
                        navController.navigate(route = "${Destinations.PLACE_DETAIL}/$it")
                    }
                )
            }

            composable(
                route = "${Destinations.PLACE_DETAIL}/{${Arguments.PLACE_ID}}",
                arguments = listOf(navArgument(Arguments.PLACE_ID) { type = NavType.StringType })
            ) { entry ->
                val arguments = requireNotNull(entry.arguments)
                val paceId = arguments.getString(Arguments.PLACE_ID)

            }
        }
    }

}