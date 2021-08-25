package com.serhiiyaremych.composefilemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.toOffset
import com.serhiiyaremych.composefilemanager.ext.darken
import com.serhiiyaremych.composefilemanager.ext.lighten
import com.serhiiyaremych.composefilemanager.ui.feature.home.HomeScreen
import com.serhiiyaremych.composefilemanager.ui.theme.Color1
import com.serhiiyaremych.composefilemanager.ui.theme.Color2
import com.serhiiyaremych.composefilemanager.ui.theme.ComposeFileManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFileManagerTheme {
                val surfaceColor = if (MaterialTheme.colors.isLight) {
                    Color1
                } else {
                    MaterialTheme.colors.surface
                }
                val gradient = listOf(
                    surfaceColor.lighten(0.01f),
                    surfaceColor,
                    surfaceColor.darken(0.01f),
                    surfaceColor.darken(0.02f),
                )
                var componentSize by remember {
                    mutableStateOf(IntSize.Zero)
                }
                Box(modifier = Modifier
                    .onGloballyPositioned {
                        componentSize = it.size
                    }
                    .background(
                        Brush.radialGradient(
                            gradient,
                            center = componentSize.center.toOffset()
                        )
                    )
                ) {
                    HomeScreen()
                }
            }
        }
    }
}
