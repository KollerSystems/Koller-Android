package com.norbert.koller.shared.recycleradapter

import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.UserFragment
import com.google.android.material.imageview.ShapeableImageView
import com.norbert.koller.shared.BaseProgramDetailsFragment
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.fragments.bottomsheet.RoomOrderBottomSheet

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

        var description ="${item.TUID} • ${MyApplication.createClassesText(context, item.Lesson, item.Length)}"

        holder.description.text = description



        holder.itemView.setOnClickListener {

            val dialog = BaseProgramDetailsFragment(snapshot()[position] as BaseProgramData)
            dialog.show((holder.itemView.context as FragmentActivity).supportFragmentManager, RoomOrderBottomSheet.TAG)

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }


}