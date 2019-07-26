package org.udacity.bakyflacky.cookbook;

import android.support.annotation.NonNull;
import android.util.Log;

import org.udacity.bakyflacky.adapters.RecipeAdaptor;
import org.udacity.bakyflacky.recipe.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CookBook {

    private static final String TAG = CookBook.class.getSimpleName();

    public static void fetchRecipes(@NonNull final RecipeAdaptor adapter) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CookBookService.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CookBookService.CookBookAPI service =
                retrofit.create(CookBookService.CookBookAPI.class);

        Call<List<Recipe>> call = service.fetchRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                try {
                    List<Recipe> recipes = response.body();
                    Log.d(TAG, "Got " + recipes.size() + " recipes");
                    adapter.setRecipes(recipes);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, "Failed to request a recipe: " + t.getMessage());
                t.getStackTrace();
            }
        });
    }
}
