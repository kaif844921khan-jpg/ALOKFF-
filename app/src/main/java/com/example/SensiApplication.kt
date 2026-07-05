package com.example

import android.app.Application
import com.example.data.AppDatabase
import com.example.data.UserSettingsRepository

class SensiApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { UserSettingsRepository(database.userSettingsDao()) }
}
