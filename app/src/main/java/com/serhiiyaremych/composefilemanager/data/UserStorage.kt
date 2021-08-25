package com.serhiiyaremych.composefilemanager.data

import androidx.compose.runtime.Immutable

enum class StorageDataProvider { DEVICE, GOOGLE_DRIVE, DROP_BOX }

@Immutable
data class UserStorage(
    val dataProvider: StorageDataProvider,
    val bytesUsed: Long,
    val bytesTotal: Long
)
