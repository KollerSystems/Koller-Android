package com.norbert.koller.shared.recycleradapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doBeforeTextChanged
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.helpers.RecyclerViewHelper

abstract class BaseRecycleAdapter(val chipGroup: ChipGroup? = null, val chips: List<Chip> = listOf()) : PagingDataAdapter<Any, RecyclerView.ViewHolder>(BaseComparator){

    private var lastMaxPosition : Int = -1
    lateinit var recyclerView: RecyclerView

    var state : Int = STATE_NONE


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView

        for (chip in chips){
            chip.doBeforeTextChanged{text, start, before, count ->
                refreshFully()
            }
        }


        chipGroup?.setOnCheckedStateChangeListener { chipGroup: ChipGroup, ints: MutableList<Int> ->
            refreshFully()
        }
    }

    var beingEmptied : Boolean = false

    fun refreshFully(){

        lastMaxPosition = -1

        beingEmptied = true
        Log.d("INFO", "START TO EMPTY")
        refresh()

        recyclerView.scrollToPosition(0)

        beingEmptied = false
        Log.d("INFO", "START TO LOAD")
        refresh()

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(super.getItemCount() == 0 && state == STATE_NONE){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_list_element_nothing_to_display, parent, false)
            return EmptyViewHolder(view)
        }
        else {

            return when (viewType) {
                VIEW_TYPE_USER -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.view_readable_item, parent, false)
                    createViewHolder(view)
                }

                VIEW_TYPE_SEPARATOR -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.view_date, parent, false)
                    DateViewHolder(view)
                }

                VIEW_TYPE_LOADING -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.view_loading, parent, false)
                    EmptyViewHolder(view)
                }

                else -> {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.shit, parent, false)
                    ErrorViewHolder(view)
                }
            }
        }
    }

    abstract fun createViewHolder(view : View) : RecyclerView.ViewHolder



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {



        if(state != STATE_NONE || super.getItemCount() != 0) {

            var item: Any? = null

            if (state == STATE_NONE || position != itemCount - 1) {
                item = getItem(position)
            }


            if (item != null) {
                when (getItemViewType(position)) {
                    VIEW_TYPE_USER -> {

                        onBindViewHolder(holder, item, position)


                        if (position == itemCount - 1) {
                            lastMaxPosition = position
                        }

                        if (position == lastMaxPosition + 1) {
                            holder.itemView.post {
                                recyclerView.adapter!!.notifyItemChanged(lastMaxPosition, Object())
                            }
                        }
                    }

                    VIEW_TYPE_SEPARATOR -> {

                        holder as DateViewHolder

                        item as String

                        holder.text.text = item


                    }
                }


                RecyclerViewHelper.roundRecyclerItemsVerticallyWithSeparator(holder.itemView, position, this)
            } else {
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
    }

    abstract fun onBindViewHolder(holder: RecyclerView.ViewHolder, item : Any, position: Int)




    override fun getItemCount(): Int {


        return if(state != STATE_NONE){
            super.getItemCount() + 1
        } else{
            if(super.getItemCount() == 0){
                1
            } else{
                super.getItemCount()
            }
        }

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

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val text : TextView = itemView.findViewById(R.id.text_view)
    }

    class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val button : Button = itemView.findViewById(R.id.button)
    }
}