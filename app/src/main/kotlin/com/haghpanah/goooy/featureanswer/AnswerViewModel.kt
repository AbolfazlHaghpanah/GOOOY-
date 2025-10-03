package com.haghpanah.goooy.featureanswer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haghpanah.goooy.data.wizardofoz.WizardOfOz
import com.haghpanah.goooy.model.answer.Answer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnswerViewModel @Inject constructor(
    private val wizardOfOz: WizardOfOz,
) : ViewModel() {
    private val _answer = MutableStateFlow<Answer?>(null)
    val answer = _answer.asStateFlow()

    init {
        getAnswer()
    }

    private fun getAnswer() {
        viewModelScope.launch {
            val result = wizardOfOz.getAnswer()

            _answer.emit(result)
        }
    }
}