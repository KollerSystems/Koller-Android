package com.norbert.koller.shared.recycleradapters

import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.BaseProgramData

abstract class BaseProgramRecyclerAdapter() : BaseRecycleAdapter() {

    abstract fun onItemPress(fragmentActivity: FragmentActivity, item : BaseProgramData)

    override fun getViewType(): Int {
        return R.layout.view_user_item
    }

    override fun getDataTag(): String {
        return "base_program"
    }

    override fun createViewHolder(view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item : Any, position: Int) {


        val context = holder.itemView.context
        holder as ViewHolder

        item as BaseProgramData

        holder.title.text = item.topic

        var description ="${item.teacher!!.name} • ${ApplicationManager.createClassesText(context, item.lesson, item.length)}"

        holder.description.text = description



        holder.itemView.setOnClickListener {

            onItemPress((holder.itemView.context as FragmentActivity), item)


        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }


}