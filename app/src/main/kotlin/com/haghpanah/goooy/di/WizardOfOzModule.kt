package com.haghpanah.goooy.di

import com.haghpanah.goooy.data.wizardofoz.WizardOfOz
import com.haghpanah.goooy.data.wizardofoz.WizardOfOzImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class WizardOfOzModule {

    @Binds
    @Singleton
    abstract fun bindWizardOfOz(
        wizardOfOzImpl: WizardOfOzImpl,
    ): WizardOfOz

}