package org.udacity.bakyflacky.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.recipe.Recipe;
import org.udacity.bakyflacky.recipe.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {

    public static final String TAG = RecipeDetailsActivity.class.getSimpleName();

    public static final String RECIPE_OBJECT = "RecipeObject";

    @BindView(R.id.tv_recipe_name) TextView recipeName;

    private boolean useTabletLayout = false;
    private boolean stepWasChosen = false;

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        if (findViewById(R.id.details_tablet_layout) != null) {
            useTabletLayout = true;
        } else {
            useTabletLayout = false;
        }

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra(RECIPE_OBJECT)) {
                recipe = intent.getParcelableExtra(RECIPE_OBJECT);

                recipeName.setText(recipe.name);

                RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment();
                detailsFragment.setRecipe(recipe);
                detailsFragment.setClickListener(new StepsClickListener());

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.details_container, detailsFragment)
                        .commit();
            }
        }
    }

    private class StepsClickListener implements RecipeDetailsFragment.OnStepClickListener {
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
}
