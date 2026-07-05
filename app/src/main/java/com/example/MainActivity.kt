package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MyApplicationTheme
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import kotlin.math.roundToInt
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.SensiViewModel
import com.example.ui.SensiViewModelFactory

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Scaffold(
          modifier = Modifier.fillMaxSize(),
          contentWindowInsets = WindowInsets.safeDrawing
        ) { innerPadding ->
          SensiScreen(modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}

// Device specs configuration data classes
data class SensiConfig(
  val general: Int,
  val redDot: Int,
  val scope2x: Int,
  val scope4x: Int,
  val sniper: Int,
  val freeLook: Int,
  val suggestedDpi: String,
  val fireButtonSize: String,
  val recommendedGraphics: String
)

@Composable
fun SensiScreen(modifier: Modifier = Modifier) {
  val context = LocalContext.current
  val haptic = LocalHapticFeedback.current
  val clipboard = LocalClipboardManager.current

  val application = context.applicationContext as SensiApplication
  val viewModel: SensiViewModel = viewModel(
    factory = SensiViewModelFactory(application.repository)
  )

  // Collect states from ViewModel
  val deviceModel by viewModel.deviceModel.collectAsStateWithLifecycle()
  val currentDpiString by viewModel.currentDpiString.collectAsStateWithLifecycle()
  val selectedDeviceRam by viewModel.selectedDeviceRam.collectAsStateWithLifecycle()
  val selectedBrand by viewModel.selectedBrand.collectAsStateWithLifecycle()
  val selectedPlaystyle by viewModel.selectedPlaystyle.collectAsStateWithLifecycle()

  val generalSensi by viewModel.generalSensi.collectAsStateWithLifecycle()
  val redDotSensi by viewModel.redDotSensi.collectAsStateWithLifecycle()
  val scope2xSensi by viewModel.scope2xSensi.collectAsStateWithLifecycle()
  val scope4xSensi by viewModel.scope4xSensi.collectAsStateWithLifecycle()
  val sniperSensi by viewModel.sniperSensi.collectAsStateWithLifecycle()
  val freeLookSensi by viewModel.freeLookSensi.collectAsStateWithLifecycle()

  val suggestedDpi by viewModel.suggestedDpi.collectAsStateWithLifecycle()
  val suggestedFireButton by viewModel.suggestedFireButton.collectAsStateWithLifecycle()
  val recommendedGraphics by viewModel.recommendedGraphics.collectAsStateWithLifecycle()

  fun generateSensi() {
    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    viewModel.generateSensi()
    Toast.makeText(context, "🎯 Best Sensi Settings Generated!", Toast.LENGTH_SHORT).show()
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(
        Brush.verticalGradient(
          colors = listOf(Color(0xFF0C0C12), Color(0xFF030305))
        )
      )
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(bottom = 32.dp, top = 16.dp)
  ) {
    // Top Hero Banner Card
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
          .clip(RoundedCornerShape(16.dp))
          .border(1.dp, Color(0xFFFF5722).copy(alpha = 0.3f), RoundedCornerShape(16.dp))
      ) {
        Image(
          painter = painterResource(id = R.drawable.img_hero_banner),
          contentDescription = "Esports Sensi Header Banner",
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )

        // Gradient overlay
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
              )
            )
        )

        // Text overlay
        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
          verticalArrangement = Arrangement.Bottom
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(8.dp)
                .background(Color(0xFFFF5722), CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "PRO SENSITIVITY ENGINE",
              color = Color(0xFFFF9800),
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 2.sp,
              fontFamily = FontFamily.Monospace
            )
          }
          Text(
            text = "SENSI MAX",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
          )
          Text(
            text = "Auto Headshot & Precision Drag Calculator",
            color = Color(0xFF9E9EAF),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }

    // SECTION 1: Device Configuration Panel & Custom Calculator Inputs
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161622)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFF2B2B3D))
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.SettingsSuggest,
              contentDescription = null,
              tint = Color(0xFFFF5722),
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "1. Device & DPI Calculator",
              color = Color.White,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Divider(color = Color(0xFF2B2B3D), thickness = 1.dp)

          // Custom Input 1: Device Model Name Input Field
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
              text = "Device Model",
              color = Color(0xFF9E9EAF),
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold
            )
            OutlinedTextField(
              value = deviceModel,
              onValueChange = {
                viewModel.updateDeviceModel(it)
              },
              placeholder = { Text("e.g. Redmi Note 12, Samsung S23", color = Color(0xFF5D5D70), fontSize = 13.sp) },
              singleLine = true,
              leadingIcon = {
                Icon(
                  imageVector = Icons.Default.PhoneAndroid,
                  contentDescription = null,
                  tint = Color(0xFFFF5722),
                  modifier = Modifier.size(20.dp)
                )
              },
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFFFF5722),
                unfocusedBorderColor = Color(0xFF2B2B3D),
                focusedContainerColor = Color(0xFF1F1F30),
                unfocusedContainerColor = Color(0xFF13131F)
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("device_model_input")
            )

            // Dynamic suggestions for device models
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              val modelSuggestions = listOf("Redmi Note 12", "Poco X5 Pro", "Samsung S23", "iPhone 15 Pro", "OnePlus 11")
              modelSuggestions.forEach { suggestion ->
                val isSelected = deviceModel.lowercase().trim() == suggestion.lowercase().trim()
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (isSelected) Color(0xFFFF5722).copy(alpha = 0.2f) else Color(0xFF222232))
                    .border(
                      1.dp,
                      if (isSelected) Color(0xFFFF5722) else Color.Transparent,
                      RoundedCornerShape(6.dp)
                    )
                    .clickable {
                      haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                      viewModel.updateDeviceModel(suggestion)
                    }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                  Text(
                    text = suggestion,
                    color = if (isSelected) Color(0xFFFF9800) else Color(0xFF9E9EAF),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }
          }

          // Custom Input 2: Current Game DPI Input Field
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Current Game DPI (System DPI)",
                color = Color(0xFF9E9EAF),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
              )
              // Info Badge
              Box(
                modifier = Modifier
                  .background(Color(0xFF222232), RoundedCornerShape(4.dp))
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                val numericDpi = currentDpiString.toIntOrNull() ?: 360
                val speedLabel = when {
                  numericDpi < 360 -> "Low Speed"
                  numericDpi in 360..420 -> "Standard Speed"
                  numericDpi in 421..500 -> "Fast Sensi"
                  else -> "Ultra-Fast Sensi"
                }
                Text(
                  text = speedLabel,
                  color = Color(0xFFFF9800),
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }

            OutlinedTextField(
              value = currentDpiString,
              onValueChange = { input ->
                if (input.all { it.isDigit() } && input.length <= 4) {
                  viewModel.updateCurrentDpiString(input)
                }
              },
              placeholder = { Text("e.g. 360, 411, 480", color = Color(0xFF5D5D70), fontSize = 13.sp) },
              singleLine = true,
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
              leadingIcon = {
                Icon(
                  imageVector = Icons.Default.Speed,
                  contentDescription = null,
                  tint = Color(0xFFFF9800),
                  modifier = Modifier.size(20.dp)
                )
              },
              colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFFFF5722),
                unfocusedBorderColor = Color(0xFF2B2B3D),
                focusedContainerColor = Color(0xFF1F1F30),
                unfocusedContainerColor = Color(0xFF13131F)
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("current_dpi_input")
            )

            // Dynamic suggestions for DPI
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              val dpiSuggestions = listOf("360", "411", "480", "600", "800")
              dpiSuggestions.forEach { suggestion ->
                val isSelected = currentDpiString.trim() == suggestion.trim()
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(if (isSelected) Color(0xFFFF5722).copy(alpha = 0.2f) else Color(0xFF222232))
                    .border(
                      1.dp,
                      if (isSelected) Color(0xFFFF5722) else Color.Transparent,
                      RoundedCornerShape(6.dp)
                    )
                    .clickable {
                      haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                      viewModel.updateCurrentDpiString(suggestion)
                    }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                  Text(
                    text = "$suggestion DPI",
                    color = if (isSelected) Color(0xFFFF9800) else Color(0xFF9E9EAF),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
              }
            }
          }

          // Advanced hardware settings details
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp)
          ) {
            Icon(
              imageVector = Icons.Default.FilterList,
              contentDescription = null,
              tint = Color(0xFFFF9800),
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Advanced Hardware & Custom HUD Refinements",
              color = Color(0xFF9E9EAF),
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }

          // 1. RAM Selector
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
              text = "Device Performance (RAM Mode)",
              color = Color(0xFF9E9EAF),
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold
            )
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              val ramOptions = listOf("Low-End Phone (< 4GB RAM)", "4GB - 8GB RAM", "High-End Phone (> 8GB RAM)")
              ramOptions.forEach { option ->
                val selected = selectedDeviceRam == option
                Box(
                  modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (selected) Color(0xFFFF5722) else Color(0xFF222232))
                    .clickable {
                      haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                      viewModel.updateSelectedDeviceRam(option)
                    }
                    .padding(vertical = 10.dp, horizontal = 4.dp),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = option.split(" ").first(),
                    color = if (selected) Color.White else Color(0xFF9E9EAF),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                  )
                }
              }
            }
          }

          // 2. Brand Selector
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
              text = "Detected Brand Engine",
              color = Color(0xFF9E9EAF),
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold
            )
            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
              Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val brandOptions = listOf("Samsung", "Xiaomi/POCO", "OnePlus", "RealMe", "Vivo", "Oppo", "iPhone / Apple")
                brandOptions.forEach { brand ->
                  val selected = selectedBrand == brand
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(20.dp))
                      .background(if (selected) Color(0xFFFF5722).copy(alpha = 0.2f) else Color(0xFF222232))
                      .border(
                        1.dp,
                        if (selected) Color(0xFFFF5722) else Color.Transparent,
                        RoundedCornerShape(20.dp)
                      )
                      .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        viewModel.updateSelectedBrand(brand)
                      }
                      .padding(horizontal = 14.dp, vertical = 8.dp)
                  ) {
                    Text(
                      text = brand,
                      color = if (selected) Color(0xFFFF9800) else Color(0xFF9E9EAF),
                      fontSize = 12.sp,
                      fontWeight = FontWeight.Bold
                    )
                  }
                }
              }
            }
          }

          // 3. Playstyle Selector
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
              text = "Target Playstyle",
              color = Color(0xFF9E9EAF),
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold
            )
            Box(modifier = Modifier.horizontalScroll(rememberScrollState())) {
              Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val styleOptions = listOf("Balanced Control", "Rush (One-Tap)", "Sniper Mode", "Spray & Recoil Control")
                styleOptions.forEach { style ->
                  val selected = selectedPlaystyle == style
                  Box(
                    modifier = Modifier
                      .clip(RoundedCornerShape(20.dp))
                      .background(if (selected) Color(0xFFFF5722).copy(alpha = 0.2f) else Color(0xFF222232))
                      .border(
                        1.dp,
                        if (selected) Color(0xFFFF5722) else Color.Transparent,
                        RoundedCornerShape(20.dp)
                      )
                      .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        viewModel.updateSelectedPlaystyle(style)
                      }
                      .padding(horizontal = 14.dp, vertical = 8.dp)
                  ) {
                    Text(
                      text = style,
                      color = if (selected) Color(0xFFFF9800) else Color(0xFF9E9EAF),
                      fontSize = 12.sp,
                      fontWeight = FontWeight.Bold
                    )
                  }
                }
              }
            }
          }

          Button(
            onClick = { generateSensi() },
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("generate_sensi_button"),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
            shape = RoundedCornerShape(10.dp)
          ) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = null,
              tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "CALCULATE ADVANCED SENSI",
              fontWeight = FontWeight.Black,
              fontSize = 13.sp,
              letterSpacing = 0.5.sp
            )
          }
        }
      }
    }

    // SECTION 2: Generated Sliders (FF Layout style)
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161622)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFF2B2B3D))
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = null,
                tint = Color(0xFFFF9800),
                modifier = Modifier.size(22.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "2. Generated Sensitivity",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
              )
            }

            IconButton(
              onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                val formatText = """
                  🔥 SENSI MAX PRO - BEST FREE FIRE MAX SENSITIVITY 🔥
                  📱 RAM: $selectedDeviceRam | Brand: $selectedBrand | Playstyle: $selectedPlaystyle
                  -----------------------------------
                  🎯 General: $generalSensi
                  🔴 Red Dot: $redDotSensi
                  🟢 2x Scope: $scope2xSensi
                  🔵 4x Scope: $scope4xSensi
                  🎯 Sniper Scope: $sniperSensi
                  👀 Free Look: $freeLookSensi
                  -----------------------------------
                  ⚙️ Suggested DPI: $suggestedDpi
                  🔘 Fire Button Size: $suggestedFireButton
                  🎮 Graphics: $recommendedGraphics
                  👉 Generated via Sensi Max App
                """.trimIndent()
                clipboard.setText(AnnotatedString(formatText))
                Toast.makeText(context, "📋 Copied Settings to Clipboard!", Toast.LENGTH_SHORT).show()
              },
              modifier = Modifier
                .background(Color(0xFFFF5722).copy(alpha = 0.15f), CircleShape)
                .size(36.dp)
                .testTag("copy_settings_button")
            ) {
              Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "Copy settings",
                tint = Color(0xFFFF5722),
                modifier = Modifier.size(18.dp)
              )
            }
          }

          Divider(color = Color(0xFF2B2B3D), thickness = 1.dp)

          // 6 Sensi Sliders
          val sliders = listOf(
            SensiSliderItem("General (Screen rotation, drag)", generalSensi, Icons.Default.Visibility, Color(0xFFFF5722)) { viewModel.updateGeneralSensi(it) },
            SensiSliderItem("Red Dot (Aim assist target)", redDotSensi, Icons.Default.FiberManualRecord, Color(0xFFE53935)) { viewModel.updateRedDotSensi(it) },
            SensiSliderItem("2x Scope", scope2xSensi, Icons.Default.FilterCenterFocus, Color(0xFFFF9800)) { viewModel.updateScope2xSensi(it) },
            SensiSliderItem("4x Scope", scope4xSensi, Icons.Default.CenterFocusStrong, Color(0xFF2196F3)) { viewModel.updateScope4xSensi(it) },
            SensiSliderItem("Sniper Scope", sniperSensi, Icons.Default.TrackChanges, Color(0xFF9C27B0)) { viewModel.updateSniperSensi(it) },
            SensiSliderItem("Free Look (Eye camera icon)", freeLookSensi, Icons.Default.Explore, Color(0xFF4CAF50)) { viewModel.updateFreeLookSensi(it) }
          )

          sliders.forEach { item ->
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = item.icon,
                    contentDescription = null,
                    tint = item.color,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = item.name,
                    color = Color(0xFFE0E0E0),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                  )
                }
                Text(
                  text = "${item.value}",
                  color = item.color,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Black,
                  fontFamily = FontFamily.Monospace
                )
              }

              // Styled slider like Free Fire in-game
              Slider(
                value = item.value.toFloat(),
                onValueChange = {
                  haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                  item.onValueChange(it.roundToInt())
                },
                valueRange = 0f..100f,
                colors = SliderDefaults.colors(
                  activeTrackColor = Color(0xFFFF5722),
                  inactiveTrackColor = Color(0xFF252535),
                  thumbColor = Color.White,
                  activeTickColor = Color.Transparent,
                  inactiveTickColor = Color.Transparent
                ),
                modifier = Modifier
                  .height(28.dp)
                  .testTag("slider_${item.name.split(" ").first().lowercase()}")
              )
            }
          }
        }
      }
    }

    // SECTION 3: DPI, Fire Button & Graphics Recommended
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161622)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFF2B2B3D))
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.PhonelinkSetup,
              contentDescription = null,
              tint = Color(0xFFFF9800),
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "3. Recommended System Settings",
              color = Color.White,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Divider(color = Color(0xFF2B2B3D), thickness = 1.dp)

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            // DPI Recommendation
            RecommendationItem(
              title = "SUGGESTED DPI",
              value = suggestedDpi,
              icon = Icons.Default.AspectRatio,
              tint = Color(0xFFFF5722),
              modifier = Modifier.weight(1f)
            )

            // Fire Button
            RecommendationItem(
              title = "FIRE BUTTON SIZE",
              value = suggestedFireButton,
              icon = Icons.Default.RadioButtonChecked,
              tint = Color(0xFFFFD700),
              modifier = Modifier.weight(1f)
            )
          }

          RecommendationItem(
            title = "IN-GAME GRAPHICS PRESET",
            value = recommendedGraphics,
            icon = Icons.Default.GraphicEq,
            tint = Color(0xFF4CAF50),
            modifier = Modifier.fillMaxWidth()
          )
        }
      }
    }

    // SECTION 4: Interactive Headshot Drag Trainer Sandbox
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF13131F)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFFF5722).copy(alpha = 0.4f))
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.SportsEsports,
              contentDescription = null,
              tint = Color(0xFFFF5722),
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "4. Drag Headshot Simulator",
              color = Color.White,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
              modifier = Modifier
                .background(Color(0xFFFF5722).copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(
                text = "PRACTICE SWIPE",
                color = Color(0xFFFF9800),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Text(
            text = "Test your actual drag velocity! Drag the circular fire button straight upwards with your thumb. Release at the right height and speed to hit an auto headshot!",
            color = Color(0xFF9E9EAF),
            fontSize = 11.sp,
            textAlign = TextAlign.Center
          )

          Divider(color = Color(0xFF252535), thickness = 1.dp)

          // Drag sandbox area
          var scoreHeadshots by remember { mutableStateOf(0) }
          var totalAttempts by remember { mutableStateOf(0) }

          var trainerFeedback by remember { mutableStateOf("Ready. Put finger on Fire Button & Drag Up!") }
          var feedbackColor by remember { mutableStateOf(Color(0xFF9E9EAF)) }
          var damageNumber by remember { mutableStateOf<Int?>(null) }
          var showDamageEffect by remember { mutableStateOf(false) }

          // State for tracking drag
          var accumulatedDragY by remember { mutableStateOf(0f) }
          var isDragging by remember { mutableStateOf(false) }
          var lastDragStartTime by remember { mutableStateOf(0L) }

          // Score Reset Button
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
              Text(
                text = "Headshots: $scoreHeadshots",
                color = Color(0xFFFF3D00),
                fontSize = 12.sp,
                fontWeight = FontWeight.Black
              )
              Text(
                text = "Total: $totalAttempts",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
              if (totalAttempts > 0) {
                val rate = (scoreHeadshots.toFloat() / totalAttempts * 100).roundToInt()
                Text(
                  text = "Accuracy: $rate%",
                  color = Color(0xFFFFD700),
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
            Text(
              text = "Reset Stats",
              color = Color(0xFFE53935),
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.clickable {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                scoreHeadshots = 0
                totalAttempts = 0
                trainerFeedback = "Stats Reset. Start Training!"
                feedbackColor = Color(0xFF9E9EAF)
                damageNumber = null
              }
            )
          }

          // Visual Simulation Board
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(180.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(Color(0xFF09090F))
              .border(1.dp, Color(0xFF252535), RoundedCornerShape(12.dp))
          ) {
            // Horizontal gridline & center aim marker
            Canvas(modifier = Modifier.fillMaxSize()) {
              drawLine(
                color = Color(0xFF1E1E2E),
                start = Offset(0f, size.height / 2),
                end = Offset(size.width, size.height / 2),
                strokeWidth = 1f
              )
              // Center target circle
              drawCircle(
                color = Color(0xFFFF3D00).copy(alpha = 0.08f),
                radius = 60.dp.toPx(),
                center = Offset(size.width / 2, size.height * 0.3f)
              )
            }

            // ENEMY TARGET MARKER
            Column(
              modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(
                    if (feedbackColor == Color(0xFFFF1744)) Color(0xFFFF1744).copy(alpha = 0.25f)
                    else Color(0xFF21212F)
                  )
                  .border(
                    2.dp,
                    if (feedbackColor == Color(0xFFFF1744)) Color(0xFFFF1744) else Color(0xFF5D5D70),
                    CircleShape
                  ),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.FilterCenterFocus,
                  contentDescription = null,
                  tint = if (feedbackColor == Color(0xFFFF1744)) Color(0xFFFF1744) else Color(0xFF9E9EAF),
                  modifier = Modifier.size(20.dp)
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "AIM POINT",
                color = Color(0xFF9E9EAF),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
              )
            }

            // DAMAGE FLOATING HIGHLIGHT
            androidx.compose.animation.AnimatedVisibility(
              visible = showDamageEffect && damageNumber != null,
              enter = fadeIn() + scaleIn(),
              exit = fadeOut() + scaleOut(),
              modifier = Modifier.align(Alignment.Center)
            ) {
              val isHeadshot = damageNumber == 245
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (isHeadshot) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                      imageVector = Icons.Default.Dangerous,
                      contentDescription = null,
                      tint = Color(0xFFFF1744),
                      modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                      text = "HEADSHOT",
                      color = Color(0xFFFF1744),
                      fontSize = 14.sp,
                      fontWeight = FontWeight.Black,
                      letterSpacing = 1.sp
                    )
                  }
                }
                Text(
                  text = "$damageNumber",
                  color = if (isHeadshot) Color(0xFFFF1744) else Color(0xFFFFEB3B),
                  fontSize = if (isHeadshot) 38.sp else 24.sp,
                  fontWeight = FontWeight.Black,
                  fontFamily = FontFamily.Monospace,
                  modifier = Modifier.drawBehind {
                    // outline glow text
                  }
                )
              }
            }

            // Drag State Live Indicators
            Column(
              modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
            ) {
              Text(
                text = "Drag Y: ${accumulatedDragY.roundToInt()} dp",
                color = Color(0xFF9E9EAF),
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
              )
              Text(
                text = "Status: ${if (isDragging) "Dragging..." else "Idle"}",
                color = if (isDragging) Color(0xFFFF9800) else Color(0xFF5D5D70),
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
              )
            }

            // FIRE BUTTON INTERACTIVE SWIPER
            // Displays floating circle that user drags up
            Box(
              modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
            ) {
              // Fire button track
              Box(
                modifier = Modifier
                  .size(68.dp)
                  .clip(CircleShape)
                  .background(Color(0xFF1E1E2E).copy(alpha = 0.5f))
                  .border(1.dp, Color(0xFF32324A), CircleShape)
              )

              // Interactive draggable fire button inside track
              val buttonOffsetLimit = 100.dp
              val animatedOffsetY by animateFloatAsState(
                targetValue = if (isDragging) (-accumulatedDragY).coerceIn(-120f, 0f) else 0f,
                animationSpec = if (isDragging) spring(dampingRatio = Spring.DampingRatioNoBouncy) else spring(stiffness = Spring.StiffnessMedium)
              )

              Box(
                modifier = Modifier
                  .offset { IntOffset(0, animatedOffsetY.roundToInt()) }
                  .size(68.dp)
                  .clip(CircleShape)
                  .background(
                    Brush.radialGradient(
                      colors = listOf(Color(0xFFFF7043), Color(0xFFD84315))
                    )
                  )
                  .border(2.dp, Color(0xFFFFE082), CircleShape)
                  .draggable(
                    state = rememberDraggableState { deltaY ->
                      // accumulate drag. Drag up is negative deltaY, let's reverse to make upward positive
                      accumulatedDragY = (accumulatedDragY - deltaY / 2.5f).coerceIn(0f, 150f)
                    },
                    orientation = Orientation.Vertical,
                    onDragStarted = {
                      haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                      isDragging = true
                      accumulatedDragY = 0f
                      lastDragStartTime = System.currentTimeMillis()
                      showDamageEffect = false
                    },
                    onDragStopped = { velocity ->
                      isDragging = false
                      val duration = System.currentTimeMillis() - lastDragStartTime
                      totalAttempts++

                      // Drag Headshot Rules tied to Sensi
                      // High general sensitivity makes sweet-spot drag smaller
                      val sensiFactor = generalSensi.toFloat() / 100f
                      val idealDragMin = 65f / sensiFactor
                      val idealDragMax = 110f / sensiFactor

                      val dragAmt = accumulatedDragY
                      val dragSpeed = if (duration > 0) (dragAmt / (duration.toFloat() / 1000f)) else 0f

                      if (dragAmt < 30) {
                        // Tapped, not really dragged
                        trainerFeedback = "Tap Detected! Drag UPWARDS to hit headshots!"
                        feedbackColor = Color(0xFFE53935)
                        damageNumber = 0
                        showDamageEffect = true
                      } else if (dragAmt < idealDragMin) {
                        // Dragged too little
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        trainerFeedback = "Too Low! Pull the button higher! (Yellow Body Damage)"
                        feedbackColor = Color(0xFFFFEB3B)
                        damageNumber = 37
                        showDamageEffect = true
                      } else if (dragAmt > idealDragMax) {
                        // Dragged too much (recoil over head)
                        trainerFeedback = "Too Strong! Bullet flew over the head! (Missed)"
                        feedbackColor = Color(0xFF9E9EAF)
                        damageNumber = 0
                        showDamageEffect = true
                      } else {
                        // Sweet spot range! Let's check speed
                        // Ideal speed between 600 dp/s and 1800 dp/s
                        if (dragSpeed < 450) {
                          haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                          trainerFeedback = "Too Slow! Drag up faster to lock headshot!"
                          feedbackColor = Color(0xFFFFEB3B)
                          damageNumber = 37
                          showDamageEffect = true
                        } else if (dragSpeed > 2500) {
                          trainerFeedback = "Recoil! Dragged too fast, target lost! (Missed)"
                          feedbackColor = Color(0xFF9E9EAF)
                          damageNumber = 0
                          showDamageEffect = true
                        } else {
                          // PERFECT DRAG HEADSHOT!
                          haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                          scoreHeadshots++
                          trainerFeedback = "BOOM! PERFECT DRAG HEADSHOT! 🎯"
                          feedbackColor = Color(0xFFFF1744)
                          damageNumber = 245
                          showDamageEffect = true
                        }
                      }
                    }
                  )
                  .testTag("fire_button_touch_area"),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.RadioButtonChecked,
                  contentDescription = "FF Fire Button",
                  tint = Color.White.copy(alpha = 0.85f),
                  modifier = Modifier.size(36.dp)
                )
              }
            }
          }

          // Dynamic Feedback Panel
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(feedbackColor.copy(alpha = 0.12f))
              .border(1.dp, feedbackColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
              .padding(10.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = trainerFeedback,
              color = if (feedbackColor == Color(0xFF9E9EAF)) Color.White else feedbackColor,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              textAlign = TextAlign.Center
            )
          }
        }
      }
    }

    // SECTION 5: Step-by-Step Settings Guide
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161622)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFF2B2B3D))
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.HelpOutline,
              contentDescription = null,
              tint = Color(0xFFFF9800),
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "5. How to Setup & Drag Info",
              color = Color.White,
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Divider(color = Color(0xFF2B2B3D), thickness = 1.dp)

          // Step 1
          GuideStepItem(
            stepNumber = "1",
            title = "Apply sensitivity in Free Fire",
            description = "Open Free Fire Max -> Settings -> Sensitivity. Change the 6 sliders to match the values shown above."
          )

          // Step 2
          GuideStepItem(
            stepNumber = "2",
            title = "Set your custom DPI (Optional)",
            description = "Go to Phone Settings -> About -> Tap 'Build Number' 7 times. Open 'Developer Options' -> Find 'Smallest Width' (DPI) -> Input suggested DPI."
          )

          // Step 3
          GuideStepItem(
            stepNumber = "3",
            title = "Position the Fire Button",
            description = "In Custom HUD, set size to suggested % ($suggestedFireButton) and position it slightly lower on the right. This gives more screen space to drag up!"
          )

          // Step 4
          GuideStepItem(
            stepNumber = "4",
            title = "Perform Rotational Drag",
            description = "If the enemy is rushing or moving sideways, drag the fire button in a semi-circle curved path (U-shape) towards their head for a perfect headshot."
          )
        }
      }
    }
  }
}

