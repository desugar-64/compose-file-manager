package com.serhiiyaremych.composefilemanager.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serhiiyaremych.composefilemanager.ui.theme.ComposeFileManagerTheme
import com.serhiiyaremych.composefilemanager.ui.theme.Shapes

@Composable
fun RoundedCornerButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    tintColor: Color,
    onClick: () -> Unit
) {
    val tintAlpha = if (MaterialTheme.colors.isLight) 1.0f else 0.6f
    CompositionLocalProvider(
        (LocalContentColor provides tintColor),
        (LocalContentAlpha provides tintAlpha)
    ) {
        IconButton(
            onClick = onClick,
            modifier = modifier
                .background(
                    color = MaterialTheme.colors.background,
                    shape = Shapes.medium
                )
        ) {
            Icon(modifier = Modifier.size(28.dp), imageVector = icon, contentDescription = null)
        }
    }
}

@Preview(name = "RoundedCornerButton")
@Preview(
    name = "RoundedCornerButtonDark",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun RoundedCornerButtonPreview() {
    ComposeFileManagerTheme {
        RoundedCornerButton(modifier = Modifier.size(64.dp), icon = Icons.Rounded.Image, tintColor = Color.Magenta) { }
    }
}