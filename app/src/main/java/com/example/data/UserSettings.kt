package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey val id: Int = 1,
    val deviceModel: String = "Redmi Note 12",
    val currentDpi: String = "360",
    val selectedRam: String = "4GB - 8GB RAM",
    val selectedBrand: String = "Xiaomi/POCO",
    val selectedPlaystyle: String = "Balanced Control",
    val generalSensi: Int = 95,
    val redDotSensi: Int = 88,
    val scope2xSensi: Int = 84,
    val scope4xSensi: Int = 80,
    val sniperSensi: Int = 50,
    val freeLookSensi: Int = 65,
    val suggestedDpi: String = "420 DPI",
    val suggestedFireButton: String = "48%",
    val recommendedGraphics: String = "Standard / High FPS"
)
