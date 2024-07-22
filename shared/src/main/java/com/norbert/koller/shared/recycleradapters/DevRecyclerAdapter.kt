package com.norbert.koller.shared.recycleradapters

import android.animation.ValueAnimator
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginRight
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.DevData
import com.norbert.koller.shared.helpers.RecyclerViewHelper


class DevRecyclerAdapter (private var devList : ArrayList<DevData>) : RecyclerView.Adapter<DevRecyclerAdapter.DevViewHolder>(){

    lateinit var lastView : View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dev, parent, false)

        lastView = view
        return DevViewHolder(view)




    }

    private lateinit var autoScrollAnimator: ValueAnimator

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.post {
            val random: Int = (0 + Math.random() * (((lastView.width + lastView.marginRight * 2) * devList.size) - 0)).toInt()
            recyclerView.layoutManager!!.scrollToPosition((Integer.MAX_VALUE / 2) - random)

            autoScrollAnimator = ValueAnimator.ofInt(0, Int.MAX_VALUE)
            autoScrollAnimator.duration = Long.MAX_VALUE
            autoScrollAnimator.addUpdateListener {
                recyclerView.scrollBy(1, 0)
            }
            autoScrollAnimator.start()
        }
    }

    override fun onBindViewHolder(holder: DevViewHolder, position: Int) {
        val currentItem = devList[position  %  devList.size]

        RecyclerViewHelper.roundRecyclerItemsHorizontallyWithHeaderImages(holder.itemView, holder.image, position, devList.size)

        holder.name.text = currentItem.name
        holder.description.text = currentItem.description
        holder.image.setImageDrawable(currentItem.image)

        holder.itemView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(currentItem.webSite))
            holder.itemView.context.startActivity(browserIntent)

        }
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    class DevViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image : ImageView = itemView.findViewById(R.id.image)
        val name : TextView = itemView.findViewById(R.id.text_name)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }

}