package org.udacity.bakyflacky.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.RemoteViews;

import org.udacity.bakyflacky.R;
import org.udacity.bakyflacky.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsListWidget extends AppWidgetProvider {

    static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                 int[] appWidgetIds, String ingredients) {
        for(int appWidgetId: appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, ingredients);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String ingredients) {
        CharSequence widgetText;
        if (TextUtils.isEmpty(ingredients)) {
             widgetText = context.getString(R.string.appwidget_default_text);
        } else {
            widgetText = ingredients;
        }
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_list_widget);
        views.setTextViewText(R.id.appwidget_ingredients_list, widgetText);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.appwidget_ingredients_list, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, null);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

