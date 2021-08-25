package com.serhiiyaremych.composefilemanager.ext

import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.lerp

fun Color.lighten(@FloatRange(from = 0.0, to = 1.0) fraction: Float): Color {
    val linear = convert(ColorSpaces.LinearSrgb)
    return lerp(linear, Color.White, fraction).convert(colorSpace)
}

fun Color.darken(@FloatRange(from = 0.0, to = 1.0) fraction: Float): Color {
    val linear = convert(ColorSpaces.LinearSrgb)
    return lerp(linear, Color.Black, fraction).convert(colorSpace)
}