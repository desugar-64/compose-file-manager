package com.serhiiyaremych.composefilemanager.ui.common

import androidx.annotation.ColorRes
import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.serhiiyaremych.composefilemanager.ui.theme.Color1
import com.serhiiyaremych.composefilemanager.ui.theme.ComposeFileManagerTheme

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    backgroundStrokeWidth: Dp,
    backgroundStrokeColor: Color,
    progressStrokeWidth: Dp,
    progressStrokeColor: Color,
    @FloatRange(from = 0.0, to = 1.0) progress: Float
) {
    val (backgroundStroke, setBackgroundStroke) = remember {
        mutableStateOf(Stroke())
    }
    val (progressStroke, setProgressStroke) = remember {
        mutableStateOf(Stroke())
    }
    val (gradientBrush, setBrush) = remember {
        mutableStateOf(Brush.radialGradient(listOf(progressStrokeColor, progressStrokeColor)))
    }
    val contentPadding = max(backgroundStrokeWidth, progressStrokeWidth) / 2
    Canvas(modifier = modifier
        .drawWithCache {
            val gradient = Brush.sweepGradient(
                colors = listOf(Color.Transparent,progressStrokeColor, progressStrokeColor)
            )
            setBrush.invoke(gradient)
            setBackgroundStroke.invoke(Stroke(width = backgroundStrokeWidth.toPx()))
            setProgressStroke.invoke(
                Stroke(
                    width = progressStrokeWidth.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )
            onDrawWithContent(ContentDrawScope::drawContent)
        }
        .padding(contentPadding)
    ) {
        rotate(270f) {
            drawCircle(color = backgroundStrokeColor, style = backgroundStroke)
            drawArc(
                brush = gradientBrush,
                startAngle = 0f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = progressStroke
            )
        }
    }
}


@Preview
@Composable
private fun CircularProgressBarPreview() {
    ComposeFileManagerTheme {
        CircularProgressBar(
            modifier = Modifier.size(64.dp),
            backgroundStrokeWidth = 2.dp,
            backgroundStrokeColor = Color.LightGray,
            progressStrokeWidth = 6.dp,
            progressStrokeColor = Color.Red,
            progress = 0.65f,
        )
    }
}

