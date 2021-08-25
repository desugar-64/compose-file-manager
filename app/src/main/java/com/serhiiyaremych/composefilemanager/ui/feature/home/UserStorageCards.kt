package com.serhiiyaremych.composefilemanager.ui.feature.home

import android.text.format.Formatter
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SdCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.serhiiyaremych.composefilemanager.R
import com.serhiiyaremych.composefilemanager.data.StorageDataProvider
import com.serhiiyaremych.composefilemanager.data.UserStorage
import com.serhiiyaremych.composefilemanager.ui.theme.ComposeFileManagerTheme
import com.serhiiyaremych.composefilemanager.ui.theme.DeviceStorageColor
import com.serhiiyaremych.composefilemanager.ui.theme.DropboxColor
import com.serhiiyaremych.composefilemanager.ui.theme.GoogleDriveColor

@Composable
fun UserStorageCards(contentPadding: Dp) {
    val repositories = remember {
        listOf(
            UserStorage(
                dataProvider = StorageDataProvider.DEVICE,
                bytesUsed = 512,
                bytesTotal = 1024
            ),
            UserStorage(
                dataProvider = StorageDataProvider.GOOGLE_DRIVE,
                bytesUsed = 784,
                bytesTotal = 1024
            ),
            UserStorage(
                dataProvider = StorageDataProvider.DROP_BOX,
                bytesUsed = 256,
                bytesTotal = 1024
            )
        )
    }
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = contentPadding)
    ) {

        itemsIndexed(repositories) { index, repository ->
            val icon = iconForDataProvider(repository.dataProvider)

            val title = storageTitleFor(repository.dataProvider)
            val formattedBytesUsed = byteFormatter(repository.bytesUsed)
            val formattedBytesTotal = byteFormatter(repository.bytesTotal)

            val surfaceColor = colorFor(repository.dataProvider, MaterialTheme.colors.surface)
            val contentAccentColor = MaterialTheme.colors.onSurface

            Row {
                StorageCard(
                    dataIcon = icon,
                    dataIconTintColor = if (repository.dataProvider == StorageDataProvider.DEVICE) Color.White else Color.Unspecified,
                    surfaceColor = surfaceColor,
                    contentAccentColor = contentAccentColor,
                    cardTitle = title,
                    cardText = "$formattedBytesUsed / $formattedBytesTotal",
                    usedSpacePercentage =
                    (repository.bytesUsed / repository.bytesTotal.toFloat()).coerceAtMost(1.0f),
                    onClick = { /* TODO */ }
                )

                if (index < repositories.size) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}

@Composable
private fun iconForDataProvider(dataProvider: StorageDataProvider): Painter {
    return when (dataProvider) {
        StorageDataProvider.DEVICE -> rememberVectorPainter(image = Icons.Rounded.SdCard)
        StorageDataProvider.GOOGLE_DRIVE -> painterResource(id = R.drawable.ic_google_drive)
        StorageDataProvider.DROP_BOX -> painterResource(id = R.drawable.ic_dropbox)
    }
}

@Composable
private fun storageTitleFor(dataProvider: StorageDataProvider): String {
    return when (dataProvider) {
        StorageDataProvider.DEVICE -> stringResource(id = R.string.internal_storage)
        StorageDataProvider.GOOGLE_DRIVE -> stringResource(id = R.string.google_drive)
        StorageDataProvider.DROP_BOX -> stringResource(id = R.string.dropbox)
    }
}

@Composable
private fun byteFormatter(bytes: Long): String {
    val context = LocalContext.current
    return Formatter.formatShortFileSize(context, bytes)
}

@Composable
private fun colorFor(dataProvider: StorageDataProvider, onColor: Color): Color {
    val dataProviderColor = when (dataProvider) {
        StorageDataProvider.DEVICE -> DeviceStorageColor
        StorageDataProvider.GOOGLE_DRIVE -> GoogleDriveColor
        StorageDataProvider.DROP_BOX -> DropboxColor
    }
    return dataProviderColor.copy(alpha = 0.5f).compositeOver(onColor)
}

@Preview
@Composable
private fun UserStoragePreview() {
    ComposeFileManagerTheme {
        UserStorageCards(contentPadding = 16.dp)
    }
}