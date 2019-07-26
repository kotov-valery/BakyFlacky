package org.udacity.bakyflacky.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.recipe.Step;

import java.util.List;


public class StepsAdaptor extends RecyclerView.Adapter<StepsAdaptor.StepViewHolder> {

    private List<Step> steps;

    public class StepViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView name;

        public StepViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_step_name);
        }
    };

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.step_main_list_item, parent, false);
        StepViewHolder stepViewHolder = new StepViewHolder(view);
        return stepViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder stepViewHolder, int i) {
        Step step = steps.get(i);
        stepViewHolder.name.setText(step.shortDescription);
    }

    @Override
    public int getItemCount() {
        return steps != null ? steps.size() : 0;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }
}

