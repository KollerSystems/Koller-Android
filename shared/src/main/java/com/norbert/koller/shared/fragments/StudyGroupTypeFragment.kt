package com.norbert.koller.shared.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.NameContentButton
import com.norbert.koller.shared.customviews.NameContentView
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.skydoves.androidveil.VeilLayout
import retrofit2.Response


abstract class StudyGroupTypeFragment(pid : Int? = null) : DetailsFragment(pid) {

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




    override fun getTimeLimit(): Int {
        return DateTimeHelper.TIME_IMPORTANT
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ncbClassroom = view.findViewById(R.id.ncb_classroom)
        ncbTeacher = view.findViewById(R.id.ncb_teacher)

        ncwMonday = view.findViewById(R.id.ncw_monday)
        ncwTuesday = view.findViewById(R.id.ncw_tuesday)
        ncwWednesday = view.findViewById(R.id.ncw_wednesday)
        ncwThursday = view.findViewById(R.id.ncw_thursday)

        ncwRepeat = view.findViewById(R.id.ncw_repeat)

        scbNextProgram = view.findViewById(R.id.Scb_next_program)

        scbNextProgram.setOnClickListener{

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