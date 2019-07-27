package org.udacity.bakyflacky.appwidget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import org.udacity.bakyflacky.R;

public class UpdateIngredientsListService extends IntentService {
    public static final String INGREDIENTS = "ingredients";

    public static final String ACTION_UPDATE_INGREDIENTS_LIST
            = "org.udacity.bakyflacky.action.update_ingredients_list";

    public static void startActionUpdateIngredientList(Context context, String ingredients) {
        Intent intent = new Intent(context, UpdateIngredientsListService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS_LIST);
        intent.putExtra(INGREDIENTS, ingredients);
        context.startService(intent);
    }

    UpdateIngredientsListService() {
        super("UpdateIngredientsListService");
    }

    @Override
    protected void onHandleIntent(@NonNull Intent intent) {
        final String action = intent.getAction();
        if (action.equals(ACTION_UPDATE_INGREDIENTS_LIST)) {
            String ingredients;
            if (intent.hasExtra(INGREDIENTS)) {
                ingredients = intent.getStringExtra(INGREDIENTS);
            } else {
                ingredients = getString(R.string.no_ingredients_found);
            }
            handleUpdateIngredientsList(ingredients);
        }
    }

    private void handleUpdateIngredientsList(String ingredients) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsListWidget.class));
        IngredientsListWidget.updateAppWidgets(this, appWidgetManager, appWidgetIds, ingredients);
    }
}
