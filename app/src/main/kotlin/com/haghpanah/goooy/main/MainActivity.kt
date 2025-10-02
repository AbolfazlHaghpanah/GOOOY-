package com.haghpanah.goooy.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.haghpanah.goooy.featureanswer.AnswerScreen
import com.haghpanah.goooy.featureintention.IntentionScreen
import com.haghpanah.goooy.featureonboarding.screens.OnBoardingLanguageSelectorScreen
import com.haghpanah.goooy.featureonboarding.screens.OnBoardingThemeSelectorScreen
import com.haghpanah.goooy.coreui.navigation.GOOOYScreens
import com.haghpanah.goooy.coreui.theme.GOOOYTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

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
                    shouldStayOnSplash = false
                }
            }

            GOOOYTheme(currentTheme) {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentWindowInsets = WindowInsets()
                ) {
                    @OptIn(ExperimentalSharedTransitionApi::class)
                    SharedTransitionLayout {
                        NavHost(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface),
                            navController = navController,
                            startDestination = if (hasSeenIntro ?: false) {
                                GOOOYScreens.Intention
                            } else {
                                GOOOYScreens.OnBoardingLanguageSelector
                            },
                            enterTransition = { fadeIn() },
                            exitTransition = { fadeOut() }
                        ) {
                            composable<GOOOYScreens.Intention> {
                                IntentionScreen(
                                    navController = navController,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedContentScope = this@composable,
                                )
                            }
                            composable<GOOOYScreens.Answer> {
                                AnswerScreen(navController)
                            }
                            composable<GOOOYScreens.OnBoardingLanguageSelector>(
                                enterTransition = { slideInHorizontally() + fadeIn() },
                                exitTransition = { slideOutHorizontally { -it } + fadeOut() }
                            ) {
                                OnBoardingLanguageSelectorScreen(
                                    navController = navController,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedContentScope = this@composable,
                                )
                            }
                            composable<GOOOYScreens.OnBoardingThemeSelector>(
                                enterTransition = { slideInHorizontally{ it} + fadeIn() },
                                exitTransition = { fadeOut() }
                            ) {
                                OnBoardingThemeSelectorScreen(
                                    navController = navController,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedContentScope = this@composable,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}