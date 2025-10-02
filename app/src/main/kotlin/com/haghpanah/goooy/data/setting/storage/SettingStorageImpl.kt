package com.haghpanah.goooy.data.setting.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.haghpanah.goooy.model.enums.ThemeStyle
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingStorageImpl @Inject constructor(
    @ApplicationContext context: Context,
) : SettingStorage {
    val preferences = context.datastore

    override val theme: Flow<ThemeStyle?>
        get() = preferences.data.map { preferences ->
            ThemeStyle.entries.find { it.id == preferences[THEME_KEY] }
        }

    override val hasSeenIntro: Flow<Boolean>
        get() = preferences.data.map { preferences ->
            preferences[HAS_SEEN_INTRO_KEY] ?: false
        }

    override suspend fun setTheme(value: ThemeStyle) {
        preferences.edit { preferences ->
            preferences[THEME_KEY] = value.id
        }
    }

    override suspend fun setHasSeenIntro(value: Boolean) {
        preferences.edit { preferences ->
            preferences[HAS_SEEN_INTRO_KEY] = value
        }
    }

    companion object {
        private val Context.datastore by preferencesDataStore("setting-storage")
        private val THEME_KEY = intPreferencesKey(
            name = "theme-type"
        )
        private val HAS_SEEN_INTRO_KEY = booleanPreferencesKey(
            name = "has-seen-intro"
        )
    }
}