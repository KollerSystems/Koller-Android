package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.customview.NameContentButton
import com.norbert.koller.shared.customview.NameContentView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.helpers.DateTimeHelper
import java.text.SimpleDateFormat

class BaseProgramDetailsFragment(val baseProgram : BaseProgramData? = null) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base_program_details, container, false)
    }

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

        if(baseProgram!= null) {
            textTitle.text = baseProgram.Topic

            ncwState.visibility = GONE
            ncwDate.textContent.text = SimpleDateFormat(DateTimeHelper.monthDay).format(baseProgram.Date)
            ncwTime.textContent.text = MyApplication.createClassesText(requireContext(), baseProgram.Lesson, baseProgram.Length)
            ncbClassroom.buttonContent.text = baseProgram.RID.toString()
            ncbClass.buttonContent.text = baseProgram.Group
            ncbTeacher.buttonContent.text = baseProgram.TUID.toString()

            ncbClassroom.buttonContent.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("RID", baseProgram.RID)
                val fragment = MyApplication.roomFragment()
                fragment.arguments = bundle
                (requireContext() as MainActivity).changeFragment(fragment)
                dismiss()
            }

            ncbClass.buttonContent.setOnClickListener {
                dismiss()
            }

            ncbTeacher.buttonContent.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("TUID", baseProgram.TUID)
                val fragment = MyApplication.userFragment()
                fragment.arguments = bundle
                (requireContext() as MainActivity).changeFragment(fragment)
                dismiss()
            }
        }
        else{
            dismiss()
        }
    }
}