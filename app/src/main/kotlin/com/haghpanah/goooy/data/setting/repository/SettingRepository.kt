package com.haghpanah.goooy.data.setting.repository

import com.haghpanah.goooy.model.enums.AppLanguage
import com.haghpanah.goooy.model.enums.ThemeStyle
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun observeTheme(): Flow<ThemeStyle?>
    fun observeHasSeenIntro() : Flow<Boolean>
    fun initiateLocales()
    fun getCurrentLanguage(): AppLanguage?
    suspend fun setTheme(themeStyle: ThemeStyle)
    suspend fun setLanguage(language: AppLanguage)
    suspend fun setHasSeenIntro(value : Boolean)
}