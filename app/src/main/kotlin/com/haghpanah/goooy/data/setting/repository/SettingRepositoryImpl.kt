package com.haghpanah.goooy.data.setting.repository

import android.app.LocaleConfig
import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.haghpanah.goooy.common.enums.AppLanguage
import com.haghpanah.goooy.common.enums.ThemeType
import com.haghpanah.goooy.data.setting.storage.SettingStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingStorage: SettingStorage,
    @ApplicationContext private val context: Context,
) : SettingRepository {
    @RequiresApi(33)
    private val localManager =
        context.getSystemService(LocaleManager::class.java)

    override fun observeTheme(): Flow<ThemeType?> =
        settingStorage.theme

    override fun observeHasSeenIntro(): Flow<Boolean> =
        settingStorage.hasSeenIntro

    override fun initiateLocales() {
        if (Build.VERSION.SDK_INT >= 34) {
            context.getSystemService(LocaleManager::class.java).apply {
                overrideLocaleConfig = LocaleConfig(
                    LocaleList.forLanguageTags(
                        AppLanguage.entries.joinToString(separator = ",") { it.tag }
                    )
                )
            }
        }
    }

    override suspend fun setTheme(themeType: ThemeType) {
        settingStorage.setTheme(themeType)
    }

    override suspend fun setLanguage(language: AppLanguage) {
        val appLocale = LocaleListCompat.forLanguageTags(language.tag)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    override fun getCurrentLanguage(): AppLanguage? {
        val currentLocal = if (Build.VERSION.SDK_INT >= 33) {
            localManager.applicationLocales.get(0)
        } else {
            context.resources.configuration.locales[0]
        }

        return AppLanguage.entries.firstOrNull {
            currentLocal
                ?.toLanguageTag()
                ?.contains(it.tag)
                ?: false
        }
    }

    override suspend fun setHasSeenIntro(value: Boolean) {
        settingStorage.setHasSeenIntro(value)
    }
}