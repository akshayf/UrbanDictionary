package com.android.akshayfaye.urbandictionary

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.android.akshayfaye.urbandictionary.ui.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test for SearchFragment
 */
@RunWith(AndroidJUnit4 ::class)
@LargeTest
class SearchFragmentTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test fun testSearchEditTextView() {
        onView(withId(R.id.dictionary_search_edit_text))
            .check(matches(isDisplayed()))
    }

    @Test fun testDefinitionButtonCLick() {
        onView(withId(R.id.find_definition_button))
            .perform(click())
            .check(matches(isDisplayed()))
    }
}
