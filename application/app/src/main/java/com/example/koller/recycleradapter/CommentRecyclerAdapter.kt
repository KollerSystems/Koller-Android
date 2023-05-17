package com.example.koller.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.fragments.bottomsheet.ItemListDialogFragment
import com.example.koller.R
import com.example.koller.data.CommentData


class CommentRecyclerAdapter (private val commentList : ArrayList<CommentData>, private val context: Context) : RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_comment, parent, false)
        return CommentViewHolder(itemView,context)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val currentItem = commentList[position]
        holder.userName.text = currentItem.userName
        holder.text.text = currentItem.text
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    class CommentViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView)
    {
        val userName : TextView = itemView.findViewById(R.id.comment_user_name)
        val pfp : ImageView = itemView.findViewById(R.id.comment_pfp)
        val text : TextView = itemView.findViewById(R.id.comment_text)

        init {

            val buttonMore : Button = itemView.findViewById(R.id.comment_more_options)

            itemView.setOnLongClickListener{

                buttonMore.callOnClick()

                return@setOnLongClickListener true
            }

            buttonMore.setOnClickListener {

                val fragmentManager = (context as AppCompatActivity)
                val dialog = ItemListDialogFragment()
                dialog.show(fragmentManager.supportFragmentManager, ItemListDialogFragment.TAG)
                val itemsArrayList = arrayListOf(
                    ListItem(context.getString(R.string.report), null, AppCompatResources.getDrawable(itemView.context, R.drawable.flag))
                )
                buttonMore.post(Runnable {
                    dialog.recycleView.adapter = ListAdapter(itemsArrayList)
                })



            }
        }
    }
}