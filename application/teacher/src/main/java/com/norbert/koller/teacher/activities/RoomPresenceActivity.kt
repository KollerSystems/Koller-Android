package com.norbert.koller.teacher.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.RoundedBadgeImageView
import com.norbert.koller.shared.data.RoomOrderConditionsBase
import com.norbert.koller.shared.data.RoomOrderConditionsBoolean
import com.norbert.koller.shared.data.RoomOrderConditionsInt
import com.norbert.koller.shared.data.RoomOrderData
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragment
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.recycleradapter.RoomRateRecyclerAdapter
import com.stfalcon.imageviewer.StfalconImageViewer
import com.norbert.koller.shared.R as Rs


class RoomPresenceActivity : RoomsActivity() {


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        collapsingToolbarLayout.title = "Szoba jelenlét"

        val adapter = RoomPresenceViewPagerAdapter()

        viewPager.adapter = adapter


        TabLayoutMediator(tabLayout, viewPager){tab,position->
            tab.text = (position + 200).toString()
        }.attach()
    }
}

class RoomPresenceViewPagerAdapter() : RecyclerView.Adapter<RoomPresenceViewPagerAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_room_presence, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.recyclerView.layoutManager = GridLayoutManager(holder.itemView.context, 2)
        holder.recyclerView.setHasFixedSize(true)

        val data : ArrayList<RoomOrderConditionsBase> = RoomOrderData.instance[0].Conditions


        holder.recyclerView.adapter = RoomPresenceRecyclerAdapter(data)




    }

    override fun getItemCount(): Int {
        return 100
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val recyclerView : RecyclerView = itemView.findViewById(com.norbert.koller.shared.R.id.recycler_view)
    }


}

class RoomPresenceRecyclerAdapter (private var roomOrderConditionsData : ArrayList<RoomOrderConditionsBase>) :  RecyclerView.Adapter<RoomPresenceRecyclerAdapter.RoomPresenceViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomPresenceViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_user_presence_vertical, parent, false)
        return RoomPresenceViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomPresenceViewHolder, position: Int) {


        if (position == 0) {

            if(roomOrderConditionsData.size == 1) {

                MyApplication.roundCard(holder.itemView)
            }
            else if (roomOrderConditionsData.size == 2){
                MyApplication.roundCardLeft(holder.itemView)
            }
            else {
                MyApplication.roundCardTopLeft(holder.itemView)
            }

        }
        else if (position == 1) {

            if (roomOrderConditionsData.size == 2 || roomOrderConditionsData.size == 3){
                MyApplication.roundCardRight(holder.itemView)
            }
            else {
                MyApplication.roundCardTopRight(holder.itemView)
            }

        }
        else if (position == roomOrderConditionsData.size - 1) {

            if(position % 2 != 0) {
                MyApplication.roundCardBottomRight(holder.itemView)
            }
            else{
                MyApplication.roundCardBottomLeft(holder.itemView)
            }

        }
        else if (position == roomOrderConditionsData.size ) {

            if(position % 2 != 0) {
                MyApplication.roundCardBottomRight(holder.itemView)
            }
            else{
                MyApplication.roundCardBottom(holder.itemView)
            }

        }
        else{
            if(position % 2 != 0) {
                MyApplication.deroundCardWithLeftMargin(holder.itemView)
            }
            else{
                MyApplication.deroundCardWithRightMargin(holder.itemView)
            }
        }


        holder.rbiwPfp.card.setOnClickListener{
            StfalconImageViewer.Builder(holder.itemView.context, listOf(holder.rbiwPfp.image.drawable)) { view, drawable ->
                view.setImageDrawable(drawable)

            }
                .withStartPosition(0)
                .withTransitionFrom(holder.rbiwPfp.image)
                .show()
        }

    }

    override fun getItemCount(): Int {
        return roomOrderConditionsData.size + 1
    }

    class RoomPresenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val rbiwPfp : RoundedBadgeImageView = itemView.findViewById(R.id.rbiw_pfp)
    }

}