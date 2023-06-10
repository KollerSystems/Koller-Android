package com.example.koller.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.MyApplication
import com.example.koller.R
import com.example.koller.activities.navigateWithDefaultAnimation
import com.example.koller.data.TodayData
import com.example.koller.data.UserData
import com.example.koller.fragments.bottomsheet.ProfileBottomSheet
import com.google.android.material.imageview.ShapeableImageView


class UserRecycleAdapter (private var todayList : List<UserData>, var context : Context, var horizontal : Boolean = false) : RecyclerView.Adapter<UserRecycleAdapter.TodayViewHolder>(){

    private val item: Int = 0
    private val loading: Int = 1

    private var isLoadingAdded: Boolean = false
    private var retryPageLoad: Boolean = false
    private var viewToShow : Int = 0
    private var startAppearance : Int = 0
    private var endAppearance : Int = 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        if(!horizontal){
            viewToShow = R.layout.notification_panel
            startAppearance = R.style.overlayRoundedCardTop
            endAppearance = R.style.overlayRoundedCardBottom
        }
        else{
            viewToShow = R.layout.view_user_vertical
            startAppearance = R.style.overlayRoundedCardLeft
            endAppearance = R.style.overlayRoundedCardRight
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(viewToShow, parent, false)
        return TodayViewHolder(view)


    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val currentItem = todayList[position]

        MyApplication.roundRecyclerItemsX(context, holder.itemView, position, todayList.size, startAppearance, endAppearance)

        holder.title.text = currentItem.Name
        holder.description.text = MyApplication.createUserDescription(currentItem)

        holder.itemView.setOnClickListener {

            if(currentItem.ID == UserData.instance.ID){
                val dialog = ProfileBottomSheet()
                val fragmentManager = (context as AppCompatActivity)
                dialog.show(fragmentManager.supportFragmentManager, ProfileBottomSheet.TAG)
            }
            else{
                (context as AppCompatActivity).intent.putExtra("userID", currentItem.ID)
                holder.itemView.findNavController().navigateWithDefaultAnimation(R.id.action_usersFragment_to_userFragment)
            }

        }
    }

    override fun getItemCount(): Int {
        return todayList.size
    }

    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }

}