package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.DescriptionButton
import com.norbert.koller.shared.customviews.DescriptionView
import com.norbert.koller.shared.customviews.CardButton
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import retrofit2.Response


abstract class StudyGroupTypeFragment(pid : Int? = null) : DetailsFragment(pid) {

    lateinit var ncbClassroom : DescriptionButton
    lateinit var ncbClass : DescriptionButton
    lateinit var ncbTeacher : DescriptionButton


    lateinit var ncwMonday : DescriptionView
    lateinit var ncwTuesday : DescriptionView
    lateinit var ncwWednesday : DescriptionView
    lateinit var ncwThursday : DescriptionView

    lateinit var ncwRepeat : DescriptionView

    lateinit var cbNextProgram : CardButton

    override fun getFragmentTitle(): String? {
        return getString(R.string.study_group)
    }

    override fun getDataTag(): String {
        return "study_group_type"
    }

    override fun apiFunctionToCall(): suspend () -> Response<*> {
        return { RetrofitInstance.api.getStudyGroupType(viewModel.id!!)}
    }




    override fun getTimeLimit(): Int {
        return DateTimeHelper.TIME_IMPORTANT
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ncbClassroom = view.findViewById(R.id.db_classroom)
        ncbTeacher = view.findViewById(R.id.db_teacher)

        ncwMonday = view.findViewById(R.id.ncw_monday)
        ncwTuesday = view.findViewById(R.id.ncw_tuesday)
        ncwWednesday = view.findViewById(R.id.ncw_wednesday)
        ncwThursday = view.findViewById(R.id.ncw_thursday)

        ncwRepeat = view.findViewById(R.id.ncw_repeat)

        cbNextProgram = view.findViewById(R.id.cb_next_program)

        cbNextProgram.setOnClickListener{

        }



        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as StudyGroupTypeData

            getMainActivity().setToolbarTitle(response.topic)

            ncbClassroom.getTextDescription().text = response.rid.toString()

            ncbTeacher.getTextDescription().text = response.teacher!!.name.toString()

            ncbClassroom.getTextDescription().setOnClickListener {
                getMainActivity().addFragment(ApplicationManager.roomFragment(response.rid))
            }

            ncbTeacher.getTextDescription().setOnClickListener {
                getMainActivity().addFragment(ApplicationManager.userFragment(response.teacher!!.uid))
            }
        }
    }

}