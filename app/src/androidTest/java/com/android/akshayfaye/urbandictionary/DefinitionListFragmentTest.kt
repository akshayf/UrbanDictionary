package com.android.akshayfaye.urbandictionary

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.android.akshayfaye.urbandictionary.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test for DefinitionListFragment
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class DefinitionListFragmentTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testSpinner() {
        Espresso.onView(withId(R.id.thump_sort_spinner))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testRecycleView() {
        Espresso.onView(withId(R.id.dictionary_recycler_view))
            .check(matches(isDisplayed()))
    }
}