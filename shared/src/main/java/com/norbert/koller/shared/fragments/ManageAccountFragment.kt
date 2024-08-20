package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.ChangePasswordActivity
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.FragmentManageAccountBinding
import com.norbert.koller.shared.managers.ApplicationManager
import com.stfalcon.imageviewer.StfalconImageViewer

class ManageAccountFragment : FragmentInMainActivity() {

    private lateinit var binding : FragmentManageAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textName.text = UserData.instance.name
        binding.user.setUser(UserData.instance)

        binding.user.setOnClickListener{
            StfalconImageViewer.Builder(requireContext(), listOf(binding.user.getImage().drawable)){ view, drawable ->
                view.setImageDrawable(drawable)
            }
                .withTransitionFrom(binding.user.getImage())
                .show(parentFragmentManager)
        }

        binding.cbPersonalData.setOnClickListener{
            getMainActivity().addFragment(PersonalDataFragment())
        }

        binding.cbPassword.setOnClickListener{
            ApplicationManager.openActivity(requireContext(), ChangePasswordActivity::class.java)
        }
    }

    override fun getFragmentTitle(): String {
        return getString(R.string.manage_account)
    }
}