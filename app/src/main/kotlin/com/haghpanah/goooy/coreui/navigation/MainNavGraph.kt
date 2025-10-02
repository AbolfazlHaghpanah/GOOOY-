package com.haghpanah.goooy.coreui.navigation

import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.haghpanah.goooy.featureanswer.AnswerScreen
import com.haghpanah.goooy.featureintention.IntentionScreen
import com.haghpanah.goooy.featureonboarding.screens.OnBoardingLanguageSelectorScreen
import com.haghpanah.goooy.featureonboarding.screens.OnBoardingThemeSelectorScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
) {
    composable<GOOOYScreens.Intention>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        IntentionScreen(
            navController = navController,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
        )
    }
    composable<GOOOYScreens.Answer>(
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        AnswerScreen(navController)
    }
    composable<GOOOYScreens.OnBoardingLanguageSelector>(
        enterTransition = { slideInHorizontally() + fadeIn() },
        exitTransition = { slideOutHorizontally { -it } + fadeOut() }
    ) {
        OnBoardingLanguageSelectorScreen(
            navController = navController,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
        )
    }
    composable<GOOOYScreens.OnBoardingThemeSelector>(
        enterTransition = { slideInHorizontally { it } + fadeIn() },
        exitTransition = { fadeOut() }
    ) {
        OnBoardingThemeSelectorScreen(
            navController = navController,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
        )
    }
}