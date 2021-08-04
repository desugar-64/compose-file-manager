package com.serhiiyaremych.composefilemanager.ui.feature.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.serhiiyaremych.composefilemanager.R
import com.serhiiyaremych.composefilemanager.ui.theme.ComposeFileManagerTheme
import com.serhiiyaremych.composefilemanager.ui.theme.Shapes

@Composable
fun SearchBar(modifier: Modifier = Modifier, elevation: Dp = 12.dp, onClick: () -> Unit) {

    val surfaceColor = if (MaterialTheme.colors.isLight)
        Color.White
    else
        MaterialTheme.colors.surface

    Surface(
        color = surfaceColor,
        contentColor = MaterialTheme.colors.onSurface,
        shape = Shapes.small,
        elevation = elevation,
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(48.dp)
            .clip(Shapes.small)
            .clickable(onClick = onClick)
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Text(
                text = stringResource(R.string.file_search_hint),
                fontWeight = FontWeight.Bold
            )

            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

    }
}

@Preview(name = "SearchBar")
@Preview(name = "SearchBar Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SearchBarPreview() {
    ComposeFileManagerTheme {
        SearchBar(onClick = {})
    }
}