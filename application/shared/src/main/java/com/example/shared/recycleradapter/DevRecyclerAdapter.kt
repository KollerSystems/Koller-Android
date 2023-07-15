package com.example.shared.recycleradapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.fragments.bottomsheet.ProfileBottomSheet
import com.example.shared.MyApplication
import com.example.shared.R
import com.example.shared.data.DevData
import com.example.shared.data.UserData
import com.example.shared.navigateWithDefaultAnimation
import com.google.android.material.card.MaterialCardView


class DevRecyclerAdapter (private var devList : ArrayList<DevData>) : RecyclerView.Adapter<DevRecyclerAdapter.DevViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_dev, parent, false)
        return DevViewHolder(view)


    }

    override fun onBindViewHolder(holder: DevViewHolder, position: Int) {
        val currentItem = devList[position]

        MyApplication.roundRecyclerItemsHorizontallyWithHeaderImages(holder.itemView.context, holder.itemView, holder.image, position, devList.size)

        holder.name.text = currentItem.name
        holder.description.text = currentItem.description
        holder.image.setImageDrawable(currentItem.image)

        holder.itemView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(currentItem.webSite))
            holder.itemView.context.startActivity(browserIntent)

        }
    }

    override fun getItemCount(): Int {
        return devList.size
    }

    class DevViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image : ImageView = itemView.findViewById(R.id.image)
        val name : TextView = itemView.findViewById(R.id.text_name)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }

}