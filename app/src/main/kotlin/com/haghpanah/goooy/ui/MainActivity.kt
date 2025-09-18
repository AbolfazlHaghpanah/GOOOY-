package com.haghpanah.goooy.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.haghpanah.goooy.feature.answer.AnswerScreen
import com.haghpanah.goooy.feature.intention.IntentionScreen
import com.haghpanah.goooy.ui.navigation.GOOOYScreens
import com.haghpanah.goooy.ui.theme.GOOOYTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GOOOYTheme {
                val navController = rememberNavController()

                NavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    navController = navController,
                    startDestination = GOOOYScreens.Intention,
                    enterTransition = { fadeIn() },
                    exitTransition = { fadeOut() }
                ) {
                    composable<GOOOYScreens.Intention> {
                        IntentionScreen(navController)
                    }
                    composable<GOOOYScreens.Answer> {
                        AnswerScreen(navController)
                    }
                    composable<GOOOYScreens.Setting> {

                    }
                }
            }
        }
    }
}