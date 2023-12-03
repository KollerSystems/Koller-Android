package com.norbert.koller.shared.recycleradapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customview.UserPreview
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.helpers.RecyclerViewHelper


class UserPreviewRecyclerAdapter (private var todayList : ArrayList<UserData>, var context : Context) : RecyclerView.Adapter<UserPreviewRecyclerAdapter.TodayViewHolder>(){

    //TODO: Befejezni

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_user_preview, parent, false)
        return TodayViewHolder(view)


    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val currentItem = todayList[position]

        RecyclerViewHelper.roundRecyclerItemsHorizontally(holder.itemView, null, position, todayList.size)

        holder.userBadge.setColorBasedOnClass(currentItem.Class?.Class)

        val nameParts : List<String> = currentItem.Name!!.split(" ")
        val name = nameParts[0] + " " + nameParts [1]

        holder.text.text = name.replace(" ", "\n")

        holder.itemView.setOnClickListener {

            if(currentItem.UID == UserData.instance.UID){
                MyApplication.openProfile.invoke(holder.itemView.context)
            }
            else{
                val bundle = Bundle()
                bundle.putInt("UID", currentItem.UID)
                val fragment = MyApplication.userFragment()
                fragment.arguments = bundle
                (context as MainActivity).changeFragment(fragment)
            }

        }
    }

    override fun getItemCount(): Int {
        return todayList.size
    }


    class ViewHolder(v: View?) : RecyclerView.ViewHolder(v!!) {
        private val customView: UserPreview?

        init {
            customView = v as UserPreview?
        }

        fun getCustomView(): UserPreview? {
            return customView
        }
    }


}