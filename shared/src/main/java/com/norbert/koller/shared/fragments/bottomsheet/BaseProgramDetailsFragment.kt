package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.customviews.NameContentButton
import com.norbert.koller.shared.customviews.NameContentView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.formatDate
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import java.text.SimpleDateFormat

class BaseProgramDetailsFragment(val program : ProgramData? = null) : BottomSheetDialogFragment() {

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
        val ncwDate : NameContentView = view.findViewById(R.id.ncw_date)
        val ncwTime : NameContentView = view.findViewById(R.id.ncw_time)
        val ncbClassroom : NameContentButton = view.findViewById(R.id.ncb_classroom)
        val ncbClass : NameContentButton = view.findViewById(R.id.ncb_class)
        val ncbTeacher : NameContentButton = view.findViewById(R.id.ncb_teacher)

        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]

        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as ProgramData

            textTitle.text = response.topic

            ncwState.visibility = GONE
            ncwDate.textContent.text = response.date.formatDate(DateTimeHelper.monthDay)
            ncwTime.textContent.text = ApplicationManager.createClassesText(requireContext(), response.lesson, response.length)
            ncbClassroom.buttonContent.text = response.rid.toString()

            if(response is BaseProgramData) {
                ncbClass.visibility = VISIBLE
                ncbClass.buttonContent.text = response.class_.class_
                ncbClass.buttonContent.setOnClickListener {
                    val userFragment = ApplicationManager.usersFragment(null)
                        .setFilter("Class.ID", response.class_.id.toString())
                    (requireContext() as MainActivity).addFragment(userFragment)
                    dismiss()
                }
            }


            ncbTeacher.buttonContent.text = response.tuid.toString()

            ncbClassroom.buttonContent.setOnClickListener {
                (requireContext() as MainActivity).addFragment(ApplicationManager.roomFragment(response.rid))
                dismiss()
            }

            ncbTeacher.buttonContent.setOnClickListener {

                (requireContext() as MainActivity).addFragment(ApplicationManager.userFragment(response.tuid))
                dismiss()
            }

        }

        if(!viewModel.response.isInitialized) {
            viewModel.response.value = program
        }
    }
}