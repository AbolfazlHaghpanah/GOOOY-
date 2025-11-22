package com.haghpanah.goooy.di

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.haghpanah.goooy.analytics.AnalyticsManager
import com.haghpanah.goooy.analytics.FirebaseAnalyticsManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
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

    companion object {

        @Provides
        fun provideFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics
    }
}
