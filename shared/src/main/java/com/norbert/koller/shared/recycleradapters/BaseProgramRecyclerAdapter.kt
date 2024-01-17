package com.norbert.koller.shared.recycleradapters

import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.google.android.material.imageview.ShapeableImageView
import com.norbert.koller.shared.fragments.bottomsheet.BaseProgramDetailsFragment
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.fragments.bottomsheet.RoomOrderBottomSheet

class BaseProgramRecyclerAdapter(chipGroupSort: ChipGroup? = null, chipGroupFilter: ChipGroup? = null) : BaseRecycleAdapter(chipGroupSort, chipGroupFilter) {
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

        var description ="${item.tuid} • ${ApplicationManager.createClassesText(context, item.lesson, item.length)}"

        holder.description.text = description



        holder.itemView.setOnClickListener {

            val dialog = BaseProgramDetailsFragment(snapshot()[position] as BaseProgramData)
            dialog.show((holder.itemView.context as FragmentActivity).supportFragmentManager, RoomOrderBottomSheet.TAG)

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }


}