package com.haghpanah.goooy.featureanswer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haghpanah.goooy.data.answer.AnswerRepository
import com.haghpanah.goooy.data.wizardofoz.WizardOfOz
import com.haghpanah.goooy.model.Answer
import com.haghpanah.goooy.model.AnswerResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnswerViewModel @Inject constructor(
    private val repository: AnswerRepository,
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
            val answerId = answer.value?.answer?.id
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

            _answer.emit(result)
        }
    }
}