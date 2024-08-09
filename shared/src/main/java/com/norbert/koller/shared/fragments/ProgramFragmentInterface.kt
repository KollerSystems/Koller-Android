package com.norbert.koller.shared.fragments

import android.content.Context
import android.view.View
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.DescriptionButton
import com.norbert.koller.shared.customviews.DescriptionView
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.formatDate

interface ProgramFragmentInterface {

    var ncwDate : DescriptionView
    var ncwTime : DescriptionView
    var ncbClassroom : DescriptionButton
    var ncbTeacher : DescriptionButton
    var toGeneralButton : View

    fun findViews(view : View){
        ncwDate = view.findViewById(com.norbert.koller.shared.R.id.ncw_date)
        ncwTime = view.findViewById(com.norbert.koller.shared.R.id.ncw_time)
        ncbClassroom = view.findViewById(com.norbert.koller.shared.R.id.db_classroom)
        ncbTeacher = view.findViewById(com.norbert.koller.shared.R.id.db_teacher)
        toGeneralButton = view.findViewById(com.norbert.koller.shared.R.id.button)
    }

    fun setViews(response : ProgramData, context : Context){
        ncwDate.getTextDescription().text = response.date.formatDate(DateTimeHelper.monthDay)
        ncwTime.getTextDescription().text = ApplicationManager.createClassesText(context, response.lesson, response.length)
        ncbClassroom.getTextDescription().text = response.rid.toString()



        ncbTeacher.getTextDescription().text = response.teacher!!.name.toString()

    }

    fun teacherClick(response : ProgramData, context : Context){
        (context as MainActivity).addFragment(ApplicationManager.userFragment(response.teacher!!.uid))
    }

    fun classRoomClick(response : ProgramData, context : Context){
        (context as MainActivity).addFragment(ApplicationManager.roomFragment(response.rid))
    }

}