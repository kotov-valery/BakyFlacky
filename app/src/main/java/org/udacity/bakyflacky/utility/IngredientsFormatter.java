package org.udacity.bakyflacky.utility;

import org.udacity.bakyflacky.recipe.Ingredient;
import org.udacity.bakyflacky.recipe.Recipe;

import java.util.ArrayList;

public class IngredientsFormatter {
    public static String format(Recipe recipe) {
        String result = "Ingredients:\n";
        ArrayList<Ingredient> ingredients = recipe.ingredients;
        for (int i=0; i<ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            result += " * " + ingredient.quantity + " " + ingredient.measure +
                " of " + ingredient.ingredient + ";\n";
        }
        return result;
    }
}
