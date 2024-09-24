package com.norbert.koller.student.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ContentFragmentBsdfProfileFooterBinding
import com.norbert.koller.shared.databinding.ContentFragmentBsdfProfileHeaderBinding
import com.norbert.koller.shared.fragments.UserOutgoingsFragment
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.student.databinding.ContentFragmentBsdfProfileBinding

class ProfileBsdfFragment : com.norbert.koller.shared.fragments.bottomsheet.ProfileBsdfFragment() {

    lateinit var binding : ContentFragmentBsdfProfileBinding

    override fun getHeaderBinding(): ContentFragmentBsdfProfileHeaderBinding {
        return binding.header
    }

    override fun getFooterBinding(): ContentFragmentBsdfProfileFooterBinding {
        return binding.footer
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cbOutgoing.setOnClickListener{

            val fragment = UserOutgoingsFragment()
            val bundle = Bundle()
            bundle.putInt("id", CacheManager.userData!!.getMainID())
            fragment.arguments = bundle
            (requireContext() as MainActivity).addFragment(fragment)
            this.dismiss()
        }
    }

    override fun getContent(inflater: LayoutInflater): ViewGroup {
        binding = ContentFragmentBsdfProfileBinding.inflate(layoutInflater)
        return binding.root
    }

}