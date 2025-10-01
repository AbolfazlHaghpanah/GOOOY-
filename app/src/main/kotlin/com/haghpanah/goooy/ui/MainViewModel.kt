package com.haghpanah.goooy.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haghpanah.goooy.common.enums.AppLanguage
import com.haghpanah.goooy.common.enums.ThemeType
import com.haghpanah.goooy.data.setting.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    settingRepository: SettingRepository,
) : ViewModel() {
    val currentTheme = settingRepository.observeTheme()
        .map {
            it ?: ThemeType.Dark
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ThemeType.Dark
        )

    val hasSeenIntro = settingRepository.observeHasSeenIntro()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )
}