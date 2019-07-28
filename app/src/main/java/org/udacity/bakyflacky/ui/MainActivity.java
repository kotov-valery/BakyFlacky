package org.udacity.bakyflacky.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.adapters.RecipeAdaptor;
import org.udacity.bakyflacky.appwidget.UpdateIngredientsListService;
import org.udacity.bakyflacky.cookbook.CookBook;
import org.udacity.bakyflacky.recipe.Recipe;
import org.udacity.bakyflacky.utility.IngredientsFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipies_list) RecyclerView recipesView;

    private RecipeAdaptor recipeAdaptor;
    private RecipeClickListener recipeClickListener;

    private static final int COLUMN_COUNT = 3;
    private boolean useTableLayout = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (findViewById(R.id.tablet_layout) != null) {
            useTableLayout = true;
        } else {
            useTableLayout = false;
        }

        RecyclerView.LayoutManager reviewsLayoutManager;
        if (useTableLayout) {
            reviewsLayoutManager =
                    new GridLayoutManager(this, COLUMN_COUNT);
        } else {
            reviewsLayoutManager =
                    new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        }
        recipesView.setLayoutManager(reviewsLayoutManager);

        recipeClickListener = new RecipeClickListener();
        recipeAdaptor = new RecipeAdaptor(recipeClickListener);
        recipesView.setAdapter(recipeAdaptor);

        if (savedInstanceState != null && recipeAdaptor.hasSavedState(savedInstanceState)) {
            recipeAdaptor.restoreStateFrom(savedInstanceState);
        } else {
            CookBook.fetchRecipes(recipeAdaptor);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        recipeAdaptor.saveStateTo(outState);
        super.onSaveInstanceState(outState);
    }

    private class RecipeClickListener implements RecipeAdaptor.OnRecipeClickListener {
        @Override
        public void onClick(Recipe recipe) {
            String ingredients = IngredientsFormatter.format(recipe);
            UpdateIngredientsListService.startActionUpdateIngredientList(getApplicationContext(), ingredients);

            Intent intent = new Intent(getApplicationContext(), RecipeDetailsActivity.class);
            intent.putExtra(RecipeDetailsActivity.RECIPE_OBJECT, recipe);
            startActivity(intent);
        }
    }
}
