package com.serhiiyaremych.composefilemanager.ui.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.serhiiyaremych.composefilemanager.R
import com.serhiiyaremych.composefilemanager.ui.theme.ComposeFileManagerTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    contentPadding: Dp = 16.dp
) {
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = contentPadding)
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