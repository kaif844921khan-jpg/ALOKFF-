package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.UserSettings
import com.example.data.UserSettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SensiViewModel(private val repository: UserSettingsRepository) : ViewModel() {

    private var isFirstLoad = true

    // Internal states
    private val _deviceModel = MutableStateFlow("Redmi Note 12")
    val deviceModel: StateFlow<String> = _deviceModel.asStateFlow()

    private val _currentDpiString = MutableStateFlow("360")
    val currentDpiString: StateFlow<String> = _currentDpiString.asStateFlow()

    private val _selectedDeviceRam = MutableStateFlow("4GB - 8GB RAM")
    val selectedDeviceRam: StateFlow<String> = _selectedDeviceRam.asStateFlow()

    private val _selectedBrand = MutableStateFlow("Xiaomi/POCO")
    val selectedBrand: StateFlow<String> = _selectedBrand.asStateFlow()

    private val _selectedPlaystyle = MutableStateFlow("Balanced Control")
    val selectedPlaystyle: StateFlow<String> = _selectedPlaystyle.asStateFlow()

    private val _generalSensi = MutableStateFlow(95)
    val generalSensi: StateFlow<Int> = _generalSensi.asStateFlow()

    private val _redDotSensi = MutableStateFlow(88)
    val redDotSensi: StateFlow<Int> = _redDotSensi.asStateFlow()

    private val _scope2xSensi = MutableStateFlow(84)
    val scope2xSensi: StateFlow<Int> = _scope2xSensi.asStateFlow()

    private val _scope4xSensi = MutableStateFlow(80)
    val scope4xSensi: StateFlow<Int> = _scope4xSensi.asStateFlow()

    private val _sniperSensi = MutableStateFlow(50)
    val sniperSensi: StateFlow<Int> = _sniperSensi.asStateFlow()

    private val _freeLookSensi = MutableStateFlow(65)
    val freeLookSensi: StateFlow<Int> = _freeLookSensi.asStateFlow()

    private val _suggestedDpi = MutableStateFlow("420 DPI")
    val suggestedDpi: StateFlow<String> = _suggestedDpi.asStateFlow()

    private val _suggestedFireButton = MutableStateFlow("48%")
    val suggestedFireButton: StateFlow<String> = _suggestedFireButton.asStateFlow()

    private val _recommendedGraphics = MutableStateFlow("Standard / High FPS")
    val recommendedGraphics: StateFlow<String> = _recommendedGraphics.asStateFlow()

    init {
        // Load settings from database once on startup
        viewModelScope.launch {
            try {
                val settings = repository.userSettings.first()
                if (settings != null) {
                    _deviceModel.value = settings.deviceModel
                    _currentDpiString.value = settings.currentDpi
                    _selectedDeviceRam.value = settings.selectedRam
                    _selectedBrand.value = settings.selectedBrand
                    _selectedPlaystyle.value = settings.selectedPlaystyle
                    _generalSensi.value = settings.generalSensi
                    _redDotSensi.value = settings.redDotSensi
                    _scope2xSensi.value = settings.scope2xSensi
                    _scope4xSensi.value = settings.scope4xSensi
                    _sniperSensi.value = settings.sniperSensi
                    _freeLookSensi.value = settings.freeLookSensi
                    _suggestedDpi.value = settings.suggestedDpi
                    _suggestedFireButton.value = settings.suggestedFireButton
                    _recommendedGraphics.value = settings.recommendedGraphics
                } else {
                    // First launch
                    generateSensi()
                }
            } catch (e: Exception) {
                // Ignore and fallback to defaults
            } finally {
                isFirstLoad = false
            }
        }
    }

    fun updateDeviceModel(value: String) {
        if (_deviceModel.value != value) {
            _deviceModel.value = value
            if (!isFirstLoad) {
                generateSensi()
            }
        }
    }

    fun updateCurrentDpiString(value: String) {
        if (_currentDpiString.value != value) {
            _currentDpiString.value = value
            if (!isFirstLoad) {
                generateSensi()
            }
        }
    }

    fun updateSelectedDeviceRam(value: String) {
        if (_selectedDeviceRam.value != value) {
            _selectedDeviceRam.value = value
            if (!isFirstLoad) {
                generateSensi()
            }
        }
    }

    fun updateSelectedBrand(value: String) {
        if (_selectedBrand.value != value) {
            _selectedBrand.value = value
            if (!isFirstLoad) {
                generateSensi()
            }
        }
    }

    fun updateSelectedPlaystyle(value: String) {
        if (_selectedPlaystyle.value != value) {
            _selectedPlaystyle.value = value
            if (!isFirstLoad) {
                generateSensi()
            }
        }
    }

    fun updateGeneralSensi(value: Int) {
        if (_generalSensi.value != value) {
            _generalSensi.value = value
            saveToDatabase()
        }
    }

    fun updateRedDotSensi(value: Int) {
        if (_redDotSensi.value != value) {
            _redDotSensi.value = value
            saveToDatabase()
        }
    }

    fun updateScope2xSensi(value: Int) {
        if (_scope2xSensi.value != value) {
            _scope2xSensi.value = value
            saveToDatabase()
        }
    }

    fun updateScope4xSensi(value: Int) {
        if (_scope4xSensi.value != value) {
            _scope4xSensi.value = value
            saveToDatabase()
        }
    }

    fun updateSniperSensi(value: Int) {
        if (_sniperSensi.value != value) {
            _sniperSensi.value = value
            saveToDatabase()
        }
    }

    fun updateFreeLookSensi(value: Int) {
        if (_freeLookSensi.value != value) {
            _freeLookSensi.value = value
            saveToDatabase()
        }
    }

    fun generateSensi() {
        val currentDpi = _currentDpiString.value.toIntOrNull() ?: 360
        val modelLower = _deviceModel.value.lowercase().trim()

        // 1. Brand detection based on model input
        var brand = _selectedBrand.value
        if (modelLower.contains("samsung") || modelLower.contains("galaxy")) {
            brand = "Samsung"
        } else if (modelLower.contains("xiaomi") || modelLower.contains("poco") || modelLower.contains("redmi") || modelLower.contains("mi ")) {
            brand = "Xiaomi/POCO"
        } else if (modelLower.contains("oneplus")) {
            brand = "OnePlus"
        } else if (modelLower.contains("realme")) {
            brand = "RealMe"
        } else if (modelLower.contains("vivo") || modelLower.contains("iqoo")) {
            brand = "Vivo"
        } else if (modelLower.contains("oppo")) {
            brand = "Oppo"
        } else if (modelLower.contains("iphone") || modelLower.contains("apple") || modelLower.contains("ipad")) {
            brand = "iPhone / Apple"
        }
        _selectedBrand.value = brand

        // 2. RAM estimation based on device keywords
        var ram = _selectedDeviceRam.value
        if (modelLower.contains("ultra") || modelLower.contains("pro max") || modelLower.contains("s23") || modelLower.contains("s24") || modelLower.contains("fold") || modelLower.contains("rog") || modelLower.contains("iphone 15") || modelLower.contains("iphone 16") || modelLower.contains("gt") || modelLower.contains("gaming")) {
            ram = "High-End Phone (> 8GB RAM)"
        } else if (modelLower.contains("lite") || modelLower.contains("play") || modelLower.contains("go") || modelLower.contains("prime") || modelLower.contains("a0") || modelLower.contains("a1") || modelLower.contains("y01") || modelLower.contains("y02") || modelLower.contains("y16")) {
            ram = "Low-End Phone (< 4GB RAM)"
        }
        _selectedDeviceRam.value = ram

        // Base calculation by RAM
        var gen = 95
        var red = 88
        var s2x = 84
        var s4x = 80
        var snip = 50
        var free = 65
        var dpi = "Default"
        var fireBtn = "50%"
        var graph = "Standard"

        when (ram) {
            "Low-End Phone (< 4GB RAM)" -> {
                gen = 100
                red = 95
                s2x = 92
                s4x = 88
                snip = 55
                free = 70
                dpi = "450 DPI (Enable Developer Options)"
                fireBtn = "45%"
                graph = "Smooth / High FPS"
            }
            "4GB - 8GB RAM" -> {
                gen = 96
                red = 89
                s2x = 84
                s4x = 82
                snip = 50
                free = 65
                dpi = "410 DPI"
                fireBtn = "50%"
                graph = "Standard / High FPS"
            }
            "High-End Phone (> 8GB RAM)" -> {
                gen = 92
                red = 82
                s2x = 78
                s4x = 75
                snip = 45
                free = 60
                dpi = "Default (No DPI needed, screen is high refresh)"
                fireBtn = "55%"
                graph = "Ultra / MAX FPS"
            }
        }

        // Adjusting general sensitivity based on current game DPI input
        if (currentDpi < 360) {
            gen = (gen + 5).coerceAtMost(100)
            red = (red + 4).coerceAtMost(100)
        } else if (currentDpi in 361..440) {
            gen = (gen + 2).coerceAtMost(100)
        } else if (currentDpi in 441..550) {
            gen = (gen - 3).coerceAtLeast(85)
            red = (red - 2).coerceAtLeast(80)
        } else if (currentDpi > 550) {
            gen = (gen - 8).coerceAtLeast(78)
            red = (red - 6).coerceAtLeast(75)
        }

        // Playstyle adjustment
        val playstyle = _selectedPlaystyle.value
        when (playstyle) {
            "Rush (One-Tap)" -> {
                gen = (gen + 4).coerceAtMost(100)
                red = (red + 6).coerceAtMost(100)
                fireBtn = "${fireBtn.replace("%", "").toInt() - 3}%"
            }
            "Sniper Mode" -> {
                gen = (gen - 5).coerceAtLeast(50)
                snip = (snip + 15).coerceAtMost(100)
                fireBtn = "${fireBtn.replace("%", "").toInt() + 4}%"
            }
            "Spray & Recoil Control" -> {
                s2x = (s2x + 6).coerceAtMost(100)
                s4x = (s4x + 6).coerceAtMost(100)
            }
            "Balanced Control" -> {
                // Keep defaults
            }
        }

        // Brand specific customization
        when (brand) {
            "Samsung" -> {
                gen = (gen + 1).coerceAtMost(100)
                red = (red + 2).coerceAtMost(100)
            }
            "Xiaomi/POCO" -> {
                gen = (gen - 1).coerceAtLeast(50)
                red = (red - 1).coerceAtLeast(50)
            }
            "OnePlus" -> {
                gen = (gen - 2).coerceAtLeast(50)
                red = (red - 2).coerceAtLeast(50)
            }
            "iPhone / Apple" -> {
                gen = 88
                red = 78
                s2x = 75
                s4x = 70
                snip = 40
                free = 55
                dpi = "Standard Apple Sensitivity"
                fireBtn = "58%"
                graph = "MAX Graphics / 120 FPS"
            }
        }

        _generalSensi.value = gen
        _redDotSensi.value = red
        _scope2xSensi.value = s2x
        _scope4xSensi.value = s4x
        _sniperSensi.value = snip
        _freeLookSensi.value = free

        // DPI suggestions based on current inputs
        _suggestedDpi.value = if (brand == "iPhone / Apple") {
            "Standard Apple (iOS has adaptive response)"
        } else {
            when {
                currentDpi < 360 -> "411 DPI (Safe increase for better drag)"
                currentDpi in 360..420 -> "450 DPI (Optimal speed boost)"
                currentDpi in 421..500 -> "510 DPI (Pro fast-paced setup)"
                else -> "Keep Current ($currentDpi DPI - already fast!)"
            }
        }

        _suggestedFireButton.value = fireBtn
        _recommendedGraphics.value = graph

        saveToDatabase()
    }

    private fun saveToDatabase() {
        viewModelScope.launch {
            try {
                val settings = UserSettings(
                    deviceModel = _deviceModel.value,
                    currentDpi = _currentDpiString.value,
                    selectedRam = _selectedDeviceRam.value,
                    selectedBrand = _selectedBrand.value,
                    selectedPlaystyle = _selectedPlaystyle.value,
                    generalSensi = _generalSensi.value,
                    redDotSensi = _redDotSensi.value,
                    scope2xSensi = _scope2xSensi.value,
                    scope4xSensi = _scope4xSensi.value,
                    sniperSensi = _sniperSensi.value,
                    freeLookSensi = _freeLookSensi.value,
                    suggestedDpi = _suggestedDpi.value,
                    suggestedFireButton = _suggestedFireButton.value,
                    recommendedGraphics = _recommendedGraphics.value
                )
                repository.saveSettings(settings)
            } catch (e: Exception) {
                // Ignore DB save issues
            }
        }
    }
}

class SensiViewModelFactory(private val repository: UserSettingsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SensiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SensiViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
