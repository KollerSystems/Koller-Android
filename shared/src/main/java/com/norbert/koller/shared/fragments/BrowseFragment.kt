package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.databinding.FragmentBrowseBinding
import com.norbert.koller.shared.helpers.setMaxWidth

open class BrowseFragment : FragmentInMainActivity() {

    lateinit var binding : FragmentBrowseBinding

    override fun getFragmentTitle(): String? {
        return getString(R.string.browse)
    }

    fun addButton(text : String, iconResource : Int, index : Int? = null) : MaterialButton {
        val button = MaterialButton(requireContext())
        val buttonToCopy = binding.btnUsers
        button.layoutParams = buttonToCopy.layoutParams
        button.setPaddingRelative(buttonToCopy.paddingStart, buttonToCopy.paddingTop, buttonToCopy.paddingEnd, buttonToCopy.paddingBottom)
        button.background = buttonToCopy.background.constantState!!.newDrawable().mutate()
        button.setTextAppearance(R.style.Text)
        button.setTextColor(buttonToCopy.textColors)
        button.iconPadding = buttonToCopy.iconPadding
        button.iconTint = buttonToCopy.iconTint
        button.text = text
        button.isSingleLine = true
        button.ellipsize = buttonToCopy.ellipsize
        button.setIconResource(iconResource)
        binding.fblBrowse.addView(button, index ?: (binding.fblBrowse.childCount))
        return button
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBrowseBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPlaces.setOnClickListener{
            (context as MainActivity).addFragment(ApplicationManager.roomListFragment())
        }

        binding.btnUsers.setOnClickListener{
            (context as MainActivity).addFragment(ApplicationManager.userListFragment())
        }

        binding.btnStudyGroups.setOnClickListener {
            (context as MainActivity).addFragment(ApplicationManager.studyGroupsFragment())
        }
    }
}