package com.haghpanah.goooy.analytics


/**
 * This could be used in Compose previews to avoid No AnalyticsManager Provided error.
 */
class NoOpAnalyticsManager : AnalyticsManager {
    override fun sendEvent(
        name: String,
        params: Map<String, String>,
    ) = Unit
}