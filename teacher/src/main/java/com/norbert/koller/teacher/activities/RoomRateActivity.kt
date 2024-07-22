package com.norbert.koller.teacher.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.data.RoomOrderConditionsBase
import com.norbert.koller.shared.data.RoomOrderData
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.recycleradapters.RoomRateRecyclerAdapter


class RoomRateActivity : RoomsActivity() {


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        collapsingToolbarLayout.title = getString(R.string.room_rate)

        val adapter = RoomRateViewPagerAdapter()

        viewPager.adapter = adapter


        TabLayoutMediator(tabLayout, viewPager){tab,position->
            tab.text = (position + 200).toString()
        }.attach()
    }
}

class RoomRateViewPagerAdapter() : RecyclerView.Adapter<RoomRateViewPagerAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_room_rate, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //val currentItem = roomOrderConditionsData[position]




        holder.recyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)


        val data : ArrayList<RoomOrderConditionsBase> = RoomOrderData.instance[0].Conditions


        holder.recyclerView.adapter = RoomRateRecyclerAdapter(data)


        holder.lyContent.post {


            for (i in 0 until holder.chipGroup.childCount) {
                val child = holder.chipGroup.getChildAt(i) as Chip

                Log.d("TEST", (holder.chipGroup.width).toString())
                Log.d("TEST", (holder.chipGroup.width / holder.chipGroup.childCount).toString())
                Log.d("TEST", (child.layoutParams.width).toString())
                child.layoutParams = LayoutParams(holder.chipGroup.width / holder.chipGroup.childCount, holder.chipGroup.height)
                Log.d("TEST", (child.layoutParams.width).toString())
            }

            holder.lyContent.setPadding(0, 0, 0, holder.lyFooter.height)
        }



    }

    override fun getItemCount(): Int {
        return 100
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val recyclerView : RecyclerView = itemView.findViewById(com.norbert.koller.shared.R.id.recycler_view)
        val lyContent : View = itemView.findViewById(R.id.ly_content)

        val lyFooter : View = itemView.findViewById(R.id.ly_fixed_footer)


        val chipGroup : ChipGroup = itemView.findViewById(R.id.chip_group)
    }


}