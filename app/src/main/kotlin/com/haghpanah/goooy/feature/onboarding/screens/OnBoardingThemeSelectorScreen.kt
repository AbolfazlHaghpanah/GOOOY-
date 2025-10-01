package com.haghpanah.goooy.feature.onboarding.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.lerp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.haghpanah.goooy.R
import com.haghpanah.goooy.common.enums.ThemeType
import com.haghpanah.goooy.feature.onboarding.StartupViewModel
import com.haghpanah.goooy.ui.navigation.GOOOYScreens
import com.haghpanah.goooy.ui.theme.GOOOYTheme
import com.haghpanah.goooy.ui.theme.onSurfaceDark
import com.haghpanah.goooy.ui.theme.onSurfaceLight
import com.haghpanah.goooy.ui.theme.surfaceContainerDark
import com.haghpanah.goooy.ui.theme.surfaceContainerHighestDark
import com.haghpanah.goooy.ui.theme.surfaceContainerHighestLight
import com.haghpanah.goooy.ui.theme.surfaceContainerLight

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun OnBoardingThemeSelectorScreen(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: StartupViewModel = hiltViewModel(),
) {
    val selectedTheme by viewModel.currentTheme.collectAsStateWithLifecycle()

    with(sharedTransitionScope) {
        OnBoardingThemeSelectorScreen(
            selectedTheme = selectedTheme,
            onThemeChange = {
                viewModel.setTheme(it)
            },
            animatedContentScope = animatedContentScope,
            onContinue = {
                //TODO Mark into as viewed
                navController.navigate(GOOOYScreens.Intention) {
                    launchSingleTop = true
                    popUpTo(GOOOYScreens.Intention) {
                        inclusive = true
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SharedTransitionScope.OnBoardingThemeSelectorScreen(
    selectedTheme: ThemeType,
    onThemeChange: (ThemeType) -> Unit,
    animatedContentScope: AnimatedContentScope,
    onContinue: () -> Unit,
) {
    Box(
        modifier = Modifier
            .systemBarsPadding()
            .padding(
                horizontal = 20.dp,
                vertical = 24.dp
            )
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .sharedElement(
                        sharedContentState = this@OnBoardingThemeSelectorScreen
                            .rememberSharedContentState("logo"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.label_goooy),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Icon(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(64.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.goooy_icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = stringResource(R.string.message_theme_selector_message),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.label_chose_your_theme),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                ThemeType.entries.forEach {
                    val isSelectedTransaction = updateTransition(
                        targetState = it == selectedTheme,
                    )
                    val height by isSelectedTransaction.animateDp { isSelected ->
                        if (isSelected)
                            32.dp else 24.dp
                    }
                    val textStyleFraction by isSelectedTransaction.animateFloat { isSelected ->
                        if (isSelected)
                            1f else 0f
                    }

                    Row(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .clickable { onThemeChange(it) }
                            .background(it.getContainerColor())
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = stringResource(it.getTextId()),
                            color = it.getTextColor(),
                            style = lerp(
                                MaterialTheme.typography.titleSmall,
                                MaterialTheme.typography.titleLarge,
                                textStyleFraction
                            ),
                        )

                        Box(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.small)
                                .background(it.getInnerContainerColor())
                                .height(height)
                                .weight(0.5f)
                        )
                    }
                }
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            onClick = onContinue
        ) {
            Text(stringResource(R.string.label_lets_go))
        }
    }
}

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Preview
//@Composable
//private fun ObBoardingThemeSelectorScreenPreview() {
//    GOOOYTheme(ThemeType.Dark) {
//        Scaffold(
//            containerColor = MaterialTheme.colorScheme.surface
//        ) {
//            OnBoardingThemeSelectorScreen(
//                selectedTheme = ThemeType.Dark,
//                onContinue = {},
//                onThemeChange = {}
//            )
//        }
//    }
//}

@Composable
private fun ThemeType.getContainerColor() = when (this) {
    ThemeType.Dark -> SolidColor(surfaceContainerDark)
    ThemeType.Light -> SolidColor(surfaceContainerLight)
    ThemeType.SystemBased -> if (LocalLayoutDirection.current == LayoutDirection.Rtl) {
        Brush.linearGradient(
            0.5f to surfaceContainerDark,
            0.5f to surfaceContainerLight,
        )
    } else {
        Brush.linearGradient(
            0.5f to surfaceContainerLight,
            0.5f to surfaceContainerDark
        )
    }
}

@Composable
private fun ThemeType.getInnerContainerColor() = when (this) {
    ThemeType.Dark -> SolidColor(surfaceContainerHighestDark)
    ThemeType.Light -> SolidColor(surfaceContainerHighestLight)
    ThemeType.SystemBased -> Brush.linearGradient(
        0.5f to surfaceContainerHighestDark,
        0.5f to surfaceContainerHighestLight,
    )
}

@Composable
private fun ThemeType.getTextColor() = when (this) {
    ThemeType.Dark -> onSurfaceDark
    ThemeType.Light -> onSurfaceLight
    ThemeType.SystemBased -> onSurfaceLight
}

@Composable
private fun ThemeType.getTextId() = when (this) {
    ThemeType.Dark -> R.string.label_dark
    ThemeType.Light -> R.string.label_light
    ThemeType.SystemBased -> R.string.label_system_default
}