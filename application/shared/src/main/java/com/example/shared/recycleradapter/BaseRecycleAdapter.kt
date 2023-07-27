package com.example.shared.recycleradapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.MyApplication
import com.example.shared.R
import com.example.shared.BaseComparator
import com.example.shared.navigateWithDefaultAnimation
import com.google.android.material.imageview.ShapeableImageView

abstract class BaseRecycleAdapter() : PagingDataAdapter<Any, RecyclerView.ViewHolder>(BaseComparator){

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
                CustomViewHolder(view)
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

    abstract fun CustomViewHolder(view : View) : RecyclerView.ViewHolder



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var item : Any? = null

        if(!(state != STATE_NONE && position == itemCount -1)){
            item = getItem(position)
        }


        if(item != null) {
            when (getItemViewType(position)) {
                VIEW_TYPE_USER -> {

                    onBindCustomViewHolder(holder, item, position)


                    if(position == itemCount-1) {
                        lastMaxPosition = position
                    }

                    if(position == lastMaxPosition+1){
                        holder.itemView.post {
                            recyclerView.adapter!!.notifyItemChanged(lastMaxPosition, Object())
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

    abstract fun onBindCustomViewHolder(holder: RecyclerView.ViewHolder, item : Any, position: Int)


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
            is String -> VIEW_TYPE_SEPARATOR
            else -> VIEW_TYPE_USER
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