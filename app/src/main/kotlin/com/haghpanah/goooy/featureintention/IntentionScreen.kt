package com.haghpanah.goooy.featureintention

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import com.haghpanah.goooy.R
import com.haghpanah.goooy.coreui.navigation.GOOOYScreens
import kotlinx.coroutines.delay

@Composable
fun IntentionScreen(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {
    var currentGestureState by remember { mutableStateOf(IntentionGestureState.Idle) }
    var circleRadius by remember { mutableFloatStateOf(0f) }
    var soonReleasedCounter by remember { mutableIntStateOf(0) }
    val hintTextId by remember {
        derivedStateOf {
            if (soonReleasedCounter == 0) {
                R.string.message_hold_to_get_answer
            } else {
                setOf(
                    R.string.message_hold_more_hint_1,
                    R.string.message_hold_more_hint_2,
                    R.string.message_hold_more_hint_3,
                ).random()
            }
        }
    }
    val animatedRadios by animateFloatAsState(
        circleRadius,
        animationSpec = spring(
            dampingRatio = if (currentGestureState == IntentionGestureState.OnHoldReady) {
                Spring.DampingRatioHighBouncy
            } else {
                Spring.DampingRatioNoBouncy
            },
            stiffness = if (currentGestureState == IntentionGestureState.OnHoldReady)
                Spring.StiffnessMedium else Spring.StiffnessVeryLow,
        )
    )
    val navigationThreshold = remember { 700 }

    LaunchedEffect(currentGestureState) {
        when (currentGestureState) {
            IntentionGestureState.Idle -> {
                circleRadius = 0f
            }

            IntentionGestureState.Pressed -> {
                circleRadius = 50f
                currentGestureState = IntentionGestureState.OnHoldNotReady
            }

            IntentionGestureState.OnHoldNotReady -> {
                while (true) {
                    delay(70)
                    if (circleRadius >= navigationThreshold) {
                        currentGestureState = IntentionGestureState.OnHoldReady
                    } else {
                        circleRadius += 70
                    }
                }
            }

            IntentionGestureState.OnHoldReady -> {
                var shouldAdd = false

                while (true) {
                    delay(10)
                    if (!shouldAdd) {
                        circleRadius -= 20
                        shouldAdd = true
                    } else {
                        circleRadius += 20
                        shouldAdd = false
                    }
                }
            }

            IntentionGestureState.Released -> {
                if (circleRadius >= navigationThreshold - 150f) {
                    circleRadius = 2500f
                    delay(200)
                    navController.navigate(GOOOYScreens.Answer) {
                        launchSingleTop = true
                    }
                } else {
                    soonReleasedCounter++
                    currentGestureState = IntentionGestureState.Idle
                }
            }
        }
    }

    with(sharedTransitionScope) {
        IntentionScreen(
            animatedContentScope = animatedContentScope,
            currentGestureState = currentGestureState,
            onGestureStateChanged = {
                currentGestureState = it
            },
            hintTextId = hintTextId,
            circleRadius = animatedRadios,
        )
    }
}

@Composable
private fun SharedTransitionScope.IntentionScreen(
    animatedContentScope: AnimatedContentScope,
    circleRadius: Float,
    @StringRes hintTextId: Int,
    currentGestureState: IntentionGestureState,
    onGestureStateChanged: (IntentionGestureState) -> Unit,
) {
    val circleColor = MaterialTheme.colorScheme.primaryContainer

    Column(
        modifier = Modifier
            .pointerInput(PointerEventType.Press, PointerEventType.Release) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()

                        if (event.type == PointerEventType.Press) {
                            onGestureStateChanged(IntentionGestureState.Pressed)
                        } else if (event.type == PointerEventType.Release) {
                            onGestureStateChanged(IntentionGestureState.Released)
                        }
                    }
                }
            }
            .drawBehind(
                onDraw = {
                    drawCircle(
                        alpha = lerp(0f, 1f, circleRadius / 200),
                        color = circleColor,
                        radius = circleRadius
                    )
                }
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedContent(
            targetState = currentGestureState,
            transitionSpec = {
                scaleIn(
                    spring(
                        dampingRatio = if (currentGestureState == IntentionGestureState.OnHoldReady) {
                            Spring.DampingRatioHighBouncy
                        } else {
                            Spring.DampingRatioNoBouncy
                        },
                        stiffness = if (currentGestureState == IntentionGestureState.OnHoldReady)
                            Spring.StiffnessMedium else Spring.StiffnessVeryLow,
                    )
                ) togetherWith fadeOut(tween(100))
            }
        ) {
            when (it) {
                IntentionGestureState.Idle -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .sharedElement(
                                    sharedContentState = this@IntentionScreen
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

                        Spacer(modifier = Modifier.height(48.dp))

                        Text(
                            text = stringResource(hintTextId),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }

                IntentionGestureState.Pressed, IntentionGestureState.OnHoldNotReady -> {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.label_hold_still),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                IntentionGestureState.OnHoldReady -> {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.label_release),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                }

                IntentionGestureState.Released -> {}
            }
        }
    }
}

private enum class IntentionGestureState {
    Idle,
    Pressed,
    OnHoldNotReady,
    OnHoldReady,
    Released
}