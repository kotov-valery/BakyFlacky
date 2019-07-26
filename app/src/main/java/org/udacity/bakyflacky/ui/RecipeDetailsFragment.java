package org.udacity.bakyflacky.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.recipe.Recipe;
import org.udacity.bakyflacky.utility.IngredientsFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment {

    @BindView(R.id.tv_recipe_name) TextView name;
    @BindView(R.id.tv_recipe_ingredients) Button ingredients;

    private Recipe recipe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, rootView);
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
        }
    }
}
