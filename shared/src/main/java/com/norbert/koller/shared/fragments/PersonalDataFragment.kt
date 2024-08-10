package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.EditCaretakerActivity
import com.norbert.koller.shared.activities.EditCloseRelativeActivity
import com.norbert.koller.shared.activities.EditSchoolActivity
import com.norbert.koller.shared.activities.EditStudentActivity
import com.norbert.koller.shared.databinding.ContentPersonalDataBinding
import com.norbert.koller.shared.managers.ApplicationManager

class PersonalDataFragment : FragmentInMainActivity() {

    lateinit var binding : ContentPersonalDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ContentPersonalDataBinding.inflate(layoutInflater)
        val padding = requireContext().resources.getDimensionPixelSize(R.dimen.full_card_padding)
        val nestedScrollView = NestedScrollView(requireContext())
        binding.root.setPadding(padding,padding,padding,padding)
        nestedScrollView.addView(binding.root)
        return nestedScrollView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEditStudent.setOnClickListener{
            ApplicationManager.openActivity(requireContext(), EditStudentActivity::class.java)
        }
        binding.btnEditCaretaker.setOnClickListener{
            ApplicationManager.openActivity(requireContext(), EditCaretakerActivity::class.java)
        }
        binding.btnEditCloseRelative.setOnClickListener{
            ApplicationManager.openActivity(requireContext(), EditCloseRelativeActivity::class.java)
        }
        binding.btnEditSchool.setOnClickListener{
            ApplicationManager.openActivity(requireContext(), EditSchoolActivity::class.java)
        }
    }

    override fun getFragmentTitle(): String {
        return getString(R.string.personal_data)
    }

}