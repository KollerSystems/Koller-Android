package com.example.shared.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.fragments.bottomsheet.ProfileBottomSheet
import com.example.shared.MyApplication
import com.example.shared.R
import com.example.shared.data.UserData
import com.example.shared.navigateWithDefaultAnimation
import com.google.android.material.card.MaterialCardView


class UserPreviewRecyclerAdapter (private var todayList : ArrayList<UserData>, var context : Context) : RecyclerView.Adapter<UserPreviewRecyclerAdapter.TodayViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_user_preview, parent, false)
        return TodayViewHolder(view)


    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val currentItem = todayList[position]

        MyApplication.roundRecyclerItemsHorizontally(context, holder.itemView, position, todayList.size)

        holder.text.text = currentItem.Name!!.replace(" ", "\n")

        holder.itemView.setOnClickListener {

            if(currentItem.ID == UserData.instance.ID){
                val dialog = ProfileBottomSheet()
                val fragmentManager = (context as AppCompatActivity)
                dialog.show(fragmentManager.supportFragmentManager, ProfileBottomSheet.TAG)
            }
            else{
                (context as AppCompatActivity).intent.putExtra("UserPreviewID", currentItem.ID)
                holder.itemView.findNavController().navigateWithDefaultAnimation(R.id.action_roomFragment_to_userFragment)
            }

        }
    }

    override fun getItemCount(): Int {
        return todayList.size
    }

    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image : ImageView = itemView.findViewById(R.id.image_pfp)
        val outline : MaterialCardView = itemView.findViewById(R.id.card_pfp_outline)
        val text : TextView = itemView.findViewById(R.id.text_name)
    }

}