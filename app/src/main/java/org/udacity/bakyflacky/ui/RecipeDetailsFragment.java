package org.udacity.bakyflacky.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.adapters.DetailsAdaptor;
import org.udacity.bakyflacky.recipe.Recipe;
import org.udacity.bakyflacky.recipe.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment {

    private static final String TAG = RecipeDetailsFragment.class.getSimpleName();

    @BindView(R.id.recipe_details_list) RecyclerView recipeDetails;

    private Recipe recipe;
    private DetailsAdaptor detailsAdaptor;

    private OnStepClickListener listener;

    public interface OnStepClickListener {
        void onClick(Step step);
    }

    private static final String RECIPE_OBJECT = "RecipeObject";

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(RECIPE_OBJECT, recipe);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            Log.e(TAG, context.getString(R.string.on_step_click_listener_not_implemented_error));
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            recipe = savedInstanceState.getParcelable(RECIPE_OBJECT);
        }

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
            this.detailsAdaptor.setRecipe(recipe);
        }
    }

    private class StepsClickListener implements DetailsAdaptor.OnStepsClickListener {
        @Override
        public void onClick(Step step) {
            listener.onClick(step);
        }
    }
}
