package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.managers.MyApplication
import com.norbert.koller.shared.customviews.NameContentButton
import com.norbert.koller.shared.customviews.NameContentView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.formatDate
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import java.text.SimpleDateFormat

class BaseProgramDetailsFragment(val baseProgram : BaseProgramData? = null) : BottomSheetDialogFragment() {

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

            response as BaseProgramData

            textTitle.text = response.topic

            ncwState.visibility = GONE
            ncwDate.textContent.text = response.date.formatDate(DateTimeHelper.monthDay)
            ncwTime.textContent.text = MyApplication.createClassesText(requireContext(), response.lesson, response.length)
            ncbClassroom.buttonContent.text = response.rid.toString()
            ncbClass.buttonContent.text = response.group
            ncbTeacher.buttonContent.text = response.tuid.toString()

            ncbClassroom.buttonContent.setOnClickListener {
                (requireContext() as MainActivity).addFragment(MyApplication.roomFragment(response.rid))
                dismiss()
            }

            ncbClass.buttonContent.setOnClickListener {
                dismiss()
            }

            ncbTeacher.buttonContent.setOnClickListener {

                (requireContext() as MainActivity).addFragment(MyApplication.userFragment(response.tuid))
                dismiss()
            }

        }

        if(!viewModel.response.isInitialized) {
            viewModel.response.value = baseProgram
        }
    }
}