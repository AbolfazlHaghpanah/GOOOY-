package com.haghpanah.goooy.featureanswer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
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
fun AnswerScreen(
    answer: Answer,
    onBackPressed: () -> Unit,
) {
    Column(
        modifier = Modifier
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
                .background(answer.getColor())
                .padding(24.dp)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = answer.name,
                color = Color.White,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.width(64.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(vertical = 48.dp, horizontal = 28.dp),
                text = answer.description,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.headlineSmall,
            )

            Text(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(vertical = 16.dp, horizontal = 28.dp),
                text = answer.emoji,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

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
