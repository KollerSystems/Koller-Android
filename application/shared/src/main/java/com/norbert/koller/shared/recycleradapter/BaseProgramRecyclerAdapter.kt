package com.norbert.koller.shared.recycleradapter

import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.UserFragment
import com.google.android.material.imageview.ShapeableImageView
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData

class BaseProgramRecyclerAdapter(chipGroup: ChipGroup? = null, chips: List<Chip> = listOf()) : BaseRecycleAdapter(chipGroup, chips) {
    override fun createViewHolder(view: View): RecyclerView.ViewHolder {
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item : Any, position: Int) {


        val context = holder.itemView.context
        holder as ViewHolder

        item as BaseProgramData
        holder.iconLeft.setImageDrawable(
            AppCompatResources.getDrawable(
                context,
                R.drawable.person
            )
        )
        holder.title.text = item.Topic

        var description ="${item.TUID} • "



            for (i in item.Lesson..item.Lesson){

                val orderedNumber = MyApplication.orderSingleNumber(context, i.toString())

                if(i < item.Lesson-1-1) {

                    description += "${orderedNumber}, "
                }
                else if (i < item.Lesson-1){
                    description += "${orderedNumber} ${context.getString(R.string.and)} "
                }
                else{
                    description += "${orderedNumber} ${context.getString(R.string.lesson)}"
                }
            }


        holder.description.text = description



        holder.itemView.setOnClickListener {

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }


}