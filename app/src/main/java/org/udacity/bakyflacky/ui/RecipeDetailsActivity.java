package org.udacity.bakyflacky.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.recipe.Recipe;
import org.udacity.bakyflacky.recipe.Step;
import org.udacity.bakyflacky.utility.Json;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity
    implements RecipeDetailsFragment.OnStepClickListener
{
    public static final String TAG = RecipeDetailsActivity.class.getSimpleName();

    public static final String RECIPE_OBJECT = "RecipeObject";

    @BindView(R.id.tv_recipe_name) TextView recipeName;

    private Recipe recipe;

    private boolean stepWasChosen = false;
    private boolean useTabletLayout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        useTabletLayout = getResources().getBoolean(R.bool.isTablet);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(RECIPE_OBJECT)) {
            recipe = intent.getParcelableExtra(RECIPE_OBJECT);
        } else if (savedInstanceState != null && savedInstanceState.containsKey(RECIPE_OBJECT)) {
            recipe = savedInstanceState.getParcelable(RECIPE_OBJECT);
        } else {
            SharedPreferences preferences = getPreferences(getApplicationContext().MODE_PRIVATE);
            String recipeJson = preferences.getString(RECIPE_OBJECT, "");
            try {
                recipe = Json.deSerialize(recipeJson, Recipe.class);
            } catch (ClassNotFoundException error) {
                error.printStackTrace();
                Log.e(TAG, "Could not parse json object: " + recipeJson);
            }
        }

        if (recipe != null) {
            recipeName.setText(recipe.name);

            if (savedInstanceState == null) {
                RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment();
                detailsFragment.setRecipe(recipe);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.details_container, detailsFragment)
                        .commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RECIPE_OBJECT, recipe);

        SharedPreferences preferences = getPreferences(getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(RECIPE_OBJECT, Json.serialize(recipe));
        editor.apply();

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(Step step) {
        if (useTabletLayout) {
            StepDetailsFragment detailsFragment = new StepDetailsFragment();
            detailsFragment.setStep(step);

            FragmentManager fragmentManager = getSupportFragmentManager();

            if (stepWasChosen) {
                fragmentManager.beginTransaction()
                        .replace(R.id.step_container, detailsFragment)
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .add(R.id.step_container, detailsFragment)
                        .commit();
                stepWasChosen = true;
            }
        } else {
            Intent intent = new Intent(getApplicationContext(), StepDetailsActivity.class);
            intent.putExtra(StepDetailsActivity.RECIPE_NAME, recipe.name);
            intent.putExtra(StepDetailsActivity.STEP_OBJECT, step);
            startActivity(intent);
        }
    }
}
