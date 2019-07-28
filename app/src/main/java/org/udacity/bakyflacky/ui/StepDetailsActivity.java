package org.udacity.bakyflacky.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.recipe.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsActivity extends AppCompatActivity {

    public static final String TAG = RecipeDetailsActivity.class.getSimpleName();

    public static final String RECIPE_NAME = "RecipeName";
    public static final String STEP_OBJECT = "StepObject";

    private String name;
    private Step step;

    @BindView(R.id.tv_recipe_name) TextView recipeName;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(RECIPE_NAME, name);
        outState.putParcelable(STEP_OBJECT, step);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(STEP_OBJECT) &&
                    intent.hasExtra(RECIPE_NAME)) {
            name = intent.getStringExtra(RECIPE_NAME);
            step = intent.getParcelableExtra(STEP_OBJECT);
        } else if (savedInstanceState != null) {
            name = savedInstanceState.getString(RECIPE_NAME);
            step = savedInstanceState.getParcelable(STEP_OBJECT);
        }

        if (name != null) {
            recipeName.setText(name);
        }

        if (savedInstanceState == null) {
            StepDetailsFragment detailsFragment = new StepDetailsFragment();
            detailsFragment.setStep(step);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.details_container, detailsFragment)
                    .commit();
        }
    }
}
