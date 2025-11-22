package com.haghpanah.goooy.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseAnalyticsManagerImpl @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics,
) : AnalyticsManager {

    override fun sendEvent(name: String, params: Map<String, String>) {
        val params = Bundle().apply {
            params.entries.forEach { (key, value) ->
                putString(key, value)
            }
        }

        firebaseAnalytics.logEvent(name, params)
    }
}
