package com.example.koller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog

class CommentRecyclerAdapter (private val commentList : ArrayList<CommentData>) : RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_comment, parent, false)
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val currentItem = commentList[position]
        holder.userName.text = currentItem.userName
        holder.text.text = currentItem.text
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val userName : TextView = itemView.findViewById(R.id.comment_user_name)
        val pfp : ImageView = itemView.findViewById(R.id.comment_pfp)
        val text : TextView = itemView.findViewById(R.id.comment_text)

        init {

            val buttonMore : Button = itemView.findViewById(R.id.comment_more_options)

            buttonMore.setOnClickListener {


            }
        }
    }
}