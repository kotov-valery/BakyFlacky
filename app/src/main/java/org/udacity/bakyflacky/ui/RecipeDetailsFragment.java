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
import android.widget.TextView;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.adapters.DetailsAdaptor;
import org.udacity.bakyflacky.recipe.Recipe;
import org.udacity.bakyflacky.recipe.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment {

    @BindView(R.id.tv_recipe_name) TextView name;
    @BindView(R.id.recipe_details_list) RecyclerView recipeDetails;

    private Recipe recipe;
    private DetailsAdaptor detailsAdaptor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, rootView);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recipeDetails.setLayoutManager(layoutManager);

        detailsAdaptor = new DetailsAdaptor(new StepsClickListener());
        recipeDetails.setAdapter(detailsAdaptor);

        updateView();

        return rootView;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    private void updateView() {
        if (recipe != null) {
            this.name.setText(recipe.name);
            this.detailsAdaptor.setRecipe(recipe);
        }
    }

    private class StepsClickListener implements DetailsAdaptor.OnStepsClickListener {
        @Override
        public void onClick(Step step) {
            Intent intent = new Intent(getActivity().getBaseContext(), StepDetailsActivity.class);
            intent.putExtra(StepDetailsActivity.RECIPE_NAME, recipe.name);
            intent.putExtra(StepDetailsActivity.STEP_OBJECT, step);
            startActivity(intent);
        }
    }
}
