package com.example.shared.recycleradapter

import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.MyApplication
import com.example.shared.R
import com.example.shared.data.UserData
import com.example.shared.navigateWithDefaultAnimation
import com.google.android.material.imageview.ShapeableImageView

class UserRecyclerAdapter : BaseRecycleAdapter() {
    override fun CustomViewHolder(view: View): RecyclerView.ViewHolder {
        return UserViewHolder(view)
    }

    override fun onBindCustomViewHolder(holder: RecyclerView.ViewHolder, item : Any, position: Int) {


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

            if (item.ID == UserData.instance.ID) {
                MyApplication.openProfile(context)
            } else {
                com.example.shared.fragments.UserFragment.userToGet = item.ID
                holder.itemView.findNavController()
                    .navigateWithDefaultAnimation(R.id.action_usersFragment_to_userFragment)
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