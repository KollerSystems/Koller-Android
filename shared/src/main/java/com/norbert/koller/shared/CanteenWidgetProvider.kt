package com.norbert.koller.shared

import android.app.ListActivity
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.norbert.koller.shared.activities.LaunchActivity


class CanteenWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Perform this loop procedure for each widget that belongs to this
        // provider.
        appWidgetIds.forEach { appWidgetId ->
            // Create an Intent to launch ExampleActivity.

            val intent = Intent(context, ListViewWidgetService::class.java).apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
            }

            // Get the layout for the widget and attach an onClick listener to
            // the button.
            val views: RemoteViews = RemoteViews(context.packageName, R.layout.widget_canteen).apply {
                //setOnClickPendingIntent(R.id.root, pendingIntent)



                setRemoteAdapter(R.id.list_view, intent)
                setEmptyView(R.id.list_view, R.id.empty_view)


                val activityIntent = Intent(context, LaunchActivity::class.java)


                // Set the action for the intent.
                // When the user touches a particular view.
                activityIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                activityIntent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)))
                val pendingIntent = PendingIntent.getActivity(
                    context, appWidgetId,
                    activityIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                setPendingIntentTemplate(R.id.list_view, pendingIntent)

            }

            // Tell the AppWidgetManager to perform an update on the current
            // widget.

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

}