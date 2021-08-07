package com.serhiiyaremych.composefilemanager.ui.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection.Ltr
import androidx.compose.ui.unit.dp
import com.serhiiyaremych.composefilemanager.R
import com.serhiiyaremych.composefilemanager.ui.common.RoundedCornerButton
import com.serhiiyaremych.composefilemanager.ui.theme.ComposeFileManagerTheme

@OptIn(ExperimentalFoundationApi::class)
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

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = contentPadding.calculateLeftPadding(Ltr))
        ) {
            spaceBetweenItem {
                RoundedCornerButton(
                    modifier = Modifier,
                    icon = Icons.Rounded.Image,
                    tintColor = Color(0xFF673AB7)
                ) {
                    /* TODO onClick */
                }
                RoundedCornerButton(
                    modifier = Modifier,
                    icon = Icons.Rounded.VideoLibrary,
                    tintColor = Color(0xFFF44336)
                ) {
                    /* TODO onClick */
                }
                RoundedCornerButton(
                    modifier = Modifier,
                    icon = Icons.Rounded.LibraryMusic,
                    tintColor = Color(0xFFFF9800)
                ) {
                    /* TODO onClick */
                }
                RoundedCornerButton(
                    modifier = Modifier,
                    icon = Icons.Rounded.Apps,
                    tintColor = Color(0xFF03A9F4)
                ) {
                    /* TODO onClick */
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            spaceBetweenItem {
                RoundedCornerButton(
                    modifier = Modifier,
                    icon = Icons.Rounded.Archive,
                    tintColor = Color(0xFF838383)
                ) {
                    /* TODO onClick */
                }
                RoundedCornerButton(
                    modifier = Modifier,
                    icon = Icons.Rounded.ListAlt,
                    tintColor = Color(0xFF1C70B3)
                ) {
                    /* TODO onClick */
                }
                RoundedCornerButton(
                    modifier = Modifier,
                    icon = Icons.Rounded.Download,
                    tintColor = Color(0xFF009688)
                ) {
                    /* TODO onClick */
                }
                RoundedCornerButton(
                    modifier = Modifier,
                    icon = Icons.Rounded.Add,
                    tintColor = Color(0xFF03A9F4)
                ) {
                    /* TODO onClick */
                }
            }

        }

    }
}

private fun LazyListScope.spaceBetweenItem(content: @Composable RowScope.() -> Unit) {
    item {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
            content = content
        )
    }
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