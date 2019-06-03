package com.android.akshayfaye.urbandictionary.data

import com.google.gson.annotations.SerializedName

/**
 * Data class for List of Definitions
 */
data class Dictionary (@SerializedName("list")val definitionList : List<Definitions>)

