package com.norbert.koller.shared

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.fragments.UserFragment
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
            ncwDate.textContent.text = SimpleDateFormat(MyApplication.monthDay).format(baseProgram.Date)
            ncwTime.textContent.text = MyApplication.createClassesText(requireContext(), baseProgram.Lesson, baseProgram.Length)
            ncbClassroom.buttonContent.text = baseProgram.RID.toString()
            ncbClass.buttonContent.text = baseProgram.Group
            ncbTeacher.buttonContent.text = baseProgram.TUID.toString()

            ncbClassroom.buttonContent.setOnClickListener {
                (requireContext() as MainActivity).changeFragment(MyApplication.roomFragment(baseProgram.RID))
                dismiss()
            }

            ncbClass.buttonContent.setOnClickListener {
                dismiss()
            }

            ncbTeacher.buttonContent.setOnClickListener {
                (requireContext() as MainActivity).changeFragment(MyApplication.userFragment(baseProgram.TUID))
                dismiss()
            }
        }
        else{
            dismiss()
        }
    }
}