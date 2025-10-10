package com.haghpanah.goooy.data.setting.repository

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.haghpanah.goooy.data.setting.storage.SettingStorage
import com.haghpanah.goooy.model.AppLanguage
import com.haghpanah.goooy.model.ThemeStyle
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingStorage: SettingStorage,
    @ApplicationContext private val context: Context,
) : SettingRepository {

    override fun observeTheme(): Flow<ThemeStyle?> =
        settingStorage.theme

    override fun observeHasSeenIntro(): Flow<Boolean> =
        settingStorage.hasSeenIntro

    override suspend fun setTheme(themeStyle: ThemeStyle) {
        settingStorage.setTheme(themeStyle)
    }

    override suspend fun setLanguage(language: AppLanguage) {
        val appLocale = LocaleListCompat.forLanguageTags(language.tag)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    override fun getCurrentLanguage(): AppLanguage? {
        val currentLocal = if (Build.VERSION.SDK_INT >= 33) {
            val localManager =
                context.getSystemService(LocaleManager::class.java)

            val local = localManager.applicationLocales.get(0)

            if (local == null) {
                localManager.applicationLocales =
                    LocaleList.forLanguageTags(AppLanguage.entries.joinToString { it.tag })
            }

            localManager.applicationLocales.get(0)
        } else {
            AppCompatDelegate.getApplicationLocales().get(0)
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