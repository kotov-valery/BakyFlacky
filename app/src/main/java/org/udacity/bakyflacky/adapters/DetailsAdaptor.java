package org.udacity.bakyflacky.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.recipe.Recipe;
import org.udacity.bakyflacky.recipe.Step;
import org.udacity.bakyflacky.utility.ImageLoader;
import org.udacity.bakyflacky.utility.IngredientsFormatter;

import java.util.List;


public class DetailsAdaptor extends RecyclerView.Adapter<DetailsAdaptor.DetailsViewHolder> {

    private static final int PREDEFINED_FILEDS_COUNT = 4;

    private static final int RECIPE_PREVIEW_FILED_INDEX = 0;
    private static final int INGRIDIENTS_FILED_INDEX = 1;
    private static final int SERVINGS_FILED_INDEX = 2;
    private static final int STEPS_TITLE_FILED_INDEX = 3;

    private List<Step> steps;
    private Recipe recipe;
    private OnStepsClickListener listener;

    public interface OnStepsClickListener {
        void onClick(Step step);
    }

    public DetailsAdaptor(OnStepsClickListener listener) {
        this.listener = listener;
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {
        private final Context context;

        private final TextView container;
        private final ImageView preview;

        public DetailsViewHolder(View view) {
            super(view);
            this.context = view.getContext();

            container = view.findViewById(R.id.tv_container);
            preview = view.findViewById(R.id.img_recipe_preview);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            if (pos > STEPS_TITLE_FILED_INDEX) {
                listener.onClick(steps.get(pos - PREDEFINED_FILEDS_COUNT));
            }
        }
    };

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.details_main_list_item, parent, false);
        DetailsViewHolder detailsViewHolder = new DetailsViewHolder(view);
        view.setOnClickListener(detailsViewHolder);
        return detailsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder detailsViewHolder, int i) {
        String text = "";
        if (i == RECIPE_PREVIEW_FILED_INDEX) {
            if (recipe.image != null && !recipe.image.isEmpty()) {
                ImageLoader.fetchIntoView(recipe.image, detailsViewHolder.preview);
            }
        } else if (i == INGRIDIENTS_FILED_INDEX) {
            text = IngredientsFormatter.format(recipe);
            detailsViewHolder.container.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        } else if (i == SERVINGS_FILED_INDEX) {
            text = detailsViewHolder.context.getString(R.string.servings) + ": " + recipe.servings;
            detailsViewHolder.container.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        } else if (i == STEPS_TITLE_FILED_INDEX) {
            text = detailsViewHolder.context.getString(R.string.steps);

            detailsViewHolder.container.setBackgroundColor(
                    ContextCompat.getColor(detailsViewHolder.context, R.color.colorPrimary));
        } else {
            Step step = steps.get(i - PREDEFINED_FILEDS_COUNT);
            text = step.shortDescription;
        }

        if (text != null && !text.isEmpty()) {
            detailsViewHolder.container.setVisibility(View.VISIBLE);
            detailsViewHolder.container.setText(text);
        }
    }

    @Override
    public int getItemCount() {
        return PREDEFINED_FILEDS_COUNT + (steps != null ? steps.size() : 0);
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        this.steps = recipe.steps;
        notifyDataSetChanged();
    }
}

