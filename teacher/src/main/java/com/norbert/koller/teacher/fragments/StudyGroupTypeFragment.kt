package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.customviews.CardButton
import com.norbert.koller.teacher.pageradapter.ProgramTypeViewPagerAdapter
import com.norbert.koller.teacher.R
import com.norbert.koller.shared.R as Rs
import com.norbert.koller.teacher.activities.EditSpecificStudyGroupActivity
import com.norbert.koller.teacher.databinding.FragmentStudyGroupTypeBinding
import com.norbert.koller.teacher.databinding.FragmentStudyGroupTypeDataBinding

class StudyGroupTypeFragment(val pid : Int? = null) : Fragment() {


    lateinit var binding : FragmentStudyGroupTypeBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudyGroupTypeBinding.inflate(layoutInflater)
        ViewCompat.setTransitionName(binding.root.rootView, "cardTransition_${pid}position")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cbEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditSpecificStudyGroupActivity::class.java)
            startActivity(intent)
        }


        val adapter = ProgramTypeViewPagerAdapter(pid, childFragmentManager, lifecycle)

        binding.viewPager.adapter = adapter


        TabLayoutMediator(binding.tabs, binding.viewPager){tab,position->
            when(position){
                0->{
                    tab.text = "Adatok"
                }
                1->{
                    tab.text = "Résztvevők"
                }
                2->{
                    tab.text = "Elkönyvelt"
                }
            }
        }.attach()
    }

}