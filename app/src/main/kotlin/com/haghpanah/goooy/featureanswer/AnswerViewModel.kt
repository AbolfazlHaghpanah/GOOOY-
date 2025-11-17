package com.haghpanah.goooy.featureanswer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haghpanah.goooy.analytics.AnalyticsManager
import com.haghpanah.goooy.data.answer.AnswerRepository
import com.haghpanah.goooy.model.AnswerResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnswerViewModel @Inject constructor(
    private val repository: AnswerRepository,
    private val analyticsManager: AnalyticsManager,
) : ViewModel() {
    private val _answer = MutableStateFlow<AnswerResult?>(null)
    val answer = _answer.asStateFlow()

    init {
        getAnswer()
    }

    fun onDidNotLikeTheAnswerClicked(
        onComplete: () -> Unit,
    ) {
        viewModelScope.launch {
            val answerId = answer.value
                ?.answer
                ?.id

            analyticsManager.sendEvent(
                "block-answer", mapOf(
                    "answer-name" to answer.value
                        ?.answer
                        ?.name
                        .toString(),
                    "answer-type" to answer.value
                        ?.answer
                        ?.type
                        ?.value
                        .toString(),
                )
            )
            if (answerId != null) {
                repository.blockAnswer(answerId)
            }

            _answer.emit(null)
            onComplete()
        }
    }

    fun getAnswer() {
        viewModelScope.launch {
            val result = repository.getAnswer()

            analyticsManager.sendEvent(
                "get-answer", mapOf(
                    "answer-name" to result.answer.name,
                    "answer-type" to result.answer.type.value,
                )
            )
            _answer.emit(result)
        }
    }
}