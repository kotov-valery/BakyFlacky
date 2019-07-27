package org.udacity.bakyflacky.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.recipe.Step;

public class StepDetailsActivity extends AppCompatActivity {

    public static final String TAG = RecipeDetailsActivity.class.getSimpleName();

    public static final String RECIPE_NAME = "RecipeName";
    public static final String STEP_OBJECT = "StepObject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra(STEP_OBJECT) &&
                    intent.hasExtra(RECIPE_NAME)) {
                StepDetailsFragment detailsFragment = new StepDetailsFragment();

                String recipeName = intent.getStringExtra(RECIPE_NAME);
                detailsFragment.setName(recipeName);

                Step step= intent.getParcelableExtra(STEP_OBJECT);
                detailsFragment.setStep(step);

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
