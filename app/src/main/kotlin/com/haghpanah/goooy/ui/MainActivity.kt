package com.haghpanah.goooy.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.haghpanah.goooy.ui.navigation.GOOOYScreens
import com.haghpanah.goooy.ui.theme.GOOOYTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GOOOYTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = GOOOYScreens.Intention
                ) {
                    composable<GOOOYScreens.Intention> {

                    }
                    composable<GOOOYScreens.Answer> {

                    }
                    composable<GOOOYScreens.Setting> {

                    }
                }
            }
        }
    }
}