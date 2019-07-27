package org.udacity.bakyflacky.recipe;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {
    public int id;
    public String name;
    public ArrayList<Ingredient> ingredients = new ArrayList<>();
    public ArrayList<Step> steps = new ArrayList<>();
    public String servings;
    public String image;

    public Recipe(int id, String name, ArrayList<Ingredient> ingredients,
                  ArrayList<Step> steps, String servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public Recipe(Parcel parcel) {
        id = parcel.readInt();
        name = parcel.readString();
        parcel.readTypedList(this.ingredients, Ingredient.CREATOR);
        parcel.readTypedList(this.steps, Step.CREATOR);
        servings = parcel.readString();
        image = parcel.readString();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeString(servings);
        dest.writeString(image);
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
