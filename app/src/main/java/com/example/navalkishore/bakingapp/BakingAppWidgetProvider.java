package com.example.navalkishore.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.navalkishore.bakingapp.activities.MainActivity;
import com.example.navalkishore.bakingapp.model.Ingredient;
import com.example.navalkishore.bakingapp.model.Steps;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {
    private static String ingredients;
    private static String mRecipeLabel;

     static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
        Intent intent = new Intent(context, MainActivity.class);
        views.setTextViewText(R.id.widget_label_receipe,mRecipeLabel);
        views.setTextViewText(R.id.appwidget_text,ingredients);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.widget_master_layout,pendingIntent);



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        update(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void setIngredentList(String inputIngredients, String receipeLable) {
        ingredients = inputIngredients;
        mRecipeLabel=receipeLable;

    }

    public static void updateMyWidgets(Context context) {
        AppWidgetManager man = AppWidgetManager.getInstance(context);
        int[] ids = man.getAppWidgetIds(
                new ComponentName(context, BakingAppWidgetProvider.class));
        Intent updateIntent = new Intent();
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra("widget_key", ids);
        context.sendBroadcast(updateIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra("widget_key")) {
            int[] ids = intent.getExtras().getIntArray("widget_key");
            this.onUpdate(context, AppWidgetManager.getInstance(context), ids);
        } else super.onReceive(context, intent);
    }


    public void update(Context context, AppWidgetManager manager, int[] ids) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
        for (int widgetId : ids) {

            views.setTextViewText(R.id.widget_label_receipe,mRecipeLabel);
            views.setTextViewText(R.id.appwidget_text,ingredients);
            manager.updateAppWidget(widgetId, views);
        }
    }
}

