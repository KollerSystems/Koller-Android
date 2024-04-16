package com.norbert.koller.student.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.customviews.NameContentButton
import com.norbert.koller.shared.customviews.NameContentView
import com.norbert.koller.student.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.fragments.ProgramFragmentInterface
import com.norbert.koller.shared.fragments.StudyGroupTypeFragment
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.formatDate
import com.norbert.koller.shared.viewmodels.ResponseViewModel

class StudyGroupDetailsFragment(val program : ProgramData? = null) : BottomSheetDialogFragment(), ProgramFragmentInterface {

    override lateinit var ncwDate: NameContentView
    override lateinit var ncwTime: NameContentView
    override lateinit var ncbClassroom: NameContentButton
    override lateinit var ncbTeacher: NameContentButton
    override lateinit var toGeneralButton: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bshd_base_program_details, container, false)
    }

    lateinit var viewModel : ResponseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textTitle : TextView = view.findViewById(R.id.text_title)
        val imageState : ImageView = view.findViewById(R.id.image_state)
        val ncwState : NameContentView = view.findViewById(R.id.ncw_state)
        findViews(view)

        toGeneralButton.isVisible = true
        toGeneralButton.setOnClickListener{
            dismiss()
            (context as MainActivity).addFragment(com.norbert.koller.student.fragments.StudyGroupTypeFragment((viewModel.response.value as StudyGroupData).programID))
        }

        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]

        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as ProgramData

            setViews(response, requireContext())
            ncwState.visibility = GONE

            textTitle.text = response.topic


        }

        if(!viewModel.response.isInitialized) {
            viewModel.response.value = program
        }
    }


}