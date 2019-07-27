package org.udacity.bakyflacky.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.recipe.Recipe;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String TAG = RecipeDetailsActivity.class.getSimpleName();

    public static final String RECIPE_OBJECT = "RecipeObject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra(RECIPE_OBJECT)) {
                RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment();

                Recipe recipe = intent.getParcelableExtra(RECIPE_OBJECT);
                detailsFragment.setRecipe(recipe);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.details_container, detailsFragment)
                        .commit();
            } else {
                Log.e(TAG, "Not possible to start an activity either " +
                        "without intent or without saved state");
            }
        }
    }
}
