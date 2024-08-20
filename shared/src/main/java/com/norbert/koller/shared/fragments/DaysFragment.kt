package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.norbert.koller.shared.databinding.FragmentDaysBinding
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.recycleradapters.DayRecyclerAdapter

class DaysFragment : FragmentInMainActivity() {


    lateinit var binding : FragmentDaysBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDaysBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun getFragmentTitle(): String? {
        return null
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