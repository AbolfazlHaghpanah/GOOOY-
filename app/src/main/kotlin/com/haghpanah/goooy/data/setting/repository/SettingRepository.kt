package com.haghpanah.goooy.data.setting.repository

import com.haghpanah.goooy.common.enums.AppLanguage
import com.haghpanah.goooy.common.enums.ThemeType
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun observeTheme(): Flow<ThemeType?>
    fun observeHasSeenIntro() : Flow<Boolean>
    fun initiateLocales()
    fun getCurrentLanguage(): AppLanguage?
    suspend fun setTheme(themeType: ThemeType)
    suspend fun setLanguage(language: AppLanguage)
    suspend fun setHasSeenIntro(value : Boolean)
}