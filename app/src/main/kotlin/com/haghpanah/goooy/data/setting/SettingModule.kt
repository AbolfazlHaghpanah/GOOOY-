package com.haghpanah.goooy.data.setting

import com.haghpanah.goooy.data.setting.repository.SettingRepository
import com.haghpanah.goooy.data.setting.repository.SettingRepositoryImpl
import com.haghpanah.goooy.data.setting.storage.SettingStorage
import com.haghpanah.goooy.data.setting.storage.SettingStorageImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingModule {

    @Binds
    @Singleton
    abstract fun bindsSettingStorage(
        settingStorageImpl: SettingStorageImpl,
    ): SettingStorage

    @Binds
    @Singleton
    abstract fun bindsSettingRepository(
        settingRepositoryImpl: SettingRepositoryImpl,
    ): SettingRepository
}