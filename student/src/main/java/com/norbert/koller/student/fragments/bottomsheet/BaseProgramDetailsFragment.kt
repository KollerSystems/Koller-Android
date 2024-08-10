package com.norbert.koller.student.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.customviews.DescriptionButton
import com.norbert.koller.shared.customviews.DescriptionView
import com.norbert.koller.student.R
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.fragments.BaseProgramFragmentInterface
import com.norbert.koller.shared.fragments.bottomsheet.ScrollBsdFragment
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import com.norbert.koller.student.databinding.FragmentBsdBaseProgramBinding

class BaseProgramDetailsFragment(val program : ProgramData? = null) : ScrollBsdFragment(), BaseProgramFragmentInterface {

    override lateinit var ncwDate: DescriptionView
    override lateinit var ncwTime: DescriptionView
    override lateinit var ncbClass: DescriptionButton
    override lateinit var ncbClassroom: DescriptionButton
    override lateinit var ncbTeacher: DescriptionButton
    override lateinit var toGeneralButton: View

    lateinit var binding : FragmentBsdBaseProgramBinding

    lateinit var viewModel : ResponseViewModel
    override fun getContent(inflater: LayoutInflater): ViewGroup {
        binding = FragmentBsdBaseProgramBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ncwState : DescriptionView = view.findViewById(R.id.db_state)

        findViews(view)

        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]



        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as BaseProgramData

            setViews(response, requireContext())
            ncwState.visibility = GONE
            setTitle(response.topic)

            ncbClass.getTextDescription().setOnClickListener{
                classClick(response, requireContext())
                dismiss()
            }

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