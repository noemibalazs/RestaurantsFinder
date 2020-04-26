package com.example.restaurantsfinder

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.restaurantsfinder.ui.BreweryLandingActivity
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class BreweryLandingActivityTest {

    @get: Rule
    val activityTest = ActivityTestRule(BreweryLandingActivity::class.java)

    @Test
    fun testNavigationViewIsDisplayed() {
        activityTest.activity.runOnUiThread {
            activityTest.activity.navController.navigate(R.id.breweriesFragment)
        }

        onView(withContentDescription(R.string.navigation_drawer_open)).perform(click())
        onView(withId(R.id.navView)).check(matches(isDisplayed()))
    }

    @Test
    fun testNavHostFragmentIsVisible() {
        onView(withId(R.id.nav_host_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun testToolbarIsVisible() {
        activityTest.activity.runOnUiThread {
            activityTest.activity.navController.navigate(R.id.breweriesFragment)
        }
        onView(withId(R.id.toolBar)).check(matches(isDisplayed()))
        onView(withId(R.id.toolBar)).check(matches(hasDescendant(withText(R.string.menu_breweries))))
    }

    @Test
    fun testDrawerLayoutIsVisible() {
        onView(withId(R.id.drawerLayout)).check(matches(isDisplayed()))
    }
}