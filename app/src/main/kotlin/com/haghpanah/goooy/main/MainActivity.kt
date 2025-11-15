package com.haghpanah.goooy.main

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.haghpanah.goooy.coreui.navigation.GOOOYScreens.InitialScreenDecider
import com.haghpanah.goooy.coreui.navigation.GOOOYScreens.Intention
import com.haghpanah.goooy.coreui.navigation.GOOOYScreens.OnBoardingLanguageSelector
import com.haghpanah.goooy.coreui.navigation.mainNavGraph
import com.haghpanah.goooy.coreui.theme.GOOOYTheme
import com.haghpanah.goooy.model.ThemeStyle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var shouldStayOnSplash = true
        installSplashScreen().apply {
            setKeepOnScreenCondition { shouldStayOnSplash }
        }

        setContent {
            val viewModel = hiltViewModel<MainViewModel>()
            val navController = rememberNavController()
            val currentTheme by viewModel.currentTheme.collectAsStateWithLifecycle()
            val hasSeenIntro by viewModel.hasSeenIntro.collectAsStateWithLifecycle()
            val isSystemDark = isSystemInDarkTheme()
            val isLightTheme by remember {
                derivedStateOf {
                    when (currentTheme) {
                        ThemeStyle.Dark -> false
                        ThemeStyle.Light -> true
                        ThemeStyle.SystemBased -> !isSystemDark
                    }
                }
            }
            ApplyStatusBarColors(isLightTheme)

            GOOOYTheme(isLightTheme) {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentWindowInsets = WindowInsets()
                ) {
                    SharedTransitionLayout {
                        NavHost(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface),
                            navController = navController,
                            startDestination = InitialScreenDecider
                        ) {
                            composable<InitialScreenDecider>(
                                enterTransition = null,
                                exitTransition = null
                            ) {
                                Box(Modifier.fillMaxSize())

                                LaunchedEffect(hasSeenIntro) {
                                    if (hasSeenIntro == true) {
                                        navController.navigate(Intention) {
                                            popUpTo<InitialScreenDecider> {
                                                inclusive = true
                                            }
                                        }
                                    } else if (hasSeenIntro == false) {
                                        navController.navigate(OnBoardingLanguageSelector) {
                                            popUpTo<InitialScreenDecider> {
                                                inclusive = true
                                            }
                                        }
                                    }
                                }

                                DisposableEffect(Unit) {
                                    onDispose {
                                        shouldStayOnSplash = false
                                    }
                                }
                            }

                            mainNavGraph(
                                navController = navController,
                                sharedTransitionScope = this@SharedTransitionLayout
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MainActivity.ApplyStatusBarColors(isLightTheme: Boolean) {
    SideEffect {
        enableEdgeToEdge(
            statusBarStyle = if (isLightTheme) {
                SystemBarStyle.light(
                    scrim = Color.Transparent.toArgb(),
                    darkScrim = Color.Transparent.toArgb()
                )
            } else {
                SystemBarStyle.dark(Color.Transparent.toArgb())
            },
            navigationBarStyle = if (isLightTheme) {
                SystemBarStyle.light(
                    scrim = Color.Transparent.toArgb(),
                    darkScrim = Color.Transparent.toArgb()
                )
            } else {
                SystemBarStyle.dark(Color.Transparent.toArgb())
            }
        )
    }
}