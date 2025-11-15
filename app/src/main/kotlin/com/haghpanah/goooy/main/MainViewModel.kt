package com.haghpanah.goooy.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haghpanah.goooy.model.ThemeStyle
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
            it ?: ThemeStyle.getDefault()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ThemeStyle.Dark
        )

    val hasSeenIntro = settingRepository.observeHasSeenIntro()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )
}