package com.norbert.koller.shared.recycleradapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.fragments.bottomsheet.list.ListBsdfFragment
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.CommentData
import com.norbert.koller.shared.data.ListCardItem
import com.norbert.koller.shared.fragments.bottomsheet.list.ListCardStaticBsdfFragment


class CommentRecyclerAdapter (private val commentList : ArrayList<CommentData>, private val context: Context) : RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
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
        val userName : TextView = itemView.findViewById(R.id.text_title)
        val pfp : ImageView = itemView.findViewById(R.id.comment_pfp)
        val text : TextView = itemView.findViewById(R.id.text_description)

        init {

            val buttonMore : Button = itemView.findViewById(R.id.comment_more_options)

            itemView.setOnLongClickListener{

                buttonMore.callOnClick()

                return@setOnLongClickListener true
            }

            buttonMore.setOnClickListener {

                val fragmentManager = (context as AppCompatActivity)
                ListCardStaticBsdfFragment().setup(context, arrayListOf(
                    ListCardItem(context.getString(R.string.report), null, AppCompatResources.getDrawable(itemView.context, R.drawable.flag))
                )).show(fragmentManager.supportFragmentManager, ListBsdfFragment.TAG)




            }
        }
    }
}