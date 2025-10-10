package com.haghpanah.goooy.data.wizardofoz

import android.content.Context
import com.haghpanah.goooy.data.setting.repository.SettingRepository
import com.haghpanah.goooy.data.wizardofoz.weightingrules.extention.applyWeightingRules
import com.haghpanah.goooy.data.wizardofoz.weightingrules.rules.AllNegative
import com.haghpanah.goooy.data.wizardofoz.weightingrules.rules.AllPositive
import com.haghpanah.goooy.data.wizardofoz.weightingrules.rules.BlockedOnes
import com.haghpanah.goooy.data.wizardofoz.weightingrules.rules.DontBeNormal
import com.haghpanah.goooy.data.wizardofoz.weightingrules.rules.MatchesTime
import com.haghpanah.goooy.data.wizardofoz.weightingrules.rules.NoStupidity
import com.haghpanah.goooy.data.wizardofoz.weightingrules.rules.NotMatchTime
import com.haghpanah.goooy.data.wizardofoz.weightingrules.rules.NotTheSameAnswer
import com.haghpanah.goooy.model.Answer
import com.haghpanah.goooy.model.AppLanguage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates
import kotlin.random.Random

@Singleton
class WizardOfOzImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingRepository: SettingRepository,
    private val json: Json,
) : WizardOfOz {
    private var cachedAnswersWithLocal: Pair<AppLanguage, List<Answer>>? by Delegates
        .observable(
            null
        ) { _, _, newValue ->
            currentAnswers.removeAll { true }
            currentAnswers.addAll(newValue?.second.orEmpty())
        }

    private val currentAnswers: MutableList<Answer> = mutableListOf()
    override val seenAnswerIds: MutableList<Int> = mutableListOf()
    override val blockedAnswerIds: MutableList<Int> = mutableListOf()

    override fun getAnswer(): Answer {
        loadAnswers()

        val weightedAnswers = currentAnswers
            .applyWeightingRules(
                listOf(
                    MatchesTime(),
                    AllNegative(),
                    AllPositive(),
                    NoStupidity(),
                    DontBeNormal(),
                    NotTheSameAnswer(seenAnswerIds),
                    BlockedOnes(blockedAnswerIds),
                    NotMatchTime(),
                )
            )
            .map { answer ->
                List(answer.weight) {
                    answer
                }
            }.flatMap {
                it
            }
            .shuffled()

        return weightedAnswers[Random.nextInt(from = 0, weightedAnswers.size - 1)].also {
            seenAnswerIds.add(it.id)
        }
    }

    override fun addAnswerToBlockedList(answerId: Int) {
        blockedAnswerIds.add(answerId)
    }

    fun loadAnswers(): List<Answer> {
        val currentLocale = settingRepository.getCurrentLanguage()
            ?: AppLanguage.getDefault()
        val cachedLocale = cachedAnswersWithLocal?.first
        val cachedAnswers = cachedAnswersWithLocal?.second
        if (!cachedAnswers.isNullOrEmpty() && currentLocale == cachedLocale) {
            return cachedAnswers
        }

        val answersJson = context.assets
            .open("answers/answers-${currentLocale.tag}.json")
            .bufferedReader()
            .readText()

        cachedAnswersWithLocal = currentLocale to json.decodeFromString(answersJson)

        return requireNotNull(cachedAnswersWithLocal?.second) {
            "Suddenly cachedAnswersWithLocal become null :("
        }
    }
}

