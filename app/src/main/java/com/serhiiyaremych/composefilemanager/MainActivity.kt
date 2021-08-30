package com.serhiiyaremych.composefilemanager

import android.content.ContentUris
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.toOffset
import com.serhiiyaremych.composefilemanager.ext.darken
import com.serhiiyaremych.composefilemanager.ext.lighten
import com.serhiiyaremych.composefilemanager.ui.feature.home.HomeScreen
import com.serhiiyaremych.composefilemanager.ui.theme.Color1
import com.serhiiyaremych.composefilemanager.ui.theme.ComposeFileManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeFileManagerTheme {
                val surfaceColor = if (MaterialTheme.colors.isLight) {
                    Color1
                } else {
                    MaterialTheme.colors.surface
                }
                val gradient = listOf(
                    surfaceColor.lighten(0.01f),
                    surfaceColor,
                    surfaceColor.darken(0.01f),
                    surfaceColor.darken(0.02f),
                )
                var componentSize by remember {
                    mutableStateOf(IntSize.Zero)
                }
                Box(modifier = Modifier
                    .onGloballyPositioned {
                        componentSize = it.size
                    }
                    .background(
                        Brush.radialGradient(
                            gradient,
                            center = componentSize.center.toOffset()
                        )
                    )
                ) {
                    HomeScreen()
                }
            }
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            startActivity(Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION))
//        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            val requestPemissions =
//                registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { isGranted ->
//                    // TODO
//                }
//            requestPemissions.launch()
//        } else { }

        val collection = MediaStore.Files.getContentUri("external")

        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.SIZE
        )

        val selection = ""

        val cursor = contentResolver.query(
            collection,
            projection,
            selection,
            null,
            null
        )

        if (cursor != null) {

            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val size = cursor.getInt(sizeColumn)
                val data = cursor.getString(dataColumn)

                val contentUri: Uri = ContentUris.withAppendedId(collection, id)

                Log.d("FILES", "File found: { id: $id, name: $name, size: $size, data: $data }")
            }


            cursor.close()
        }
    }
}
