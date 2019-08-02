package org.udacity.bakyflacky.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.udacity.bakyflacky.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityClickOnAnItemTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityClickOnAnItemTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipies_list),
                        childAtPosition(
                                withClassName(is("android.widget.FrameLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_recipe_name), withText("Brownies"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Brownies")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tv_container), withText("Ingredients:\n * 350 G of Bittersweet chocolate (60-70% cacao);\n * 226 G of unsalted butter;\n * 300 G of granulated sugar;\n * 100 G of light brown sugar;\n * 5 UNIT of large eggs;\n * 1 TBLSP of vanilla extract;\n * 140 G of all purpose flour;\n * 40 G of cocoa powder;\n * 1.5 TSP of salt;\n * 350 G of semisweet chocolate chips;\n"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Ingredients:  * 350 G of Bittersweet chocolate (60-70% cacao);  * 226 G of unsalted butter;  * 300 G of granulated sugar;  * 100 G of light brown sugar;  * 5 UNIT of large eggs;  * 1 TBLSP of vanilla extract;  * 140 G of all purpose flour;  * 40 G of cocoa powder;  * 1.5 TSP of salt;  * 350 G of semisweet chocolate chips; ")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
