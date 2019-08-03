package org.udacity.bakyflacky.ui;

import android.content.Intent;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

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


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.recipe.Ingredient;
import org.udacity.bakyflacky.recipe.Recipe;
import org.udacity.bakyflacky.recipe.Step;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailsActivityOpenWithAnIntentTest {

    private static final String RECIPE_NAME = "My awesome recipe";
    private static final String RECIPE_STEP_DESCRIPTION = "Prepare everything";

    private Step step;
    private Recipe recipe;
    private Ingredient ingredient;

    @Rule
    public IntentsTestRule<RecipeDetailsActivity> mActivityTestRule =
            new IntentsTestRule<>(RecipeDetailsActivity.class, false, false);

    @Test
    public void testActivityLaunchedWithAnIntent() {
        recipe = new Recipe(0, RECIPE_NAME, new ArrayList<Ingredient>(),
                new ArrayList<Step>(), "2", "");
        Intent intent = new Intent();
        intent.putExtra(RecipeDetailsActivity.RECIPE_OBJECT, recipe);
        mActivityTestRule.launchActivity(intent);

        onView(withId(R.id.tv_recipe_name)).check(matches(withText(RECIPE_NAME)));
    }

    @Test
    public void testActivityLaunchesStepActivityWithAnIntent() {
        step = new Step(0, RECIPE_STEP_DESCRIPTION, "", "", "");
        ArrayList<Step> steps = new ArrayList<>();
        steps.add(step);

        ingredient = new Ingredient("2", "cups", "awesomeness");
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(ingredient);

        recipe = new Recipe(0, RECIPE_NAME, ingredients,
                steps, "2", "");

        Intent intent = new Intent();
        intent.putExtra(RecipeDetailsActivity.RECIPE_OBJECT, recipe);
        mActivityTestRule.launchActivity(intent);

        onView(withId(R.id.tv_recipe_name)).check(matches(withText(RECIPE_NAME)));

        onView(new RecyclerViewMatcher(R.id.recipe_details_list).atPosition(2))
                .check(matches(hasDescendant(withText("Servings: 2"))));

        onView(new RecyclerViewMatcher(R.id.recipe_details_list).atPosition(3))
                .check(matches(hasDescendant(withText("Steps"))));

        onView(new RecyclerViewMatcher(R.id.recipe_details_list).atPosition(4))
                .check(matches(hasDescendant(withText(RECIPE_STEP_DESCRIPTION))));

        onView(withId(R.id.recipe_details_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));

        intended(allOf(
                hasComponent(hasShortClassName(".ui.StepDetailsActivity")),
                hasExtraWithKey(StepDetailsActivity.RECIPE_NAME),
                hasExtraWithKey(StepDetailsActivity.STEP_OBJECT)));
    }

}
