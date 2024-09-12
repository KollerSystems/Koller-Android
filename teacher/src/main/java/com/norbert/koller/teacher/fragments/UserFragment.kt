package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ContentFragmentUserHeaderBinding
import com.norbert.koller.shared.fragments.CrossingListFragment
import com.norbert.koller.shared.fragments.PersonalDataFragment
import com.norbert.koller.shared.fragments.UserOutgoingsFragment
import com.norbert.koller.teacher.activities.EditUserActivity
import com.norbert.koller.teacher.databinding.FragmentUserBinding

class UserFragment(uid : Int) : com.norbert.koller.shared.fragments.UserFragment(uid) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cbOutgoings.setOnClickListener {
            (context as MainActivity).addFragment(UserOutgoingsFragment(viewModel.response.value as UserData))
        }

        binding.cbCrossings.setOnClickListener {
            (context as MainActivity).addFragment(CrossingListFragment(viewModel.id))
        }

        binding.cbEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditUserActivity::class.java)
            startActivity(intent)
        }

        binding.cbPersonalData.setOnClickListener{
            getMainActivity().addFragment(PersonalDataFragment())
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