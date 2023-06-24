package com.example.koller.recycleradapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.MyApplication
import com.example.koller.R
import com.example.koller.data.TodayData
import com.example.koller.fragments.MessageFragment
import com.example.koller.fragments.bottomsheet.ItemListDialogFragment
import com.example.koller.fragments.bottomsheet.RoomOrderBottomSheet
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import java.security.AccessController.getContext


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

        MyApplication.roundRecyclerItemsVertically(context, holder.itemView, holder.card_unread_overlay, position, todayList.size)

        holder.card_unread_overlay.visibility = VISIBLE
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

        holder.card_unread_overlay.translationX = 0f
        holder.card_new_mark.translationX = 0f

        if(!currentItem.read){
            holder.card_unread_overlay.cardElevation = MyApplication.convertDpToPixel(24, holder.itemView.context
            ).toFloat()
            holder.root.cardElevation = MyApplication.convertDpToPixel(1, holder.itemView.context).toFloat()
            holder.card_new_mark.visibility = VISIBLE
            text = context.getString(R.string.mark_as_read)
            icon = AppCompatResources.getDrawable(holder.itemView.context, R.drawable.eye_off)
        }
        else{
            holder.card_unread_overlay.cardElevation = MyApplication.convertDpToPixel(
                -24,
                holder.itemView.context
            ).toFloat()
            holder.root.cardElevation = MyApplication.convertDpToPixel(25, holder.itemView.context).toFloat()
            holder.card_new_mark.visibility = INVISIBLE
            text = context.getString(R.string.mark_as_unread)
            icon = AppCompatResources.getDrawable(holder.itemView.context, R.drawable.eye)
        }

        holder.itemView.setOnClickListener {

            if(currentItem.title == context.getString(R.string.room_order)){
                val dialog = RoomOrderBottomSheet()
                dialog.show((holder.itemView.context as FragmentActivity).supportFragmentManager, RoomOrderBottomSheet.TAG)
            }else{
                val dialog = MessageFragment()
                dialog.show((holder.itemView.context as FragmentActivity).supportFragmentManager, MessageFragment.TAG)
            }

            currentItem.read = true
            notifyItemChanged(holder.bindingAdapterPosition)
        }

        holder.itemView.setOnLongClickListener{

            val fragmentManager = (context as AppCompatActivity)
            val dialog = ItemListDialogFragment()
            dialog.show(fragmentManager.supportFragmentManager, ItemListDialogFragment.TAG)


            val itemsArrayList = arrayListOf(
                ListItem(text, null, icon)
            )
            holder.itemView.post(Runnable {
                dialog.recycleView.adapter = ListAdapter(itemsArrayList)

            })

            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return todayList.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        var itemTouchHelper: ItemTouchHelper? = null
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onChildDraw(
                c: Canvas, recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int,
                isCurrentlyActive: Boolean
            ) {


                super.onChildDraw(c, recyclerView, viewHolder, 0f, 0f, actionState, isCurrentlyActive)
                val child: View = viewHolder.itemView.findViewById(R.id.notification_card_unread_overlay)
                child.translationX = dX

                val mark: View = viewHolder.itemView.findViewById(R.id.notification_card_new_mark)
                mark.translationX = dX

            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {



                todayList[viewHolder.bindingAdapterPosition].read = !todayList[viewHolder.bindingAdapterPosition].read
                notifyItemChanged(viewHolder.bindingAdapterPosition)


            }



        }
        itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    class TodayViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
        val iconRight : TextView = itemView.findViewById(R.id.notification_icon_end)
        val root : MaterialCardView = itemView as MaterialCardView
        val card_unread_overlay : MaterialCardView = itemView.findViewById(R.id.notification_card_unread_overlay)
        val card_new_mark : MaterialCardView = itemView.findViewById(R.id.notification_card_new_mark)
    }

}