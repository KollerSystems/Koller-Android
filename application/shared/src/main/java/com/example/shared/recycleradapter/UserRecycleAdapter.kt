package com.example.shared.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
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
import java.util.Objects

class UserRecycleAdapter() : PagingDataAdapter<Any, RecyclerView.ViewHolder>(UserComparator){


    public var onRetryClick: (() -> Unit)? = null

    var lastMaxPosition : Int = -1
    lateinit var recyclerView: RecyclerView

    public var state : Int = STATE_NONE


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            VIEW_TYPE_USER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_panel, parent, false)
                UserViewHolder(view)
            }
            VIEW_TYPE_SEPARATOR -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_date, parent, false)
                GateRecyclerAdapter.DateViewHolder(view)
            }
            VIEW_TYPE_LOADING -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_loading, parent, false)
                LoadingViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.shit, parent, false)
                ErrorViewHolder(view)
            }
        }
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var item : Any? = null

        if(!(state != STATE_NONE && position == itemCount -1)){
            item = getItem(position)
        }

        val context = holder.itemView.context
        if(item != null) {
            when (getItemViewType(position)) {
                VIEW_TYPE_USER -> {

                    holder as UserViewHolder
                    if(position == itemCount-1) {
                        lastMaxPosition = position
                    }

                    if(position == lastMaxPosition+1){
                        holder.itemView.post {
                            recyclerView.adapter!!.notifyItemChanged(lastMaxPosition, Object())
                        }
                    }


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

                }
                VIEW_TYPE_SEPARATOR -> {

                    holder as GateRecyclerAdapter.DateViewHolder

                    item as String

                    holder.text.text = item


                }
            }


            MyApplication.roundRecyclerItemsVerticallyWithSeparator(holder.itemView, position, this)
        }
        else{
            when (getItemViewType(position)) {
                VIEW_TYPE_LOADING -> {
                    // Töltő ikon megjelenítése
                }
                VIEW_TYPE_RETRY -> {
                    // Hibaüzenet megjelenítése és újra próbálkozás gomb eseménykezelése
                    val retryViewHolder = holder as ErrorViewHolder
                    retryViewHolder.button.setOnClickListener {
                        retry()
                    }
                }
            }
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

    class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val button : Button = itemView.findViewById(R.id.button)
    }

    override fun getItemCount(): Int {

        if(state != STATE_NONE){
            return super.getItemCount() + 1
        }

        return super.getItemCount()
    }


    override fun getItemViewType(position: Int): Int {

        if(position == itemCount-1) {
            if (state == STATE_LOADING) {
                return VIEW_TYPE_LOADING
            }
            else if(state == STATE_ERROR){
                return VIEW_TYPE_RETRY
            }
        }

        return when (getItem(position)){
            is UserData -> VIEW_TYPE_USER
            is String -> VIEW_TYPE_SEPARATOR
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }


    companion object {
        const val STATE_NONE = 0
        const val STATE_LOADING = 1
        const val STATE_ERROR = 2


        const val VIEW_TYPE_USER = 0
        const val VIEW_TYPE_SEPARATOR = 1
        const val VIEW_TYPE_LOADING = 2
        const val VIEW_TYPE_RETRY = 3
    }
}