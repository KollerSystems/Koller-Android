package com.norbert.koller.shared.fragments

import android.content.Context
import android.view.View
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.NameContentButton
import com.norbert.koller.shared.customviews.NameContentView
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.formatDate

interface ProgramFragmentInterface {

    var ncwDate : NameContentView
    var ncwTime : NameContentView
    var ncbClassroom : NameContentButton
    var ncbClass : NameContentButton
    var ncbTeacher : NameContentButton

    fun findViews(view : View){
        ncwDate = view.findViewById(com.norbert.koller.shared.R.id.ncw_date)
        ncwTime = view.findViewById(com.norbert.koller.shared.R.id.ncw_time)
        ncbClassroom = view.findViewById(com.norbert.koller.shared.R.id.ncb_classroom)
        ncbClass = view.findViewById(com.norbert.koller.shared.R.id.ncb_class)
        ncbTeacher = view.findViewById(com.norbert.koller.shared.R.id.ncb_teacher)
    }

    fun setViews(response : ProgramData, context : Context){
        ncwDate.textContent.text = response.date.formatDate(DateTimeHelper.monthDay)
        ncwTime.textContent.text = ApplicationManager.createClassesText(context, response.lesson, response.length)
        ncbClassroom.buttonContent.text = response.rid.toString()

        if(response is BaseProgramData) {
            ncbClass.visibility = View.VISIBLE
            ncbClass.buttonContent.text = response.class_.class_
            ncbClass.buttonContent.setOnClickListener {
                val userFragment = ApplicationManager.usersFragment(null)
                    .setFilter("Class.ID", response.class_.id.toString())
                (context as MainActivity).addFragment(userFragment)
            }
        }


        ncbTeacher.buttonContent.text = response.teacher!!.name.toString()

        ncbClassroom.buttonContent.setOnClickListener {
            (context as MainActivity).addFragment(ApplicationManager.roomFragment(response.rid))
        }

        ncbTeacher.buttonContent.setOnClickListener {

            (context as MainActivity).addFragment(ApplicationManager.userFragment(response.teacher!!.uid))
        }
    }

}