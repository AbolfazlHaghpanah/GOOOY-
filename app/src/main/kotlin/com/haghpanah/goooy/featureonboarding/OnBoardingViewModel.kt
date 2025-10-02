package com.haghpanah.goooy.featureonboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haghpanah.goooy.model.enums.AppLanguage
import com.haghpanah.goooy.model.enums.ThemeStyle
import com.haghpanah.goooy.data.setting.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val settingRepository: SettingRepository,
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

    private val _currentLanguage = MutableStateFlow(AppLanguage.getDefault())
    val currentLanguage = _currentLanguage.asStateFlow()

    init {
        getCurrentLanguage()
    }

    fun getCurrentLanguage() {
        viewModelScope.launch {
            _currentLanguage.emit(
                settingRepository.getCurrentLanguage()
                    ?: AppLanguage.getDefault()
            )
        }
    }

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

    fun setTheme(theme: ThemeStyle) {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.setTheme(theme)
        }
    }
}