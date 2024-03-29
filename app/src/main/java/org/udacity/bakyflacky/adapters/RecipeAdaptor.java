package org.udacity.bakyflacky.adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;


public class RecipeAdaptor extends RecyclerView.Adapter<RecipeAdaptor.RecipeViewHolder> {

    private ArrayList<Recipe> recipes;
    private OnRecipeClickListener mCallback;

    private static final String RECIPES_LIST = "RecipesList";

    public interface OnRecipeClickListener {
        void onClick(Recipe recipe);
    }

    public RecipeAdaptor(OnRecipeClickListener callback) {
        mCallback = callback;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {
        private final TextView name;

        public RecipeViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_recipe_name);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mCallback.onClick(recipes.get(pos));
        }
    };

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.recipe_main_list_item, parent, false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);
        view.setOnClickListener(recipeViewHolder);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
        Recipe recipe = recipes.get(i);
        recipeViewHolder.name.setText(recipe.name);
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = new ArrayList<>(recipes);
        notifyDataSetChanged();
    }

    public boolean hasSavedState(Bundle bundle) {
        return bundle.containsKey(RECIPES_LIST);
    }

    public void saveStateTo(Bundle bundle) {
        bundle.putParcelableArrayList(RECIPES_LIST, recipes);
    }

    public void restoreStateFrom(Bundle bundle) {
        recipes = bundle.getParcelableArrayList(RECIPES_LIST);
        notifyDataSetChanged();
    }
}

