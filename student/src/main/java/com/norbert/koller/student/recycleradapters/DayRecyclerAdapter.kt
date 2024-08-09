package com.norbert.koller.student.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.bottomsheet.LessonsBsdFragment
import com.norbert.koller.shared.recycleradapters.Comparator
import com.norbert.koller.student.R
import com.norbert.koller.student.databinding.FragmentDayBinding
import java.util.Date

class DayRecyclerAdapter : PagingDataAdapter<Any, DayRecyclerAdapter.ViewHolder>(Comparator){

    lateinit var binding : FragmentDayBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = FragmentDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding.cardLessons.setOnClickListener{
            val lessons1 = arrayListOf(
                ProgramData(2,0,"Online Marketing", 7, UserData(name = "Kis Gazsi"), 9, Date(24432), 0, 1),
                ProgramData(7,2,"Offline Marketing", 5, UserData(name = "Nagy Gazsi"), 8, Date(24432), 2, 2)
            )
            val lessons2 = arrayListOf<ProgramData>()
            val lessons3 = arrayListOf(
                ProgramData(2,0,"Online Marketing", 7, UserData(name = "Kis Gazsi"), 9, Date(24432), 0, 1),
                ProgramData(7,2,"Offline Marketing", 5, UserData(name = "Nagy Gazsi"), 8, Date(24432), 1, 2)
            )
            val dialog = LessonsBsdFragment(lessons3)
            dialog.show((holder.itemView.context as AppCompatActivity).supportFragmentManager, LessonsBsdFragment.TAG)

        }
    }

    override fun getItemCount(): Int {
        return 365
    }

    class ViewHolder(val itemBinding: FragmentDayBinding) : RecyclerView.ViewHolder(itemBinding.root){

    }
}