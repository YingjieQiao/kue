package com.example.android1;


import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;
import static org.hamcrest.core.AllOf.allOf;
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class HomePageActivityTest  {

    @Rule
    public ActivityScenarioRule<HomePageActivity> activityRule
            = new ActivityScenarioRule<>(HomePageActivity.class);


    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void testButton() {
        // Type text and then press the button.
        onView(withId(R.id.button_cook)).check(matches(isDisplayed()));
        onView(withId(R.id.button_cashier)).check(matches(isDisplayed()));
        onView(withId(R.id.button_settings)).check(matches(isDisplayed()));
        onView(withId(R.id.button_statistics)).check(matches(isDisplayed()));
    }
}