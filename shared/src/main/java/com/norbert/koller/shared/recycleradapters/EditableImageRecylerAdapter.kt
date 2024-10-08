package com.norbert.koller.shared.recycleradapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.databinding.ItemEditableImageBinding
import com.norbert.koller.shared.managers.getAttributeColor
import com.stfalcon.imageviewer.StfalconImageViewer
import java.util.ArrayList
import java.util.Collections

class EditableImageRecyclerAdapter (private val imageList : ArrayList<Uri>, val context : Context, val imageLimit : Int, val textViewLimit : TextView) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    var draggable : Boolean = true
    var recyclerView : RecyclerView? = null
    var defaultTextColor : Int = 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        updateLimit(textViewLimit, imageList.size, imageLimit)
        this.recyclerView = recyclerView
        defaultTextColor = textViewLimit.currentTextColor

        var itemTouchHelper: ItemTouchHelper? = null
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,0) {
            var fromVeryFirstPosition : Int = -1
            var toPosition : Int = -1

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                val fromPosition = viewHolder.bindingAdapterPosition
                if(fromVeryFirstPosition == -1) fromVeryFirstPosition = fromPosition

                if(fromPosition == imageList.size) return false

                toPosition = target.bindingAdapterPosition

                if(toPosition > imageList.size -1){
                    toPosition = imageList.size -1
                }

                Collections.swap(imageList, fromPosition, toPosition)
                recyclerView.adapter!!.notifyItemMoved(fromPosition, toPosition)



                return true
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                when (actionState) {
                    ItemTouchHelper.ACTION_STATE_DRAG -> {
                        fromVeryFirstPosition = -1
                        toPosition = -1
                    }
                    ItemTouchHelper.ACTION_STATE_SWIPE ->
                        Log.d("DragTest","Start to swipe: $actionState")
                    ItemTouchHelper.ACTION_STATE_IDLE -> {
                        if(fromVeryFirstPosition == 0 || toPosition == 0){
                            //RecyclerView.adapter!!.notifyItemChanged(0)
                            recyclerView.adapter!!.notifyItemChanged(toPosition)
                            if(fromVeryFirstPosition < 2)
                                recyclerView.adapter!!.notifyItemChanged(fromVeryFirstPosition)
                            else
                                recyclerView.adapter!!.notifyItemChanged(1)
                        }
                    }
                }
            }



            override fun isLongPressDragEnabled(): Boolean {
                if(imageList.size==1) return false

                return draggable
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }
        }

        itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = null
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == itemCount-1){
            1
        }
        else{
            0
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            0 -> {
                val itemView = ItemEditableImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ImageViewHolder(itemView)
            }

            else -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.button_add_image, parent, false)
                ButtonViewHolder(itemView)
            }
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            0 -> {

                holder as ImageViewHolder

                if(position==0){
                    holder.itemBinding.mcardCoverOverlay.visibility = VISIBLE
                }
                else{
                    holder.itemBinding.mcardCoverOverlay.visibility = GONE
                }

                val currentItem = imageList[position]
                holder.itemBinding.image.setImageURI(currentItem)

                holder.itemBinding.mcard.setOnClickListener{

                    StfalconImageViewer.Builder(context, imageList){view,uri ->
                        view.setImageURI(uri)
                    }
                        .withStartPosition(holder.bindingAdapterPosition)
                        .withTransitionFrom(holder.itemBinding.image)
                        .show((context as AppCompatActivity).supportFragmentManager)
                }

                holder.itemBinding.btnRemove.setOnClickListener{
                    imageList.removeAt(holder.bindingAdapterPosition)
                    recyclerView!!.adapter!!.notifyItemRemoved(holder.bindingAdapterPosition)
                    if(position == 0 && imageList.size != 0) {
                        recyclerView!!.adapter!!.notifyItemChanged(0)
                    }
                    updateLimit(textViewLimit, imageList.size, imageLimit)
                }

                holder.itemBinding.btnRemove.setOnTouchListener { v, event ->
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN ->
                            draggable = false
                    }

                    v?.onTouchEvent(event) ?: true
                }

                holder.itemView.setOnTouchListener { v, event ->
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN ->
                            draggable = true
                    }

                    v?.onTouchEvent(event) ?: true
                }
            }
            else -> {
                holder.itemView.setOnClickListener{

                    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                    launcher.launch(galleryIntent)
                }

                holder.itemView.setOnTouchListener { v, event ->
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN ->
                            draggable = false
                    }

                    v?.onTouchEvent(event) ?: true
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    fun updateLimit(text : TextView, current : Int, max : Int){
        text.text = "${current}/${max}"
        if(current > max){
            text.setTextColor(context.getAttributeColor(com.google.android.material.R.attr.colorError))
        }
        else if (defaultTextColor != 0){
            text.setTextColor(defaultTextColor)
        }
    }

    private val launcher = (context as AppCompatActivity).registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val clipData: ClipData? = data?.clipData

            if (clipData != null) {
                val selectedCount = clipData.itemCount

                for (i in 0 until selectedCount) {
                    val uri: Uri? = clipData.getItemAt(i).uri
                    if (uri != null) {
                        imageList.add(uri)
                        recyclerView!!.adapter!!.notifyItemInserted(imageList.size - 1)
                        recyclerView!!.scrollToPosition(imageList.size)
                    }
                }
                updateLimit(textViewLimit, imageList.size, imageLimit)
            }
        }
    }

    override fun getItemCount(): Int {
        return imageList.size + 1
    }

    class ImageViewHolder(val itemBinding: ItemEditableImageBinding) : RecyclerView.ViewHolder(itemBinding.root)

    class ButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}