package com.haghpanah.goooy.featureanswer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.haghpanah.goooy.model.answer.Answer
import com.haghpanah.goooy.model.answer.AnswerType

@Composable
fun AnswerScreen(
    navController: NavController,
) {
    val viewModel = hiltViewModel<AnswerViewModel>()
    val answer by viewModel.answer.collectAsStateWithLifecycle()

    if (answer != null) {
        AnswerScreen(
            answer = answer!!,
            onBackPressed = { navController.navigateUp() }
        )
    }
}

@Composable
private fun AnswerScreen(
    answer: Answer,
    onBackPressed: () -> Unit,
) {
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .clickable(
                indication = null,
                interactionSource = null,
                onClick = onBackPressed
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .background(color = answer.getColor())
                .statusBarsPadding()
                .padding(
                    horizontal = 20.dp,
                    vertical = 64.dp
                )
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = answer.name,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.width(24.dp))

                Text(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(24.dp),
                    text = answer.emoji,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Text(
            text = answer.description,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(Modifier.weight(1f))

        TextButton(
            modifier = Modifier
                .padding(24.dp),
            //TODo
            onClick = onBackPressed
        ) {
            Text("Did Not Like It? Try Again")
        }
    }
}

fun Answer.getColor() = when (type) {
    AnswerType.Positive -> Color(0xFF4CAF50)
    AnswerType.Negative -> Color(0xFFF44336)
    AnswerType.Silly -> Color(0xFF9C27B0)
    AnswerType.Natural -> Color(0xFF9E9E9E)
}
