package org.udacity.bakyflacky.recipe;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {
    public String name;
    public ArrayList<Ingredient> ingredients = new ArrayList<>();
    public ArrayList<Step> steps = new ArrayList<>();

    public Recipe(String name, ArrayList<Ingredient> ingredients,
                  ArrayList<Step> steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe(Parcel parcel) {
        name = parcel.readString();
        parcel.readTypedList(this.ingredients, Ingredient.CREATOR);
        parcel.readTypedList(this.steps, Step.CREATOR);
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
