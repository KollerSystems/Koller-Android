package com.norbert.koller.teacher.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.customviews.UserView
import com.norbert.koller.shared.data.RoomOrderConditionsBase
import com.norbert.koller.shared.data.RoomOrderData
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.norbert.koller.shared.managers.DataStoreManager.Companion.getCurrentUserDataStore
import com.norbert.koller.shared.managers.DataStoreManager.Companion.userDataStore
import com.norbert.koller.teacher.R
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class RoomPresenceActivity : RoomsActivity() {

    var knowsTheLayout : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setTitle("Szoba jelenlét")

        val adapter = RoomPresenceViewPagerAdapter()

        getViewPager().adapter = adapter


        TabLayoutMediator(getTabLayout(), getViewPager()){tab,position->
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

        val data : ArrayList<RoomOrderConditionsBase> = RoomOrderData.instance[0].conditions
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

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_presence_vertical, parent, false)
        return RoomPresenceViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomPresenceViewHolder, position: Int) {

        RecyclerViewHelper.roundRecyclerItemsVerticallyGrid(holder.itemView, position, roomOrderConditionsData.size)

        holder.rbiwPfp.setOnClickListener{
            StfalconImageViewer.Builder(holder.itemView.context, listOf(holder.rbiwPfp.getImage().drawable)){view, drawable ->
                view.setImageDrawable(drawable)
            }
                .withStartPosition(0)
                .withTransitionFrom(holder.rbiwPfp.getImage())
                .show((holder.itemView.context as RoomPresenceActivity).supportFragmentManager)
        }

    }

    override fun getItemCount(): Int {
        return roomOrderConditionsData.size
    }

    class RoomPresenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val rbiwPfp : UserView = itemView.findViewById(R.id.rbiw_pfp)
    }

}