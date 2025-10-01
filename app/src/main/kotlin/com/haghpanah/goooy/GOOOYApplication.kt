package com.haghpanah.goooy

import android.app.Application
import com.haghpanah.goooy.data.setting.repository.SettingRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GOOOYApplication : Application() {
    @Inject
    lateinit var settingRepository: SettingRepository

    override fun onCreate() {
        super.onCreate()

        settingRepository.initiateLocales()
    }
}