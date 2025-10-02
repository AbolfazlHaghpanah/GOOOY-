package com.haghpanah.goooy.data.setting.storage

import com.haghpanah.goooy.model.enums.ThemeStyle
import kotlinx.coroutines.flow.Flow

interface SettingStorage {
    val theme: Flow<ThemeStyle?>
    val hasSeenIntro: Flow<Boolean>
    suspend fun setTheme(value: ThemeStyle)
    suspend fun setHasSeenIntro(value: Boolean)
}