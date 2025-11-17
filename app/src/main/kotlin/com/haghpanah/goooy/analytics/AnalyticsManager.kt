package com.haghpanah.goooy.analytics

interface AnalyticsManager {
    fun sendEvent(name: String, params: Map<String, String>)
}