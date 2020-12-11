package com.example.android1;

import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
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
public class MainActivityTest  {

    private String username;
    private String password;
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        username = "username1@kue.com";
        password = "password1";
    }
    @Before
    public void setUp() {
        Intents.init();
    }
    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.username))
                .perform(typeText(username), closeSoftKeyboard());

        // Check that the text was changed.
        onView(withId(R.id.username))
                .check(matches(withText(username)));

        onView(withId(R.id.password))
                .perform(typeText(password), closeSoftKeyboard());

        // Check that the text was changed.
        onView(withId(R.id.password))
                .check(matches(withText(password)));

        onView(withId(R.id.button_start_sign_up)).perform(click());

        intended(allOf(
                hasComponent(hasShortClassName(".SignUpActivity")),
                toPackage("com.example.android1")
        ));


    }
    @After
    public void tearDown() {
        Intents.release();
    }
}