// Sliders mapping helper structure
data class SensiSliderItem(
  val name: String,
  val value: Int,
  val icon: ImageVector,
  val color: Color,
  val onValueChange: (Int) -> Unit
)

// UI Components
@Composable
fun RecommendationItem(
  title: String,
  value: String,
  icon: ImageVector,
  tint: Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    colors = CardDefaults.cardColors(containerColor = Color(0xFF222232)),
    shape = RoundedCornerShape(10.dp),
    border = BorderStroke(1.dp, Color(0xFF2D2D3F))
  ) {
    Column(
      modifier = Modifier.padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = tint,
          modifier = Modifier.size(16.dp)
        )
        Text(
          text = title,
          color = Color(0xFF9E9EAF),
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        )
      }
      Text(
        text = value,
        color = Color.White,
        fontSize = 13.sp,
        fontWeight = FontWeight.Black
      )
    }
  }
}

@Composable
fun GuideStepItem(
  stepNumber: String,
  title: String,
  description: String
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(10.dp),
    verticalAlignment = Alignment.Top
  ) {
    Box(
      modifier = Modifier
        .size(24.dp)
        .background(Color(0xFFFF5722).copy(alpha = 0.15f), CircleShape)
        .border(1.dp, Color(0xFFFF5722), CircleShape),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = stepNumber,
        color = Color(0xFFFF9800),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold
      )
    }

    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
      Text(
        text = title,
        color = Color.White,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = description,
        color = Color(0xFF9E9EAF),
        fontSize = 11.sp,
        lineHeight = 15.sp
      )
    }
  }
}
