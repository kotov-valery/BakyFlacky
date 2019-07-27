package org.udacity.bakyflacky.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.adapters.StepsAdaptor;
import org.udacity.bakyflacky.recipe.Recipe;
import org.udacity.bakyflacky.recipe.Step;
import org.udacity.bakyflacky.utility.ImageLoader;
import org.udacity.bakyflacky.utility.IngredientsFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment {

    @BindView(R.id.tv_recipe_name) TextView name;
    @BindView(R.id.tv_recipe_ingredients) Button ingredients;
    @BindView(R.id.tv_recipe_servings) Button servings;
    @BindView(R.id.img_recipe_preview) ImageView preview;
    @BindView(R.id.recipe_steps_list) RecyclerView stepsView;

    private Recipe recipe;
    private StepsAdaptor stepsAdaptor;
    private StepsClickListener stepsClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, rootView);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        stepsView.setLayoutManager(layoutManager);

        stepsClickListener = new StepsClickListener();
        stepsAdaptor = new StepsAdaptor(stepsClickListener);
        stepsView.setAdapter(stepsAdaptor);

        updateView();

        return rootView;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    private void updateView() {
        if (recipe != null) {
            this.name.setText(recipe.name);
            this.ingredients.setText(IngredientsFormatter.format(recipe));
            this.servings.setText(getString(R.string.servings) + ": " + recipe.servings);
            this.stepsAdaptor.setSteps(recipe.steps);

            if (recipe.image != null && !recipe.image.isEmpty()) {
                ImageLoader.fetchIntoView(recipe.image, preview);
            }
        }
    }

    private class StepsClickListener implements StepsAdaptor.OnStepsClickListener {
        @Override
        public void onClick(Step step) {
            Intent intent = new Intent(getActivity().getBaseContext(), StepDetailsActivity.class);
            intent.putExtra(StepDetailsActivity.RECIPE_NAME, recipe.name);
            intent.putExtra(StepDetailsActivity.STEP_OBJECT, step);
            startActivity(intent);
        }
    }
}
