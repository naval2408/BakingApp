package com.example.navalkishore.bakingapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.navalkishore.bakingapp.activities.MainActivity;
import com.example.navalkishore.bakingapp.activities.SelectReceipeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityBasicTest {
    private static final String RECEIPE_LABEL="Brownies";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkIfRecyclerViewDisplayed() {
        onView(withId(R.id.rv_main_dishes)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void checkIfProgressBarHidden() {
        onView(withId(R.id.pb_loading_data_from_file)).check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        onView(withText("Brownies")).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void checkIfARecepiePresnt() {
        onView(withText(RECEIPE_LABEL)).check(ViewAssertions.matches(isDisplayed()));
    }



}
