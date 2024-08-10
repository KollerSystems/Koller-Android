package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.NestedScrollView
import com.norbert.koller.shared.activities.EditCaretakerActivity
import com.norbert.koller.shared.activities.EditCloseRelativeActivity
import com.norbert.koller.shared.activities.EditSchoolActivity
import com.norbert.koller.shared.activities.EditStudentActivity
import com.norbert.koller.teacher.R
import com.norbert.koller.shared.R as Rs
import com.norbert.koller.shared.customviews.CardButton
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ContentFragmentUserHeaderBinding
import com.norbert.koller.shared.fragments.CrossingsFragment
import com.norbert.koller.shared.fragments.PersonalDataFragment
import com.norbert.koller.shared.fragments.UserOutgoingsFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListStaticBsdFragment
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.teacher.activities.EditUserActivity
import com.norbert.koller.teacher.databinding.FragmentUserBinding

class UserFragment(uid : Int? = null) : com.norbert.koller.shared.fragments.UserFragment(uid) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cbOutgoings.setOnClickListener {
            (context as MainActivity).addFragment(UserOutgoingsFragment(viewModel.response.value as UserData))
        }

        binding.cbCrossings.setOnClickListener {
            (context as MainActivity).addFragment(CrossingsFragment(viewModel.id))
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