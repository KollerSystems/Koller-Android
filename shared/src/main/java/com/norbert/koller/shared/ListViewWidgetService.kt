package com.norbert.koller.shared

import android.content.Intent
import android.widget.RemoteViewsService
import com.norbert.koller.shared.data.CanteenData
import java.util.Date


class ListViewWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        val canteenDataArrayList = arrayListOf(
            CanteenData(1,1, "13:00 - 15:45", "Zöccség leves és Polipos genyó", Date(1724536800000)),
            CanteenData(2,2, "19:15 - 19:45", "Száraz kenyér", Date(1724536800000)),
            CanteenData(3,0, "6:00 - 8:45", "Száraz kenyér", Date(1724623200000)),
            CanteenData(4,1, "13:00 - 15:45", "Zöccség leves és Polipos genyó", Date(1724623200000)),
            CanteenData(5,2, "19:15 - 19:45", "Száraz kenyér", Date(1724623200000)),
        )
        return CanteenListAdapter(this.applicationContext, canteenDataArrayList)
    }

}