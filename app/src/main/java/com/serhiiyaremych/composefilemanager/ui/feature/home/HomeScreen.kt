package com.serhiiyaremych.composefilemanager.ui.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection.Ltr
import androidx.compose.ui.unit.dp
import com.serhiiyaremych.composefilemanager.R
import com.serhiiyaremych.composefilemanager.ui.common.dashBorder
import com.serhiiyaremych.composefilemanager.ui.theme.ComposeFileManagerTheme
import com.serhiiyaremych.composefilemanager.ui.theme.Shapes

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(16.dp)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = contentPadding.calculateTopPadding())
    ) {
        SearchBar(
            modifier = Modifier.padding(
                horizontal = contentPadding.calculateStartPadding(Ltr)
            ),
            onClick = {})
        Spacer(modifier = Modifier.requiredHeight(32.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = contentPadding.calculateLeftPadding(Ltr))
        ) {
            items(3) { index ->
                val color =
                    if (index == 0) MaterialTheme.colors.secondary else MaterialTheme.colors.background
                val accentColor = if (index == 0) Color.White else MaterialTheme.colors.onBackground
                StorageCard(
                    surfaceColor = color,
                    contentAccentColor = accentColor,
                    state = StorageCardState(
                        cardTitle = stringResource(R.string.card_internal_storage),
                        usedStorageBytes = 0,
                        totalStorageBytes = 0,
                        storageDataFormatter = { "$it bytes" }
                    )
                ) { /* TODO onClick */ }

                Spacer(modifier = Modifier.width(16.dp))
            }
        }

        Spacer(modifier = Modifier.requiredHeight(32.dp))

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
                Tile.Images(images, Icons.Rounded.Image, Color(0xFF673AB7)),
                Tile.Videos(videos, Icons.Rounded.VideoLibrary, Color(0xFFF44336)),
                Tile.Music(music, Icons.Rounded.LibraryMusic, Color(0xFFFF9800)),
                Tile.Apps(apps, Icons.Rounded.Apps, Color(0xFF03A9F4)),
                Tile.ZipFiles(zipFiles, Icons.Rounded.Archive, Color(0xFF838383)),
                Tile.Documents(documents, Icons.Rounded.ListAlt, Color(0xFF1C70B3)),
                Tile.Downloads(downloads, Icons.Rounded.Download, Color(0xFF009688)),
                Tile.Custom(add, Icons.Rounded.Add, Color(0xFF03A9F4), ""),
            ).chunked(4)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = contentPadding.calculateLeftPadding(Ltr)
                )
        ) {

            tiles
                .forEachIndexed { index, row ->
                    if (index > 0) {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    SpaceBetweenItem {

                        row.forEach { tile ->
                            val tileBgStroke = if (tile is Tile.Custom) {
                                Modifier
                                    .dashBorder(
                                        intervals = { floatArrayOf(10.dp.toPx(), 5.dp.toPx()) },
                                        strokeLineWidth = 6f,
                                        strokeColor = Color.LightGray,
                                        cornerRadius = 16.dp
                                    )
                            } else Modifier

                            ResourceTile(
                                modifier = tileBgStroke,
                                tile = tile,
                                onClick = {})
                        }
                    }
                }

        }


        /*val swipeableState = rememberSwipeableState(initialValue = "A")
        val anchors = mapOf(
            160.0f to "A"
        )
        Box(
            modifier =  Modifier
                .offset { IntOffset(0, swipeableState.offset.value.roundToInt()) }
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colors.onBackground,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .fillMaxHeight()
                .swipeable(
                    swipeableState,
                    anchors = anchors,
                    orientation = Orientation.Vertical,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) })

        ) {

        }*/
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