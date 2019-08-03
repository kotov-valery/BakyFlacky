package org.udacity.bakyflacky.ui;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.udacity.bakyflacky.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

//@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityClickOnAnItemTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void mainActivityClickOnAnItemTest() {
        onView(new RecyclerViewMatcher(R.id.recipies_list).atPosition(1))
                .check(matches(hasDescendant(withText("Brownies"))));

        onView(withId(R.id.recipies_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        intended(allOf(
                hasComponent(hasShortClassName(".ui.RecipeDetailsActivity")),
                        hasExtraWithKey(RecipeDetailsActivity.RECIPE_OBJECT)));
    }
}
