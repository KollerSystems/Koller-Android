package com.example.koller

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView


class TodayRecyclerAdapter (private var todayList : ArrayList<TodayData>, private val context: Context) : RecyclerView.Adapter<TodayRecyclerAdapter.TodayViewHolder>(){

    private val item: Int = 0
    private val loading: Int = 1

    private var isLoadingAdded: Boolean = false
    private var retryPageLoad: Boolean = false

    private var errorMsg: String? = "Szar"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_panel, parent, false)

        return TodayViewHolder(view, context)


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
        else{
            holder.iconRight.visibility = GONE
        }

        var text : String?
        var icon : Drawable?

        if(!currentItem.read){
            holder.card_unread_overlay.cardElevation = MyApplication.convertDpToPixel(25, holder.itemView.context).toFloat()
            holder.root.cardElevation = MyApplication.convertDpToPixel(0, holder.itemView.context).toFloat()
            holder.card_new_mark.visibility = VISIBLE
            text = context.getString(R.string.mark_as_unread)
            icon = AppCompatResources.getDrawable(holder.itemView.context, R.drawable.eye_off)
        }
        else{
            holder.card_unread_overlay.cardElevation = MyApplication.convertDpToPixel(-25, holder.itemView.context).toFloat()
            holder.root.cardElevation = MyApplication.convertDpToPixel(25, holder.itemView.context).toFloat()
            holder.card_new_mark.visibility = INVISIBLE
            text = context.getString(R.string.mark_as_read)
            icon = AppCompatResources.getDrawable(holder.itemView.context, R.drawable.eye)
        }

        holder.itemView.setOnClickListener {

            val dialog = RoomOrderBottomSheet()
            dialog.show((holder.itemView.context as FragmentActivity).supportFragmentManager, RoomOrderBottomSheet.TAG)

            currentItem.read = true
            notifyItemChanged(holder.bindingAdapterPosition)
        }

        holder.itemView.setOnLongClickListener{

            val fragmentManager = (context as AppCompatActivity)
            val dialog = ItemListDialogFragment()
            dialog.show(fragmentManager.supportFragmentManager, ItemListDialogFragment.TAG)


            val itemsArrayList = arrayListOf(
                ListItem(text, null, icon))
            holder.itemView.post(Runnable {
                dialog.recycleView.adapter = ListAdapter(itemsArrayList)

            })

            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return todayList.size
    }

    class TodayViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.notification_icon_start)
        val title : TextView = itemView.findViewById(R.id.comment_user_name)
        val description : TextView = itemView.findViewById(R.id.comment_text)
        val iconRight : TextView = itemView.findViewById(R.id.notification_icon_end)
        val root : MaterialCardView = itemView as MaterialCardView
        val card_unread_overlay : MaterialCardView = itemView.findViewById(R.id.notification_card_unread_overlay)
        val card_new_mark : MaterialCardView = itemView.findViewById(R.id.notification_card_new_mark)
    }

}