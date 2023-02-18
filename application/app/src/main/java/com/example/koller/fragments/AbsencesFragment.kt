package com.example.koller.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.viewpager2.widget.ViewPager2
import com.example.koller.AbsencesViewPagerAdapter
import com.example.koller.R
import com.example.koller.WelcomeFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AbsencesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_absences, container, false)


        val tabs : TabLayout = view.findViewById(R.id.absences_tabs)
        val viewPager : ViewPager2 = view.findViewById(R.id.absences_view_pager)

        val adapter = AbsencesViewPagerAdapter(parentFragmentManager, lifecycle)

        viewPager.adapter = adapter


        TabLayoutMediator(tabs, viewPager){tab,position->
            when(position){
                0->{
                    tab.text = "Portai késések"
                }
                1->{
                    tab.text = "Alapprogrami hiányzások"
                }
                2->{
                    tab.text = "Szakköri hiányzások"
                }
            }
        }.attach()

        return view
    }
}