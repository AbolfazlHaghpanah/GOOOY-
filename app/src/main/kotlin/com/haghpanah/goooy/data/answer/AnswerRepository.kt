package com.haghpanah.goooy.data.answer

import com.haghpanah.goooy.model.AnswerResult

interface AnswerRepository {
    fun getAnswer(): AnswerResult
    fun blockAnswer(id: Int)
}