package com.norbert.koller.student.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.student.recycleradapters.DayRecyclerAdapter
import com.norbert.koller.shared.R
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.student.databinding.FragmentDaysBinding

class DaysFragment : Fragment() {


    lateinit var binding : FragmentDaysBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDaysBinding.inflate(layoutInflater)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.cardDate.setOnClickListener{
            DateTimeHelper.setupDbd(binding.cardDate.getChildAt(0) as TextView, DateTimeHelper.monthDayWeekdayFormat).show(parentFragmentManager,  "MATERIAL_DATE_PICKER")
        }

        binding.recyclerView.adapter = DayRecyclerAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)



    }
}