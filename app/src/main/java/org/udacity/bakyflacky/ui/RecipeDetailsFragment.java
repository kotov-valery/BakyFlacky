package org.udacity.bakyflacky.ui;

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
import android.widget.TextView;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.adapters.StepsAdaptor;
import org.udacity.bakyflacky.recipe.Recipe;
import org.udacity.bakyflacky.utility.IngredientsFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment {

    @BindView(R.id.tv_recipe_name) TextView name;
    @BindView(R.id.tv_recipe_ingredients) Button ingredients;
    @BindView(R.id.recipe_steps_list) RecyclerView stepsView;

    private Recipe recipe;
    public StepsAdaptor stepsAdaptor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, rootView);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        stepsView.setLayoutManager(layoutManager);

        stepsAdaptor = new StepsAdaptor();
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
            this.stepsAdaptor.setSteps(recipe.steps);
        }
    }
}
