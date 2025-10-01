package com.haghpanah.goooy.feature.startup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haghpanah.goooy.common.enums.AppLanguage
import com.haghpanah.goooy.common.enums.ThemeType
import com.haghpanah.goooy.data.setting.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartupViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
) : ViewModel() {
    val currentTheme = settingRepository.observeTheme()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = ThemeType.Dark
        )

    private val _currentLanguage = MutableStateFlow(AppLanguage.getDefault())
    val currentLanguage = _currentLanguage.asStateFlow()

    fun setLanguage(
        language: AppLanguage,
    ) {
        viewModelScope.launch {
            try {
                settingRepository.setLanguage(language)
                _currentLanguage.emit(language)
            } catch (e: CancellationException) {
                throw e
            } catch (t: Throwable) {
                Log.e("error", "$t")
            }
        }
    }

    fun markIntroSeen(
        onComplete: suspend () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                settingRepository.setHasSeenIntro(true)
                onComplete()
            } catch (e: CancellationException) {
                throw e
            } catch (t: Throwable) {
                Log.e("error", "$t")
            }
        }
    }

    fun setTheme(theme: ThemeType) {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.setTheme(theme)
        }
    }
}