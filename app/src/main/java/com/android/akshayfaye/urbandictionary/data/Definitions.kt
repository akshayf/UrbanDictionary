package com.android.akshayfaye.urbandictionary.data

import com.google.gson.annotations.SerializedName

/**
 * Data class for Definitions
 */
data class Definitions (val definition : String, val permalink : String, @SerializedName("thumbs_up")val thumbsUp : Int,
                        val example : String, @SerializedName("thumbs_down")val thumbsDown : Int)