package com.serhiiyaremych.composefilemanager.ui.feature.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SdCard
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serhiiyaremych.composefilemanager.ext.darken
import com.serhiiyaremych.composefilemanager.ext.lighten
import com.serhiiyaremych.composefilemanager.ui.common.CircularProgressBar
import com.serhiiyaremych.composefilemanager.ui.theme.ComposeFileManagerTheme
import com.serhiiyaremych.composefilemanager.ui.theme.Shapes
import kotlin.math.roundToInt

@Composable
fun StorageCard(
    dataIcon: Painter,
    dataIconTintColor: Color = Color.Unspecified,
    surfaceColor: Color,
    contentAccentColor: Color,
    cardTitle: String,
    cardText: String,
    @FloatRange(from = 0.0, to = 1.0) usedSpacePercentage: Float,
    onClick: () -> Unit
) {
    val ripple = rememberRipple(color = surfaceColor.lighten(0.7f))

    CompositionLocalProvider(LocalIndication provides ripple) {
        Box(
            modifier = Modifier
                .size(250.dp, 130.dp)
                .shadow(elevation = 2.dp, Shapes.medium)
                .clickable(onClick = onClick)
        ) {
            val isLightTheme = MaterialTheme.colors.isLight
            val progress by animateFloatAsState(
                targetValue = usedSpacePercentage,
                animationSpec = tween(durationMillis = 1500)
            )
            val alpha = if (isLightTheme) 0.4f else 0.2f
            val color = if (surfaceColor == Color.White)
                surfaceColor.darken(0.2f)
            else
                surfaceColor.lighten(0.6f)

            val progressLineColor = color.copy(alpha = alpha)
            var containerSize by remember { mutableStateOf(Size.Zero) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            generateCardGradientColors(surfaceColor),
                            endY = containerSize.height
                        ),
                        shape = Shapes.medium
                    )
                    .drawBehind {
                        containerSize = size
                        drawLine(
                            color = progressLineColor,
                            start = Offset(0f, size.height / 2),
                            end = Offset(size.width * progress, size.height / 2),
                            strokeWidth = size.height
                        )
                    }
                    .padding(end = 10.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                BackgroundProgress(progress, surfaceColor, contentAccentColor, containerSize)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Icon(
                    painter = dataIcon,
                    contentDescription = null,
                    tint = dataIconTintColor,
                    modifier = Modifier
                        .background(
                            shape = MaterialTheme.shapes.small.copy(CornerSize(10.dp)),
                            color = contentAccentColor
                                .copy(alpha = 0.2f)
                                .compositeOver(surfaceColor)
                        )
                        .padding(5.dp)
                )

                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = cardTitle,
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold,
                                color = contentAccentColor
                            )
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = cardText,
                            style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Bold),
                            color = contentAccentColor
                        )
                    }
                }
            }

        }
    }

}

@Composable
private fun BackgroundProgress(
    progress: Float,
    surfaceColor: Color,
    contentAccentColor: Color,
    containerSize: Size
) {
    val textProgress = "${(progress * 100).roundToInt().coerceIn(0, 100)}%"
    val textColorBackground = surfaceColor
    val textProgressAlpha = if (contentAccentColor == Color.Black) 0.2f else 0.4f
    val textColorForeground = contentAccentColor
        .copy(alpha = textProgressAlpha)
        .compositeOver(surfaceColor)

    val textStyle = MaterialTheme.typography.h3.copy(
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.SansSerif,
    )
    val clipShapeForeground = remember(progress, containerSize) {
        val width = containerSize.width * (1.0f - progress).coerceIn(0.0f, 1.0f)
        ClipShape(
            offset = Offset(
                x = containerSize.width - width,
                y = 0f
            ),
            size = containerSize.copy(width = width)
        )
    }

    // Background text
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = textProgress,
        style = textStyle,
        color = textColorBackground,
        textAlign = TextAlign.End
    )
    // Foreround Text
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clip(clipShapeForeground),
        text = textProgress,
        style = textStyle,
        color = textColorForeground,
        textAlign = TextAlign.End
    )
}

private fun generateCardGradientColors(color: Color): List<Color> {
    return if (color == Color.White) {
        listOf(
            color.darken(0.03f),
            color.darken(0.01f),
            color,
        )
    } else {
        listOf(
            color,
            color.lighten(0.1f),
            color.lighten(0.3f)
        )
    }
}

@Preview(name = "StorageCard")
@Preview(name = "StorageCardDark", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun StoragePreview() {
    ComposeFileManagerTheme {
        StorageCard(
            surfaceColor = MaterialTheme.colors.background,
            contentAccentColor = MaterialTheme.colors.onSurface,
            dataIcon = rememberVectorPainter(image = Icons.Rounded.SdCard),
            cardTitle = "Internal storage",
            cardText = "1023 / 1024 bytes",
            usedSpacePercentage = .99f,
            onClick = { }
        )
    }
}