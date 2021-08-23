package com.serhiiyaremych.composefilemanager.ui.feature.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SdCard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StorageCard(
    surfaceColor: Color,
    contentAccentColor: Color,
    state: StorageCardState = remember(StorageCardState::init),
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        backgroundColor = surfaceColor,
        modifier = Modifier.size(250.dp, 130.dp),
        elevation = 6.dp
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

        val progressLineColor = color
            .copy(alpha = alpha)

        var containerSize by remember { mutableStateOf(Size.Zero) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = generateCardGradientColors(surfaceColor)
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
                val width = containerSize.width * progress
                ClipShape(
                    offset = Offset(
                        x = width,
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
                        style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

    }
}

private fun generateCardGradientColors(color: Color): List<Color> {
    return if (color == Color.White) {
        listOf(
            color.darker(0.05f),
            color,
            color.darker(0.05f),
        )
    } else {
        listOf(
            color,
            color.lighter(0.3f),
            color
        )
    }
}

private fun Color.lighter(@FloatRange(from = 0.0, to = 1.0) fraction: Float): Color {
    return lerp(this, Color.White, fraction)
}

private fun Color.darker(@FloatRange(from = 0.0, to = 1.0) fraction: Float): Color {
    return lerp(this, Color.Black, fraction)
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