package com.norbert.koller.shared.recycleradapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.github.iielse.imageviewer.ImageViewerBuilder
import com.github.iielse.imageviewer.core.SimpleDataProvider
import com.norbert.koller.shared.R
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.stfalcon.imageviewer.StfalconImageViewer
import java.util.ArrayList

class ImageRecyclerAdapter (private val imageList : ArrayList<Drawable>, val context : Context) : RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_image, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        RecyclerViewHelper.roundRecyclerItemsHorizontally(holder.itemView, holder.image, position, itemCount)

        val currentItem = imageList[position]
        holder.image.setImageDrawable(currentItem)

        holder.itemView.setOnClickListener{

            StfalconImageViewer.Builder(context, imageList){view, drawable ->
                view.setImageDrawable(drawable)
            }
                .withStartPosition(position)
                .withTransitionFrom(holder.image)
                .show((context as AppCompatActivity).supportFragmentManager)
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