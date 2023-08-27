package com.norbert.koller.teacher.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.data.RoomOrderConditionsBase
import com.norbert.koller.shared.data.RoomOrderData
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.recycleradapter.RoomRateRecyclerAdapter

class RoomRateActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_rate)

        MyApplication.setToolbarToBottomViewColor(findViewById(R.id.bottom_view), window)

        val recyclerView : RecyclerView = findViewById(com.norbert.koller.shared.R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this@RoomRateActivity)
        recyclerView.setHasFixedSize(true)

        val data : ArrayList<RoomOrderConditionsBase> = RoomOrderData.instance[0].Conditions


        recyclerView.adapter = RoomRateRecyclerAdapter(data)

        val lyContent : LinearLayout = findViewById(R.id.ly_content)

        val lyFooter : LinearLayout = findViewById(R.id.ly_fixed_footer)

        lyContent.post{
            lyContent.setPadding(0,0,0,lyFooter.height)
        }
    }
}