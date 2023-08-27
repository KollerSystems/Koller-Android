package com.norbert.koller.shared.recycleradapter

import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.UserFragment
import com.google.android.material.imageview.ShapeableImageView

class UserRecyclerAdapter : BaseRecycleAdapter() {
    override fun serverErrorPopup(view: View): RecyclerView.ViewHolder {
        return UserViewHolder(view)
    }

    override fun onBindserverErrorPopup(holder: RecyclerView.ViewHolder, item : Any, position: Int) {


        val context = holder.itemView.context
        holder as UserViewHolder

        item as UserData
        holder.iconLeft.setImageDrawable(
            AppCompatResources.getDrawable(
                context,
                R.drawable.person
            )
        )
        holder.title.text = item.Name
        holder.description.text = MyApplication.createUserDescription(item)

        holder.itemView.setOnClickListener {

            if (item.UID == UserData.instance.UID) {
                MyApplication.openProfile(context)
            } else {
                UserFragment.open(context, item.UID)
            }

        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }


}