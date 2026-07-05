package com.example.data

import kotlinx.coroutines.flow.Flow

class UserSettingsRepository(private val userSettingsDao: UserSettingsDao) {
    val userSettings: Flow<UserSettings?> = userSettingsDao.getUserSettings()

    suspend fun saveSettings(settings: UserSettings) {
        userSettingsDao.saveUserSettings(settings)
    }
}
