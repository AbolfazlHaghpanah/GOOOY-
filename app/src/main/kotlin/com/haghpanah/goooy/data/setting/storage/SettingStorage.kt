package com.haghpanah.goooy.data.setting.storage

import com.haghpanah.goooy.common.enums.ThemeType
import kotlinx.coroutines.flow.Flow

interface SettingStorage {
    val theme: Flow<ThemeType?>
    val hasSeenIntro: Flow<Boolean>
    suspend fun setTheme(value: ThemeType)
    suspend fun setHasSeenIntro(value: Boolean)
}