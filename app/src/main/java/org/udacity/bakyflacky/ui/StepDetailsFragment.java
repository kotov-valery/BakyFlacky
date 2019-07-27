package org.udacity.bakyflacky.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.recipe.Step;
import org.udacity.bakyflacky.utility.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailsFragment extends Fragment {

    @BindView(R.id.tv_recipe_name) TextView name;
    @BindView(R.id.tv_step_short_description) TextView description;
    @BindView(R.id.btn_step_instruction) Button instructions;
    @BindView(R.id.img_step_thumbnail) ImageView thumbnail;

    private Step step;
    private String recipeName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps_details, container, false);
        ButterKnife.bind(this, rootView);
        updateView();
        return rootView;
    }

    public void setStep(Step step) {
        this.step= step;
    }

    public void setName(String name) {
        this.recipeName = name;
    }

    private void updateView() {
        this.name.setText(recipeName);
        this.description.setText(step.shortDescription);
        this.instructions.setText(step.description);
    }
}
