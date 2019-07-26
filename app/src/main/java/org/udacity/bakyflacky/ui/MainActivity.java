package org.udacity.bakyflacky.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.adapters.RecipeAdaptor;
import org.udacity.bakyflacky.cookbook.CookBook;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipies_list) RecyclerView recipesView;

    private RecipeAdaptor recipeAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        RecyclerView.LayoutManager reviewsLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recipesView.setLayoutManager(reviewsLayoutManager);

        recipeAdaptor = new RecipeAdaptor();
        recipesView.setAdapter(recipeAdaptor);

        CookBook.fetchRecipes(recipeAdaptor);
    }
}
