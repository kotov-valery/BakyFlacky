package org.udacity.bakyflacky.cookbook;

import org.udacity.bakyflacky.recipe.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public class CookBookService {

    public static final String API_BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    public static final String API_RECIPES_DATE = "2017/May";
    public static final String API_RECIPES_LOCATION = "59121517_baking/baking.json";

    public interface CookBookAPI {
        @GET("topher/" + API_RECIPES_DATE + "/" + API_RECIPES_LOCATION)
        Call<List<Recipe>> fetchRecipes();
    }
}
