package com.example.koller

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView


class TodayRecyclerAdapter (private var todayList : ArrayList<TodayData>) : RecyclerView.Adapter<TodayRecyclerAdapter.TodayViewHolder>(){

    private val item: Int = 0
    private val loading: Int = 1

    private var isLoadingAdded: Boolean = false
    private var retryPageLoad: Boolean = false

    private var errorMsg: String? = "Szar"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {


            val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_panel, parent, false)
            return TodayViewHolder(view)


    }

    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val currentItem = todayList[position]

        holder.iconLeft.setImageDrawable(currentItem.iconLeft)
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        if (currentItem.iconRight != null || currentItem.charRight != "") {
            holder.iconRight.visibility = VISIBLE
            holder.iconRight.background = currentItem.iconRight
            holder.iconRight.text = currentItem.charRight
        }
        if(!currentItem.read){
            holder.card_unread_overlay.cardElevation = MyApplication.convertDpToPixel(10, holder.itemView.context).toFloat()
            holder.root.cardElevation = MyApplication.convertDpToPixel(0, holder.itemView.context).toFloat()
        }
        else{
            holder.card_unread_overlay.cardElevation = MyApplication.convertDpToPixel(-10, holder.itemView.context).toFloat()
            holder.root.cardElevation = MyApplication.convertDpToPixel(10, holder.itemView.context).toFloat()
        }
    }

    override fun getItemCount(): Int {
        return todayList.size
    }

    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.notification_icon_start)
        val title : TextView = itemView.findViewById(R.id.comment_user_name)
        val description : TextView = itemView.findViewById(R.id.comment_text)
        val iconRight : TextView = itemView.findViewById(R.id.notification_icon_end)
        val root : MaterialCardView = itemView as MaterialCardView
        val card_unread_overlay : MaterialCardView = itemView.findViewById(R.id.notification_card_unread_overlay)

        init {
            itemView.setOnClickListener {

                val dialog = RoomOrderBottomSheet()
                dialog.show((itemView.context as FragmentActivity).supportFragmentManager, RoomOrderBottomSheet.TAG)
            }
        }
    }

}