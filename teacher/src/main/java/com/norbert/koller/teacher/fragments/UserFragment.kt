package com.norbert.koller.teacher.fragments

import android.R.attr.key
import android.R.attr.value
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.databinding.ContentFragmentUserHeaderBinding
import com.norbert.koller.shared.fragments.CrossingListFragment
import com.norbert.koller.shared.fragments.PersonalDataFragment
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.norbert.koller.teacher.activities.EditUserActivity
import com.norbert.koller.teacher.databinding.FragmentUserBinding


class UserFragment() : com.norbert.koller.shared.fragments.UserFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.cbEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditUserActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onFinishLoading() {
        super.onFinishLoading()
        if(getUserData().role == 1){

            binding.textManage.text = getString(com.norbert.koller.shared.R.string.manage_student)
            binding.cbOutgoings.isVisible = true
            binding.cbCalendar.isVisible = true
            binding.cbCrossings.isVisible = true
            binding.cbPersonalData.isVisible = true
            binding.cbCommendationsOrWarnings.isVisible = true

            binding.cbOutgoings.setOnClickListener {
                val fragment = OutgoingListFragment()
                val bundle = Bundle()
                bundle.putInt("id", getUserData().uid)
                fragment.arguments = bundle
                (context as MainActivity).addFragment(fragment)
            }

            binding.cbCrossings.setOnClickListener {
                val fragment = CrossingListFragment()
                val bundle = Bundle()
                bundle.putInt("id", getUserData().uid)
                fragment.arguments = bundle
                (context as MainActivity).addFragment(fragment)
            }
            binding.cbPersonalData.setOnClickListener{
                getMainActivity().addFragment(PersonalDataFragment())
            }
        }
    }

    lateinit var binding : FragmentUserBinding

    override fun getHeaderBinding(): ContentFragmentUserHeaderBinding {
        return binding.header
    }

    override fun getNestedScrollView(): NestedScrollView {
        return binding.nsv
    }

    override fun createRootView(): View {
        binding = FragmentUserBinding.inflate(layoutInflater)
        return binding.root
    }



}