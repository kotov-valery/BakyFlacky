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
    private OnStepsClickListener listener;

    public interface OnStepsClickListener {
        void onClick(Step step);
    }

    public StepsAdaptor(OnStepsClickListener listener) {
        this.listener = listener;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener
    {
        private final TextView name;

        public StepViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_step_name);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onClick(steps.get(pos));
        }
    };

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.step_main_list_item, parent, false);
        StepViewHolder stepViewHolder = new StepViewHolder(view);
        view.setOnClickListener(stepViewHolder);
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

