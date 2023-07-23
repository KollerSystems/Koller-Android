package com.example.shared.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.shared.MyApplication
import com.example.shared.R
import com.example.shared.UserComparator
import com.example.shared.data.UserData
import com.example.shared.fragments.bottomsheet.ProfileBottomSheet
import com.example.shared.navigateWithDefaultAnimation
import com.google.android.material.imageview.ShapeableImageView


class UserRecycleAdapter (var horizontal : Boolean = false) : PagingDataAdapter<Any, RecyclerView.ViewHolder>(UserComparator){



    private val item: Int = 0
    private val loading: Int = 1

    private var isLoadingAdded: Boolean = false
    private var retryPageLoad: Boolean = false
    private var viewToShow : Int = 0
    private var startAppearance : Int = 0
    private var endAppearance : Int = 0
    private var paddingLeft : Int = 0
    private var paddingTop : Int = 0
    private var paddingRight : Int = 0
    private var paddingBottom : Int = 0


    var funny : Int = 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)


        if(!horizontal){
            viewToShow = R.layout.notification_panel
            startAppearance = R.style.overlayRoundedCardTop
            endAppearance = R.style.overlayRoundedCardBottom
            paddingLeft = 0
            paddingTop = recyclerView.context.resources.getDimensionPixelSize(R.dimen.card_margin)
            paddingRight = 0
            paddingBottom = recyclerView.context.resources.getDimensionPixelSize(R.dimen.card_margin)
        }
        else{
            viewToShow = R.layout.view_user_vertical
            startAppearance = R.style.overlayRoundedCardLeft
            endAppearance = R.style.overlayRoundedCardRight
            paddingLeft = recyclerView.context.resources.getDimensionPixelSize(R.dimen.card_margin)
            paddingTop = 0
            paddingRight = recyclerView.context.resources.getDimensionPixelSize(R.dimen.card_margin)
            paddingBottom = 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


        return if (viewType == VIEW_TYPE_USER) {
            val view = LayoutInflater.from(parent.context).inflate(viewToShow, parent, false)
            UserViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_date, parent, false)
            GateRecyclerAdapter.DateViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


            val item = getItem(position)


        val context = holder.itemView.context
        if(item != null) {

            if (holder is UserViewHolder) {

                item as UserData
                holder.iconLeft.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.person
                    )
                )
                holder.title.text = item.Name
                holder.description.text = MyApplication.createUserDescription(item)

                holder.itemView.setOnClickListener {

                    if (item.ID == UserData.instance.ID) {
                        MyApplication.openProfile(context)
                    } else {
                        com.example.shared.fragments.UserFragment.userToGet = item.ID
                        holder.itemView.findNavController()
                            .navigateWithDefaultAnimation(R.id.action_usersFragment_to_userFragment)
                    }

                }

            } else if (holder is GateRecyclerAdapter.DateViewHolder) {

                item as String

                holder.text.text = item

            }

            MyApplication.roundRecyclerItemsVerticallyWithSeparator(holder.itemView, position, this)
        }
    }


    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

    }



    override fun getItemViewType(position: Int): Int {

        return when (getItem(position)) {
            is UserData -> VIEW_TYPE_USER
            is String -> VIEW_TYPE_SEPARATOR
            else -> throw IllegalArgumentException("Unknown item type")
        }

    }

    companion object {
        const val VIEW_TYPE_USER = 0
        const val VIEW_TYPE_SEPARATOR = 1
        const val VIEW_TYPE_LOADING = 2
    }
}