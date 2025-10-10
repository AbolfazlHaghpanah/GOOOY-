package com.haghpanah.goooy.data.answer

import com.haghpanah.goooy.data.wizardofoz.WizardOfOz
import com.haghpanah.goooy.model.AnswerResult
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AnswerRepositoryImpl @Inject constructor(
    private val wizardOfOz: WizardOfOz,
) : AnswerRepository {
    override fun getAnswer(): AnswerResult {
        val answer = wizardOfOz.getAnswer()
        val isAnswerBlocked = wizardOfOz.blockedAnswerIds.contains(answer.id)
        val seenAnswerCount = wizardOfOz.seenAnswerIds.count { it == answer.id }

        return AnswerResult(
            answer = answer,
            seenCount = seenAnswerCount,
            isBlocked = isAnswerBlocked
        )
    }

    override fun blockAnswer(id: Int) {
        wizardOfOz.addAnswerToBlockedList(id)
    }
}