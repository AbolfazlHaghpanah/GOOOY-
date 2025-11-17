package com.haghpanah.goooy.analytics

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAnalyticsManager = staticCompositionLocalOf<AnalyticsManager> {
    error("No AnalyticsManager Provided")
}