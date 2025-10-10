package com.haghpanah.goooy.data.setting.repository

import com.haghpanah.goooy.model.AppLanguage
import com.haghpanah.goooy.model.ThemeStyle
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun observeTheme(): Flow<ThemeStyle?>
    fun observeHasSeenIntro() : Flow<Boolean>
    fun getCurrentLanguage(): AppLanguage?
    suspend fun setTheme(themeStyle: ThemeStyle)
    suspend fun setLanguage(language: AppLanguage)
    suspend fun setHasSeenIntro(value : Boolean)
}