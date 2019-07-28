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

    @BindView(R.id.tv_recipe_name) TextView recipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra(STEP_OBJECT) &&
                    intent.hasExtra(RECIPE_NAME)) {
                StepDetailsFragment detailsFragment = new StepDetailsFragment();

                String name = intent.getStringExtra(RECIPE_NAME);
                recipeName.setText(name);

                Step step= intent.getParcelableExtra(STEP_OBJECT);
                detailsFragment.setStep(step);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.details_container, detailsFragment)
                        .commit();
            }
        }
    }
}
