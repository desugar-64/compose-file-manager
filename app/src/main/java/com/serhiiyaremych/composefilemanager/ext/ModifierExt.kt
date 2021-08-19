package com.serhiiyaremych.composefilemanager.ext

import androidx.compose.ui.Modifier

fun Modifier.applyIf(condition: Boolean, block: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        this.then(block.invoke(this))
    } else {
        this
    }
}