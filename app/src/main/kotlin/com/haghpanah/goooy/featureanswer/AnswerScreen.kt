package com.haghpanah.goooy.featureanswer

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.haghpanah.goooy.R
import com.haghpanah.goooy.model.Answer
import com.haghpanah.goooy.model.AnswerResult
import com.haghpanah.goooy.model.AnswerType

@Composable
fun AnswerScreen(
    navController: NavController,
) {
    val viewModel = hiltViewModel<AnswerViewModel>()
    val answer by viewModel.answer.collectAsStateWithLifecycle()
    var circleRadius by remember { mutableFloatStateOf(2500f) }
    val animatedRadios by animateFloatAsState(
        targetValue = circleRadius,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessVeryLow,
        )
    )

    LaunchedEffect(answer) {
        if (answer != null) {
            circleRadius = 0f
        }
    }

    LaunchedEffect(animatedRadios) {
        if (animatedRadios >= 2300f) {
            viewModel.onDidNotLikeTheAnswerClicked {
                viewModel.getAnswer()
            }
        }
    }

    if (answer != null) {
        AnswerScreen(
            answerResult = answer!!,
            onBackPressed = { navController.navigateUp() },
            onDidNotLikeAnswerClicked = {
                circleRadius = 2500f
            },
            circleRadius = animatedRadios,
        )
    }
}

@Composable
fun AnswerScreen(
    answerResult: AnswerResult,
    circleRadius: Float,
    onBackPressed: () -> Unit,
    onDidNotLikeAnswerClicked: () -> Unit,
) {
    val circleColor = MaterialTheme.colorScheme.primaryContainer

    Column(
        modifier = Modifier
            .drawWithContent(
                onDraw = {
                    drawContent()
                    drawCircle(
                        alpha = lerp(0f, 1f, circleRadius / 1000),
                        color = circleColor,
                        radius = circleRadius,
                        center = Offset(this.center.x, this.size.height - 48.dp.toPx())
                    )
                }
            )
            .systemBarsPadding()
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = onBackPressed
            )
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .fillMaxWidth()
                .background(answerResult.answer.getColor())
                .padding(24.dp)
                .offset(circleRadius.dp/300)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = answerResult.answer.name,
                color = Color.White,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.width(64.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .offset(x = circleRadius.dp/300, y =  circleRadius.dp/100)
                    .clip(MaterialTheme.shapes.medium)
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(vertical = 48.dp, horizontal = 28.dp),
                text = answerResult.answer.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.headlineSmall,
            )

            Text(
                modifier = Modifier
                    .offset(y = circleRadius.dp/100)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(vertical = 16.dp, horizontal = 28.dp),
                text = answerResult.answer.emoji,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onDidNotLikeAnswerClicked
        ) {
            Text(stringResource(R.string.label_did_not_liked_the_answer))
        }
    }
}

fun Answer.getColor() = when (type) {
    AnswerType.Positive -> Color(0xFF4CAF50)
    AnswerType.Negative -> Color(0xFFF44336)
    AnswerType.Silly -> Color(0xFF9C27B0)
    AnswerType.Natural -> Color(0xFF9E9E9E)
}
