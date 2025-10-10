package com.haghpanah.goooy.di

import com.haghpanah.goooy.data.answer.AnswerRepository
import com.haghpanah.goooy.data.answer.AnswerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface AnswerModule {

    @Binds
    fun bindAnswerRepository(
        answerRepositoryImpl: AnswerRepositoryImpl
    ): AnswerRepository
}