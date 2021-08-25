package com.serhiiyaremych.composefilemanager.ui.feature.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serhiiyaremych.composefilemanager.ext.darker
import com.serhiiyaremych.composefilemanager.ext.lighter
import com.serhiiyaremych.composefilemanager.ui.common.CircularProgressBar
import com.serhiiyaremych.composefilemanager.ui.theme.ComposeFileManagerTheme
import com.serhiiyaremych.composefilemanager.ui.theme.Shapes
import kotlin.math.roundToInt

@Stable
data class StorageCardState(
    val cardLogo: ImageVector,
    val cardTitle: String,
    val usedStorageBytes: Long,
    val totalStorageBytes: Long,
    val storageDataFormatter: (Long) -> String
) {
    val usedStorageFormatted: String
        get() = storageDataFormatter.invoke(usedStorageBytes)

    val totalStorageFormatted: String
        get() = storageDataFormatter.invoke(totalStorageBytes)

    companion object {
        fun init() = StorageCardState(
            cardLogo = Icons.Rounded.SdCard,
            cardTitle = "Unknown",
            usedStorageBytes = 0,
            totalStorageBytes = 0,
            storageDataFormatter = { "-" }
        )
    }
}


@Composable
fun StorageCard(
    surfaceColor: Color,
    contentAccentColor: Color,
    state: StorageCardState = remember(StorageCardState::init),
    onClick: () -> Unit
) {
    val ripple = rememberRipple(color = surfaceColor)

    CompositionLocalProvider(LocalIndication provides ripple) {
        Box(
            modifier = Modifier
                .size(250.dp, 130.dp)
                .shadow(elevation = 2.dp, Shapes.medium)
                .clickable(onClick = onClick)
        ) {
            val isLightTheme = MaterialTheme.colors.isLight
            val progress by animateFloatAsState(
                targetValue = state.usedStorageBytes / state.totalStorageBytes.toFloat(),
                animationSpec = tween(durationMillis = 3000)
            )
            val alpha = if (isLightTheme) 0.3f else 0.1f
            val color = if (surfaceColor == Color.White)
                surfaceColor.darker(0.2f)
            else
                surfaceColor.lighter(0.7f)

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
                    imageVector = state.cardLogo,
                    contentDescription = null,
                    tint = Color.White.compositeOver(contentAccentColor),
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
                            text = state.cardTitle,
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold,
                                color = contentAccentColor
                            )
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = "${state.usedStorageFormatted} / ${state.totalStorageFormatted}",
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
            color.darker(0.05f),
            color.darker(0.03f),
            color,
        )
    } else {
        listOf(
            color,
            color.lighter(0.3f),
            color.lighter(0.5f)
        )
    }
}

@Composable
private fun SpaceUsed(
    usedStorageBytes: Long,
    totalStorageBytes: Long,
    contentAccentColor: Color
) {
    Box(contentAlignment = Alignment.Center) {
        val progress = usedStorageBytes / totalStorageBytes.toFloat()
        val textProgress = (progress * 100).roundToInt().coerceIn(0, 100)
        CircularProgressBar(
            modifier = Modifier.size(48.dp, 48.dp),
            backgroundStrokeWidth = 2.dp,
            backgroundStrokeColor = Color.LightGray,
            progressStrokeWidth = 5.dp,
            progressStrokeColor = contentAccentColor,
            progress = progress
        )
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontSize = 14.sp)) {
                    append("$textProgress")
                }
                withStyle(SpanStyle(fontSize = 10.sp)) {
                    append("%")
                }
            },
            textAlign = TextAlign.Center
        )
    }
}

@Preview(name = "StorageCard")
@Preview(name = "StorageCardDark", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun StoragePreview() {
    ComposeFileManagerTheme {
        StorageCard(
            state = StorageCardState.init().copy(totalStorageBytes = 512, usedStorageBytes = 380),
            surfaceColor = MaterialTheme.colors.background,
            contentAccentColor = Color.White
        ) { }
    }
}