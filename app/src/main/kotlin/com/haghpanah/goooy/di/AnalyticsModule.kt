package com.haghpanah.goooy.di

import com.haghpanah.goooy.analytics.AnalyticsManager
import com.haghpanah.goooy.analytics.FirebaseAnalyticsManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface AnalyticsModule {

    @Binds
    @Singleton
    fun bindAnalyticsManager(
        firebaseAnalyticsManagerImpl: FirebaseAnalyticsManagerImpl,
    ): AnalyticsManager
}
