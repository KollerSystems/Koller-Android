package com.example.koller.recycleradapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.R
import com.example.koller.activities.FullScreenImageActivity
import com.example.koller.activities.MainActivity
import com.example.koller.data.StarData
import com.google.android.material.imageview.ShapeableImageView
import com.igreenwood.loupe.Loupe

class ImageRecyclerAdapter (private val imageList : ArrayList<Drawable>, val context : Context) : RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_image, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = imageList[position]
        holder.image.setImageDrawable(currentItem)

        holder.itemView.setOnClickListener{
            holder.image.transitionName = "smooth_transition"

            val intent = Intent(context, FullScreenImageActivity::class.java)
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (context as AppCompatActivity),
                holder.image,
                holder.image.transitionName
            ).toBundle()
            context.startActivity(intent, bundle)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image : ImageView = itemView.findViewById(R.id.image_image)
    }
}