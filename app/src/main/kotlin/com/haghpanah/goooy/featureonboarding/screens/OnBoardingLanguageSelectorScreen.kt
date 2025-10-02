package com.haghpanah.goooy.featureonboarding.screens

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.haghpanah.goooy.R
import com.haghpanah.goooy.model.enums.AppLanguage
import com.haghpanah.goooy.featureonboarding.OnBoardingViewModel
import com.haghpanah.goooy.coreui.navigation.GOOOYScreens

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun OnBoardingLanguageSelectorScreen(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    viewModel: OnBoardingViewModel = hiltViewModel(),
) {
    val selectedLanguage by viewModel.currentLanguage.collectAsStateWithLifecycle()

    with(sharedTransitionScope) {
        OnBoardingLanguageSelectorScreen(
            selectedLanguage = selectedLanguage,
            onLanguageSelected = {
                viewModel.setLanguage(
                    language = it,
                )
            },
            animatedContentScope = animatedContentScope,
            onContinue = {
                navController.navigate(GOOOYScreens.OnBoardingThemeSelector) {
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
private fun SharedTransitionScope.OnBoardingLanguageSelectorScreen(
    selectedLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
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
                        sharedContentState = this@OnBoardingLanguageSelectorScreen
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
                text = stringResource(R.string.message_intorduction_description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.label_select_language),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                AppLanguage.entries.forEachIndexed { index, language ->
                    TextButton(
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = animateColorAsState(
                                if (selectedLanguage == language) {
                                    MaterialTheme.colorScheme.surfaceContainerHigh
                                } else {
                                    ButtonDefaults.textButtonColors().containerColor
                                }
                            ).value,
                            contentColor = animateColorAsState(
                                if (selectedLanguage == language) {
                                    MaterialTheme.colorScheme.onSurface
                                } else {
                                    ButtonDefaults.textButtonColors().contentColor
                                }
                            ).value
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onLanguageSelected(language) }
                    ) {
                        Text(language.getTextId())
                    }

                    if (index != AppLanguage.entries.lastIndex) {
                        HorizontalDivider()
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
            Text(stringResource(R.string.label_continue))
        }
    }
}

fun AppLanguage.getTextId() =
    when (this) {
        AppLanguage.En -> "English"
        AppLanguage.Fa -> "فارسی"
    }
//
//@Preview
//@Composable
//private fun IntroductionScreenPreview() {
//    GOOOYTheme(ThemeType.Dark) {
//        Box(
//            modifier = Modifier.background(MaterialTheme.colorScheme.background)
//        ) {
//            OnBoardingLanguageSelectorScreen(
//                selectedLanguage = AppLanguage.Fa,
//                onLanguageSelected = {},
//                onContinue = {}
//            )
//        }
//
//    }
//}