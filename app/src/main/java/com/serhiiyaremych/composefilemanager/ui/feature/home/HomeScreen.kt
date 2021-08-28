package com.serhiiyaremych.composefilemanager.ui.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.serhiiyaremych.composefilemanager.R
import com.serhiiyaremych.composefilemanager.ui.theme.ComposeFileManagerTheme
import com.serhiiyaremych.composefilemanager.ui.theme.Shapes
import kotlin.math.roundToInt

private enum class RecentFileSheetState { INITIAL, HALF_OPENED, EXPANDED }

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    contentPadding: Dp = 16.dp
) {
    var sheetTopOffset by remember { mutableStateOf(IntOffset.Zero) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = contentPadding)
    ) {
        SearchBar(
            modifier = Modifier.padding(horizontal = contentPadding),
            onClick = { /* TODO */ }
        )

        Spacer(modifier = Modifier.requiredHeight(32.dp))

        UserStorageCards(contentPadding = contentPadding)

        Spacer(modifier = Modifier.requiredHeight(32.dp))

        UserCommonDataTypeTiles(contentPadding) { layoutPosition ->
            sheetTopOffset = layoutPosition
        }
    }

    if (sheetTopOffset != IntOffset.Zero) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        val sheetPeekY = sheetTopOffset.y.toFloat()
        val sheetHalfOpenedY = sheetPeekY / 2
        val sheetExpandedY = 0.0f

        val swipeableState = rememberSwipeableState(initialValue = RecentFileSheetState.INITIAL)

        val recentSheetSwipeAnchors = mapOf(
            sheetPeekY to RecentFileSheetState.INITIAL,
            sheetHalfOpenedY to RecentFileSheetState.HALF_OPENED,
            sheetExpandedY to RecentFileSheetState.EXPANDED
        )

        val swipingOffset by swipeableState.offset
        val swipeProgress = 1.0f - (swipingOffset / sheetPeekY).coerceIn(0.0f, 1.0f)
        val sheetCornerRadius = lerp(16.dp, 0.dp, swipeProgress)
        Surface(
            shape = Shapes.medium.copy(CornerSize(sheetCornerRadius)),
            modifier = Modifier
                .requiredSize(screenWidth, screenHeight)
                .offset { IntOffset(x = 0, y = swipingOffset.roundToInt()) }
                .swipeable(
                    state = swipeableState,
                    anchors = recentSheetSwipeAnchors,
                    orientation = Orientation.Vertical
                ),
            elevation = lerp(0.dp, 16.dp, swipeProgress)
        ) {
            Column(modifier = Modifier.padding(contentPadding)) {
                Text(
                    text = "Recent files",
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onSurface
                    )
                )
            }
        }
    }
}

@Composable
private fun UserCommonDataTypeTiles(contentPadding: Dp, onLayoutBottomPlaced: (IntOffset) -> Unit) {
    // TODO: make own data source for resource tiles
    val images = stringResource(R.string.images)
    val videos = stringResource(R.string.videos)
    val music = stringResource(R.string.music)
    val apps = stringResource(R.string.apps)
    val zipFiles = stringResource(R.string.zip_files)
    val documents = stringResource(R.string.document)
    val downloads = stringResource(R.string.downloads)
    val add = stringResource(R.string.add)
    val tiles = remember {
        listOf(
            ResourceTile.Images(images, Icons.Rounded.Image, Color(0xFF673AB7)),
            ResourceTile.Videos(videos, Icons.Rounded.VideoLibrary, Color(0xFFF44336)),
            ResourceTile.Music(music, Icons.Rounded.LibraryMusic, Color(0xFFFF9800)),
            ResourceTile.Apps(apps, Icons.Rounded.Apps, Color(0xFF03A9F4)),
            ResourceTile.ZipFiles(zipFiles, Icons.Rounded.Archive, Color(0xFF838383)),
            ResourceTile.Documents(documents, Icons.Rounded.ListAlt, Color(0xFF1C70B3)),
            ResourceTile.Downloads(downloads, Icons.Rounded.Download, Color(0xFF009688)),
            ResourceTile.Add(add, Icons.Rounded.Add, Color(0xFF03A9F4)),
        ).chunked(4)
    }

    val currentLayoutPlaceListener by rememberUpdatedState(newValue = onLayoutBottomPlaced)

    val contentPaddingPx = with(LocalDensity.current) { contentPadding.roundToPx() }

    Column(
        modifier = Modifier
            .wrapContentHeight(align = Alignment.Top)
            .padding(horizontal = contentPadding)
            .onGloballyPositioned { layoutCoordinates: LayoutCoordinates ->
                val offset = layoutCoordinates.localToRoot(Offset.Zero)
                currentLayoutPlaceListener.invoke(
                    IntOffset(
                        x = 0,
                        y = offset.y.roundToInt() + layoutCoordinates.size.height + contentPaddingPx
                    )
                )
            }
    ) {
        tiles.forEachIndexed { index, row ->
            if (index > 0) {
                Spacer(modifier = Modifier.height(16.dp))
            }
            SpaceBetweenItem {
                row.forEach { tile ->
                    ResourceTile(
                        tile = tile,
                        showDashBorder = tile is ResourceTile.Add,
                        onClick = {})
                }
            }
        }

    }
}

@Composable
private inline fun SpaceBetweenItem(content: @Composable RowScope.() -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        content = content
    )
}


@Preview
@Composable
private fun HomePreview() {
    ComposeFileManagerTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(16.dp)) {
                HomeScreen()
            }
        }
    }
}