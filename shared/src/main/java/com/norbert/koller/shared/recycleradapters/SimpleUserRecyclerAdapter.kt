package com.norbert.koller.shared.recycleradapters

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.PathInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.marginLeft
import androidx.core.view.setMargins
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.customviews.RoundedBadgeImageView
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragmentBase
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragmentStatic
import com.norbert.koller.shared.fragments.bottomsheet.MessageFragment
import com.norbert.koller.shared.fragments.bottomsheet.RoomOrderBottomSheet
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.getAttributeColor

class SimpleUserRecyclerAdapter(var userList : List<UserData>) : RecyclerView.Adapter<SimpleUserRecyclerAdapter.ViewHolder>() {

    lateinit var recyclerView : RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_user_item, parent, false)

        return ViewHolder(view)


    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]

        val context = holder.itemView.context

        RecyclerViewHelper.marginItemsVertically(holder.itemView, position, userList.size)

        holder.userBadge.setUser(currentItem)
        holder.title.text = currentItem.name
        holder.description.text = currentItem.createDescription()


        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val userBadge : RoundedBadgeImageView = itemView.findViewById(R.id.user_badge)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }

}