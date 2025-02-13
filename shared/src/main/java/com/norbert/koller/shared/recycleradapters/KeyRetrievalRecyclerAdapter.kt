package com.norbert.koller.shared.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.UserView
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.databinding.ItemBinding
import com.norbert.koller.shared.databinding.ItemReadableBinding
import com.norbert.koller.shared.databinding.ItemUserBinding
import com.norbert.koller.shared.helpers.RecyclerViewHelper

class KeyRetrievalRecyclerAdapter() : ApiRecyclerAdapter() {

    override fun setItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return RecyclerViewHelper.ItemViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: BaseData, position: Int) {


        holder as RecyclerViewHelper.ItemViewHolder

        item as UserData

        val titles = listOf("Bevásárlás", "Fodrász", "Verseny", "Kiállítás", "Múzeum")
        val descriptions = listOf("16:00 - 18:00 • Barkóczi István", "17:15 - 18:00 • Kis Gazsi", "18:00 - 21:00 • Barkóczi István")
        holder.itemBinding.textTitle.text = titles.random()
        holder.itemBinding.textDescription.text = descriptions.random()
        holder.itemBinding.imageIcon.setImageResource(R.drawable.wave)

        holder.itemView.setOnClickListener {

        }
    }
}