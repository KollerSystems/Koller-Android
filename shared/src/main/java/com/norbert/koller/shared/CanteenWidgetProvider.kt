package com.norbert.koller.shared

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.norbert.koller.shared.activities.LaunchActivity
import com.norbert.koller.shared.data.CanteenData
import java.util.Date

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
            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                context,
                0,
                Intent(context, LaunchActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Get the layout for the widget and attach an onClick listener to
            // the button.
            val views: RemoteViews = RemoteViews(context.packageName, R.layout.widget_canteen).apply {
                setOnClickPendingIntent(R.id.root, pendingIntent)
                val canteenDataArrayList = arrayListOf(
                    CanteenData(1,0, "6:00 - 8:45", "Száraz kenyér", Date()),
                    CanteenData(1,1, "13:00 - 15:45", "Zöccség leves és Polipos genyó", Date()),
                    CanteenData(1,2, "19:15 - 19:45", "Száraz kenyér", Date())
                )
                val adapter = CanteenListAdapter(context, canteenDataArrayList)

            }

            // Tell the AppWidgetManager to perform an update on the current
            // widget.
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

}