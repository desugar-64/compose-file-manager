package com.serhiiyaremych.composefilemanager.ui.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serhiiyaremych.composefilemanager.ui.common.RoundedCornerButton

sealed class Tile {
    abstract val title: String
    abstract val icon: ImageVector
    abstract val tintColor: Color

    data class Images(
        override val title: String,
        override val icon: ImageVector,
        override val tintColor: Color
    ) : Tile()

    data class Videos(
        override val title: String,
        override val icon: ImageVector,
        override val tintColor: Color
    ) : Tile()

    data class Music(
        override val title: String,
        override val icon: ImageVector,
        override val tintColor: Color
    ) : Tile()

    data class Apps(
        override val title: String,
        override val icon: ImageVector,
        override val tintColor: Color
    ) : Tile()

    data class ZipFiles(
        override val title: String,
        override val icon: ImageVector,
        override val tintColor: Color
    ) : Tile()

    data class Documents(
        override val title: String,
        override val icon: ImageVector,
        override val tintColor: Color
    ) : Tile()

    data class Downloads(
        override val title: String,
        override val icon: ImageVector,
        override val tintColor: Color
    ) : Tile()

    data class Custom(
        override val title: String,
        override val icon: ImageVector,
        override val tintColor: Color,
        val resourceId: String
    ) : Tile()
}

@Composable
fun ResourceTile(
    modifier: Modifier = Modifier,
    tile: Tile,
    onClick: (Tile) -> Unit
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundedCornerButton(
            modifier = modifier,
            icon = tile.icon,
            tintColor = tile.tintColor,
            onClick = { onClick.invoke(tile) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = tile.title,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        )
    }
}