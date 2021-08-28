package com.serhiiyaremych.composefilemanager.ext

import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.toArgb

fun Color.lighten(@FloatRange(from = 0.0, to = 1.0) fraction: Float): Color {
    val linear = convert(ColorSpaces.LinearSrgb)
    return lerp(linear, Color.White, fraction).convert(colorSpace)
}

fun Color.darken(@FloatRange(from = 0.0, to = 1.0) fraction: Float): Color {
    val linear = convert(ColorSpaces.LinearSrgb)
    return lerp(linear, Color.Black, fraction).convert(colorSpace)
}

fun Color.saturate(scale: Float): Color {
    val linear = convert(ColorSpaces.LinearSrgb)
    val hsv = FloatArray(3)
    android.graphics.Color.colorToHSV(linear.toArgb(), hsv)
    val (_, saturation, _) = hsv
    hsv[1] = (saturation * scale).coerceIn(0.0f, 1.0f)
    return Color(android.graphics.Color.HSVToColor(hsv)).convert(ColorSpaces.Srgb)
}