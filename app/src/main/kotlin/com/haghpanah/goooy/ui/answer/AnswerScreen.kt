package com.haghpanah.goooy.ui.answer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.haghpanah.goooy.data.answers
import kotlin.random.Random

@Composable
fun AnswerScreen(navController: NavController) {
    val answer = remember { answers[Random.nextInt(from = 0, answers.size - 1)] }

    Scaffold(
        containerColor = answer.color
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = answer.emoji,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(answer.contentTextId),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}