package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.NameContentButton
import com.norbert.koller.shared.customviews.NameContentView
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.fragments.DetailsFragment
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.formatDate
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.EditSpecificStudyGroupActivity
import com.norbert.koller.teacher.activities.EditStudyGroupActivity
import com.skydoves.androidveil.VeilLayout
import retrofit2.Response

class ProgramTypeFragment(pid : Int? = null) : DetailsFragment(pid) {

    lateinit var ncbClassroom : NameContentButton
    lateinit var ncbClass : NameContentButton
    lateinit var ncbTeacher : NameContentButton


    lateinit var ncwMonday : NameContentView
    lateinit var ncwTuesday : NameContentView
    lateinit var ncwWednesday : NameContentView
    lateinit var ncwThursday : NameContentView

    lateinit var ncwRepeat : NameContentView

    lateinit var scbNextProgram : SimpleCardButton

    override fun getDataTag(): String {
        return "study_group_type"
    }

    override fun apiFunctionToCall(): suspend () -> Response<*> {
        return { RetrofitInstance.api.getStudyGroupType(viewModel.id!!)}
    }

    override fun getVeils(): List<VeilLayout> {
        return listOf()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_program_type
    }


    override fun getTimeLimit(): Int {
        return DateTimeHelper.TIME_IMPORTANT
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ncbClassroom = view.findViewById(com.norbert.koller.shared.R.id.ncb_classroom)
        ncbClass = view.findViewById(com.norbert.koller.shared.R.id.ncb_class)
        ncbTeacher = view.findViewById(com.norbert.koller.shared.R.id.ncb_teacher)

        ncwMonday = view.findViewById(R.id.ncw_monday)
        ncwTuesday = view.findViewById(R.id.ncw_tuesday)
        ncwWednesday = view.findViewById(R.id.ncw_wednesday)
        ncwThursday = view.findViewById(R.id.ncw_thursday)

        ncwRepeat = view.findViewById(R.id.ncw_repeat)

        scbNextProgram = view.findViewById(R.id.Scb_next_program)

        scbNextProgram.setOnClickListener{

        }


        val editButton : SimpleCardButton = view.findViewById(R.id.Scb_edit)

        editButton.setOnClickListener {
            val intent = Intent(requireContext(), EditSpecificStudyGroupActivity::class.java)
            startActivity(intent)
        }

        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as StudyGroupTypeData

            (context as MainActivity).setToolbarTitle(response.topic)

            ncbClassroom.buttonContent.text = response.rid.toString()

            ncbTeacher.buttonContent.text = response.teacher!!.name.toString()

            ncbClassroom.buttonContent.setOnClickListener {
                (context as MainActivity).addFragment(ApplicationManager.roomFragment(response.rid))
            }

            ncbTeacher.buttonContent.setOnClickListener {
                (context as MainActivity).addFragment(ApplicationManager.userFragment(response.teacher!!.uid))
            }
        }
    }
}