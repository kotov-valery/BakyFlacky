package org.udacity.bakyflacky.recipe;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    public String quantity;
    public String measure;
    public String ingredient;

    public Ingredient(String quantity, String measure,
                      String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public Ingredient(Parcel parcel) {
        quantity = parcel.readString();
        measure = parcel.readString();
        ingredient = parcel.readString();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
