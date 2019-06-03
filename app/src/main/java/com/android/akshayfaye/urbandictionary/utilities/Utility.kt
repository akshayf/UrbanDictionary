package com.android.akshayfaye.urbandictionary.utilities

import android.view.View

/**
 * OnClickListener for safe call
 */
fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}