package com.norbert.koller.shared.widgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.LaunchActivity

class NowWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Perform this loop procedure for each widget that belongs to this
        // provider.
        appWidgetIds.forEach { appWidgetId ->
            // Create an Intent to launch ExampleActivity.
            val activityIntent = Intent(context, LaunchActivity::class.java)
            activityIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            activityIntent.putExtra(WidgetHelper.WIDGET_TAG_KEY, TAG)
            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                context,
                appWidgetId,
                activityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )


            // Get the layout for the widget and attach an onClick listener to
            // the button.
            val views: RemoteViews = RemoteViews(context.packageName, R.layout.widget_now).apply {
                setOnClickPendingIntent(R.id.root, pendingIntent)
            }

            // Tell the AppWidgetManager to perform an update on the current
            // widget.
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    companion object{
        val TAG = "now"
    }

}