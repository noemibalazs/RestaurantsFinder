package com.example.restaurantsfinder

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.example.restaurantsfinder.breweries.BreweryVH
import com.example.restaurantsfinder.ui.BreweryLandingActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class FragmentBreweriesTest {

    @get: Rule
    val activityTest = ActivityTestRule(BreweryLandingActivity::class.java)

    @Test
    fun testToolbarIsVisible() {
        activityTest.activity.runOnUiThread {
            activityTest.activity.navController.navigate(R.id.breweriesFragment)
        }
        onView(withId(R.id.toolBar))
            .check(matches(isDisplayed()))
        onView(withId(R.id.toolBar))
            .check(matches(hasDescendant(withText(R.string.menu_breweries))))
    }


    @Test
    fun testRecycleViewIsVisibleClickable() {
        activityTest.activity.runOnUiThread {
            activityTest.activity.navController.navigate(R.id.breweriesFragment)
        }

        onView(isRoot()).perform(EspressoHelper.waitFor(1900L))
        onView(withId(R.id.rv_breweries)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_breweries)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BreweryVH>(
                0,
                click()
            )
        )
    }

    @Test
    fun testEmptyContainerLayoutIsNotVisible() {
        activityTest.activity.runOnUiThread {
            activityTest.activity.navController.navigate(R.id.breweriesFragment)
        }

        onView(withId(R.id.cl_empty_container)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testNoInternetEmptyLayoutIsVisible() {
        activityTest.activity.runOnUiThread {
            activityTest.activity.navController.navigate(R.id.breweriesFragment)
        }

        onView(isRoot()).perform(EspressoHelper.waitFor(1900L))
        onView(withId(R.id.cl_empty_container)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_empty_logo)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_empty_info)).check(matches((withText(R.string.empty_info))))
        onView(withId(R.id.tv_empty_info)).check(matches(hasTextColor(R.color.black_open)))
        onView(withId(R.id.tv_empty_info)).check(matches((EspressoHelper.checkTextSize(18f))))
    }

}