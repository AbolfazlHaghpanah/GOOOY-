package com.haghpanah.goooy.data.wizardofoz

import android.content.Context
import android.content.res.AssetManager
import androidx.compose.material3.TimePicker
import com.haghpanah.goooy.data.setting.repository.SettingRepository
import com.haghpanah.goooy.model.AppLanguage
import com.haghpanah.goooy.model.answer.Answer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.combineTransform
import kotlinx.serialization.json.Json
import java.util.Date
import javax.inject.Inject

class WizardOfOzImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingRepository: SettingRepository,
    private val json: Json,
) : WizardOfOz {
    val blockedAnswers = setOf<Answer>()
    var lateAnswer: Answer? = null
    private var cachedAnswers: List<Answer> = listOf()

    override fun getAnswer(): Answer {
        val currentTimeIn24 = Date(System.currentTimeMillis()).time

        val answers = loadAnswers()

        return answers.first()
    }

    override fun decreaseAnswerWeight(answerId: Int) {
        //TODO
    }

    fun loadAnswers(): List<Answer> {
        if (cachedAnswers.isNotEmpty()) {
            return cachedAnswers
        }

        val currentLocale = settingRepository.getCurrentLanguage()
            ?: AppLanguage.getDefault()

        val answersJson = context.assets
            .open("answers/answers-${currentLocale.tag}.json")
            .bufferedReader()
            .readText()

        cachedAnswers = json.decodeFromString(answersJson)

        return cachedAnswers
    }

    private fun calculateWeights(answers: List<Answer>) {

    }

    private fun decreaseAnswerPoint() {

    }

    companion object {
    }
}
