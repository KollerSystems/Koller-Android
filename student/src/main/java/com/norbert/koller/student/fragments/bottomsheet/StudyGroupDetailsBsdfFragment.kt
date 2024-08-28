package com.norbert.koller.student.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.norbert.koller.shared.customviews.DescriptionButton
import com.norbert.koller.shared.customviews.DescriptionView
import com.norbert.koller.student.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.fragments.ProgramFragmentInterface
import com.norbert.koller.shared.fragments.bottomsheet.ScrollBsdfFragment
import com.norbert.koller.shared.viewmodels.DetailsViewModel
import com.norbert.koller.student.databinding.ContentFragmentBsdfBaseProgramBinding

class StudyGroupDetailsBsdfFragment(val program : ProgramData? = null) : ScrollBsdfFragment(), ProgramFragmentInterface {

    override lateinit var ncwDate: DescriptionView
    override lateinit var ncwTime: DescriptionView
    override lateinit var ncbClassroom: DescriptionButton
    override lateinit var ncbTeacher: DescriptionButton
    override lateinit var toGeneralButton: View

    lateinit var binding : ContentFragmentBsdfBaseProgramBinding
    override fun getContent(inflater: LayoutInflater): ViewGroup {
        binding = ContentFragmentBsdfBaseProgramBinding.inflate(inflater)
        return binding.root
    }

    lateinit var viewModel : DetailsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ncwState : DescriptionView = view.findViewById(R.id.db_state)
        findViews(view)

        toGeneralButton.isVisible = true
        toGeneralButton.setOnClickListener{
            dismiss()
            (context as MainActivity).addFragment(com.norbert.koller.student.fragments.StudyGroupTypeFragment((viewModel.response.value as StudyGroupData).programID))
        }

        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as ProgramData

            setViews(response, requireContext())
            ncwState.visibility = GONE

            setTitle(response.topic)


            ncbTeacher.getTextDescription().setOnClickListener{
                teacherClick(response, requireContext())
                dismiss()
            }

            ncbClassroom.getTextDescription().setOnClickListener{
                classRoomClick(response, requireContext())
                dismiss()
            }


        }

        if(!viewModel.response.isInitialized) {
            viewModel.response.value = program
        }
    }


}