package com.robertconstantindinescu.core_ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.robertconstantindinescu.core_ui.databinding.ActivityMainBinding
import com.robertconstantindinescu.navigation.NavigationFlow
import com.robertconstantindinescu.navigation.Navigator
import com.robertconstantindinescu.navigation.ToFlowNavigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ToFlowNavigate {
    private val navigator: Navigator = Navigator()
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)

        navigator.navController = navController

        navView.setupWithNavController(navController)
    }
    override fun navigateToFlow(flow: NavigationFlow) {
        navigator.navigateToFlow(flow)
    }
}