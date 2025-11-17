package com.haghpanah.goooy.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FirebaseAnalyticsManagerImpl @Inject constructor(
    @ApplicationContext context: Context,
) : AnalyticsManager {
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun sendEvent(name: String, params: Map<String, String>) {
        val params = Bundle().apply {
            params.entries.forEach { (key, value) ->
                putString(key, value)
            }
        }

        firebaseAnalytics.logEvent(name, params)
    }
}
