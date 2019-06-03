package com.android.akshayfaye.urbandictionary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.akshayfaye.urbandictionary.R

/**
 * MainActivity to hold DefinitionListFragment & SearchFragment
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
