package org.udacity.bakyflacky.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.adapters.RecipeAdaptor;
import org.udacity.bakyflacky.cookbook.CookBook;
import org.udacity.bakyflacky.recipe.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipies_list) RecyclerView recipesView;

    private RecipeAdaptor recipeAdaptor;
    private RecipeClickListener recipeClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        RecyclerView.LayoutManager reviewsLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recipesView.setLayoutManager(reviewsLayoutManager);

        recipeClickListener = new RecipeClickListener();

        recipeAdaptor = new RecipeAdaptor(recipeClickListener);
        recipesView.setAdapter(recipeAdaptor);

        CookBook.fetchRecipes(recipeAdaptor);
    }

    private class RecipeClickListener implements RecipeAdaptor.OnRecipeClickListener {
        @Override
        public void onClick(Recipe recipe) {
            Intent intent = new Intent(getApplicationContext(), RecipeDetailsActivity.class);
            intent.putExtra(RecipeDetailsActivity.RECIPE_OBJECT, recipe);
            startActivity(intent);
        }
    }
}
