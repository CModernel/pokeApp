package com.pedrogomez.pokeapp.pokelist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.rule.ActivityTestRule
import com.pedrogomez.pokeapp.R

@RunWith(AndroidJUnit4::class)
@LargeTest
class PokemonsListActivityTest{

    private lateinit var stringToBetyped: String

    @get:Rule
    var activityRule: ActivityTestRule<PokemonsListActivity>
            = ActivityTestRule(PokemonsListActivity::class.java)

    @Before
    fun initValidString() {
        // Specify a valid string.
        stringToBetyped = "Gabriel Garcia Marquez"
    }

    @Test
    fun changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.etSearchField))
                .perform(typeText(stringToBetyped), closeSoftKeyboard())
        //onView(withId(R.id.changeTextBt)).perform(click())

        // Check that the text was changed.
        //onView(withId(R.id.textToBeChanged))
        //        .check(matches(withText(stringToBetyped)))
    }
}