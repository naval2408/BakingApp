package com.example.navalkishore.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.navalkishore.bakingapp.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class EndToEndTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private static final String PLACEHOLDER = "Recipe Introduction";

    @Test
    public void checkIfDetailsOfReceipeOpens() {
        onView(withId(R.id.rv_main_dishes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.tv_ingredents_label)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void checkIfIngredentsDetialsOpens() {
        onView(withId(R.id.rv_main_dishes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.tv_ingredents_label)).perform(click());
        onView(withId(R.id.tv_ingredents_list)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void checkIfExoPlayerProgressBarIsDisabled() {
        onView(withId(R.id.rv_main_dishes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.rv_select_receipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.progressBarForExoPlayer)).check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @Test
    public void checkPlaceholderForReceipeStep() {
        onView(withId(R.id.rv_main_dishes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.rv_select_receipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withText(PLACEHOLDER)).check(matches(isDisplayed()));
    }

}
