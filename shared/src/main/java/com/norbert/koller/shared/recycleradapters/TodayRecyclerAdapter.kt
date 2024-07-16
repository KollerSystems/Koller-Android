package com.norbert.koller.shared.recycleradapters
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.animation.PathInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.marginLeft
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.fragments.bottomsheet.MessageFragment
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragmentBase
import com.norbert.koller.shared.fragments.bottomsheet.RoomOrderBottomSheet
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragmentStatic
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.helpers.RecyclerViewHelper


class TodayRecyclerAdapter (private var todayList : ArrayList<TodayData>) : RecyclerView.Adapter<TodayRecyclerAdapter.TodayViewHolder>(){


    lateinit var recyclerView : RecyclerView

    var smallerSizeDp : Int = 0
    var smallerMarginDp : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_readable_item, parent, false)

        return TodayViewHolder(view)


    }


    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        val currentItem = todayList[position]

        val context = holder.itemView.context

        RecyclerViewHelper.roundRecyclerItemsVertically(holder.itemView, holder.itemView, position, todayList.size)

        holder.cardUnreadOverlay.visibility = VISIBLE
        holder.iconLeft.setImageDrawable(currentItem.iconLeft)
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        if (currentItem.iconRight != null || currentItem.charRight != "") {
            holder.iconRight.visibility = VISIBLE
            if(currentItem.iconRight != null) {
                holder.iconRight.background = currentItem.iconRight
                val marginEnd = context.resources.getDimensionPixelSize(R.dimen.text_container_margin)
                val layoutParams = holder.iconRight.layoutParams as MarginLayoutParams
                layoutParams.height = ApplicationManager.convertDpToPixel(35, context)
                layoutParams.width = ApplicationManager.convertDpToPixel(35, context)
                layoutParams.marginEnd = marginEnd
                holder.iconRight.layoutParams = layoutParams
            }
            holder.iconRight.text = currentItem.charRight
        }
        else{
            holder.iconRight.visibility = GONE
        }

        val text : String?
        val icon : Drawable?

        holder.cardUnreadOverlay.translationX = 0f
        holder.cardNewMark.translationX = 0f

        if(!currentItem.read){
            holder.cardUnreadOverlay.setCardBackgroundColor(context.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainerHigh))
            holder.root.setCardBackgroundColor(context.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainerLow))
            holder.cardNewMark.visibility = VISIBLE
            text = context.getString(R.string.mark_as_read)
            icon = AppCompatResources.getDrawable(holder.itemView.context, R.drawable.eye)

            holder.cardNewMark.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val params = (holder.cardNewMark as ViewGroup).layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(ApplicationManager.convertDpToPixel(5, holder.itemView.context))
            holder.cardNewMark.layoutParams = params
            holder.cardNewMark.getChildAt(0).alpha = 1f
            
            if(waitingDone){
                waitToHideText(holder)
            }

        }
        else{
            holder.cardUnreadOverlay.setCardBackgroundColor(context.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainerLow))
            holder.root.setCardBackgroundColor(context.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainerHigh))
            holder.cardNewMark.visibility = INVISIBLE
            text = context.getString(R.string.mark_as_unread)
            icon = AppCompatResources.getDrawable(holder.itemView.context, R.drawable.eye_off)
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
            val dialog = ItemListDialogFragmentStatic(arrayListOf(
                ListItem(text, null, icon, null, {
                    currentItem.read = !currentItem.read
                    notifyItemChanged(holder.bindingAdapterPosition)
                })))
            dialog.show(fragmentManager.supportFragmentManager, ItemListDialogFragmentBase.TAG)






            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return todayList.size
    }

    fun changeSizeAnimation(view: ViewGroup, textView: View, newWidth: Int, newHeight: Int, newMargin : Int) {

        val cubicInterpolator = PathInterpolator(0.65f, 0f, 0.35f, 1f)

        val widthAnimator = ValueAnimator.ofInt(
            view.width,
            newWidth
        )

        widthAnimator.addUpdateListener { animation ->
            val params = view.layoutParams
            params.width = animation.animatedValue as Int
            view.layoutParams = params
        }

        widthAnimator.interpolator = cubicInterpolator

        val heightAnimator = ValueAnimator.ofInt(
            view.height,
            newHeight
        )

        heightAnimator.addUpdateListener { animation ->
            val params = view.layoutParams
            params.height = animation.animatedValue as Int
            view.layoutParams = params
        }

        heightAnimator.interpolator = cubicInterpolator

        val marginAnimator = ValueAnimator.ofInt(
            view.marginLeft,
            newMargin
        )

        marginAnimator.addUpdateListener { animation ->
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(animation.animatedValue as Int)
            view.layoutParams = params
        }

        marginAnimator.interpolator = cubicInterpolator

        val alphaAnimator = ValueAnimator.ofFloat(
            textView.alpha,
            0f
        )

        alphaAnimator.addUpdateListener { animation ->
            textView.alpha = animation.animatedValue as Float
        }

        alphaAnimator.interpolator = cubicInterpolator

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(widthAnimator, heightAnimator, marginAnimator, alphaAnimator)

        animatorSet.duration = 250

        animatorSet.start()
    }

    var waitingDone : Boolean = false


    fun waitToHideAllText(recyclerView : RecyclerView){


        recyclerView.postDelayed({

            waitingDone = true
            for (i in 0 until recyclerView.childCount) {
                val view: View? = recyclerView.getChildAt(i)
                if (view != null) {
                    val card : ViewGroup = view.findViewById(R.id.notification_card_new_mark)
                    changeSizeAnimation(card, card.getChildAt(0), smallerSizeDp, smallerSizeDp, smallerMarginDp)
                }
            }

        }, 1000*3)
    }

    fun waitToHideText(holder: TodayViewHolder){


        holder.handler.removeCallbacksAndMessages(null)
        holder.handler.postDelayed({

            val child = recyclerView.getChildAt(holder.absoluteAdapterPosition)

            if(child != null) {
                val card: ViewGroup = child.findViewById(R.id.notification_card_new_mark)
                changeSizeAnimation(card, card.getChildAt(0), smallerSizeDp, smallerSizeDp, smallerMarginDp)
            }

        }, 1000*3)
    }



    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {

        this.recyclerView = recyclerView

        super.onAttachedToRecyclerView(recyclerView)

        smallerSizeDp = ApplicationManager.convertDpToPixel(15,recyclerView.context)
        smallerMarginDp = ApplicationManager.convertDpToPixel(10,recyclerView.context)

        waitToHideAllText(recyclerView)

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


    class TodayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val handler : Handler = Handler(Looper.myLooper()!!)
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
        val iconRight : TextView = itemView.findViewById(R.id.notification_icon_end)
        val root : MaterialCardView = itemView as MaterialCardView
        val cardUnreadOverlay : MaterialCardView = itemView.findViewById(R.id.notification_card_unread_overlay)
        val cardNewMark : MaterialCardView = itemView.findViewById(R.id.notification_card_new_mark)


    }

}