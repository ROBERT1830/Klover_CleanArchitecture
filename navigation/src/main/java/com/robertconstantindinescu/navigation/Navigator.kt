package com.robertconstantindinescu.navigation

import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController
    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        NavigationFlow.CancerRecordsFlow -> navController.navigate(MainNavGraphDirections.actionGlobalCancerRecordsFlow())

    }
}