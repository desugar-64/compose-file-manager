package com.serhiiyaremych.composefilemanager.ui.feature.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SdCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serhiiyaremych.composefilemanager.ui.theme.ComposeFileManagerTheme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StorageCard(
    surfaceColor: Color,
    contentAccentColor: Color,
    cardName: String
) {
    Card(
        onClick = { /*TODO*/ },
        backgroundColor = surfaceColor,
        modifier = Modifier.size(250.dp, 150.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.SdCard,
                contentDescription = null,
                tint = Color.White
                    .compositeOver(contentAccentColor),
                modifier = Modifier
                    .background(
                        shape = MaterialTheme.shapes.small.copy(CornerSize(10.dp)),
                        color = contentAccentColor
                            .copy(alpha = 0.2f)
                            .compositeOver(surfaceColor)
                    )
                    .padding(6.dp)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = cardName,
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold,
                            color = contentAccentColor
                        )
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "67 Gb / 128 Gb",
                        style = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Bold)
                    )
                }

                CircularProgressIndicator(progress = 0.95f, color = contentAccentColor)
            }
        }
    }
}

@Preview(name = "StorageCard")
@Preview(name = "StorageCardDark", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun StoragePreview() {
    ComposeFileManagerTheme {
        StorageCard(
            surfaceColor = MaterialTheme.colors.background,
            contentAccentColor = Color.White,
            "Internal storage"
        )
    }
}