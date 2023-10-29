package com.norbert.koller.shared.recycleradapter

import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginRight
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.DevData


class DevRecyclerAdapter (private var devList : ArrayList<DevData>) : RecyclerView.Adapter<DevRecyclerAdapter.DevViewHolder>(){

    lateinit var lastView : View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_dev, parent, false)

        lastView = view
        return DevViewHolder(view)




    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.post {
            val random: Int = (0 + Math.random() * (((lastView.width + lastView.marginRight * 2) * devList.size) - 0)).toInt()
            recyclerView.layoutManager!!.scrollToPosition((Integer.MAX_VALUE / 2) - random)

            val handler = Handler()
            val runnable: Runnable = object : Runnable {
                override fun run() {
                    recyclerView.scrollBy(1, 0)
                    handler.postDelayed(this, 0)
                }
            }
            handler.postDelayed(runnable, 0)
        }
    }

    override fun onBindViewHolder(holder: DevViewHolder, position: Int) {
        val currentItem = devList[position  %  devList.size]

        MyApplication.roundRecyclerItemsHorizontallyWithHeaderImages(holder.itemView, holder.image, position, devList.size)

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