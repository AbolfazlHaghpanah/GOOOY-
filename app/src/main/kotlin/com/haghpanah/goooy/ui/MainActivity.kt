package com.haghpanah.goooy.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.haghpanah.goooy.feature.answer.AnswerScreen
import com.haghpanah.goooy.feature.intention.IntentionScreen
import com.haghpanah.goooy.feature.onboarding.screens.IntroductionScreen
import com.haghpanah.goooy.ui.navigation.GOOOYScreens
import com.haghpanah.goooy.ui.theme.GOOOYTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var shouldStayOnSplash = true
        installSplashScreen().apply {
            setKeepOnScreenCondition { shouldStayOnSplash }
        }

        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val navController = rememberNavController()
            val currentTheme by viewModel.currentTheme.collectAsStateWithLifecycle()
            val hasSeenIntro by viewModel.hasSeenIntro.collectAsStateWithLifecycle()

            LaunchedEffect(hasSeenIntro) {
                if (hasSeenIntro != null) {
                    delay(100)
                    shouldStayOnSplash = true
                }
            }

            GOOOYTheme(currentTheme) {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentWindowInsets = WindowInsets()
                ) {
                    NavHost(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface),
                        navController = navController,
                        startDestination = if (hasSeenIntro ?: false) {
                            GOOOYScreens.Intention
                        } else {
                            GOOOYScreens.Introduction
                        },
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
                        composable<GOOOYScreens.Introduction> {
                            IntroductionScreen(navController)
                        }
                        composable<GOOOYScreens.LanguageSelector> {

                        }
                        composable<GOOOYScreens.ThemeSelector> {

                        }
                    }
                }
            }
        }
    }
}