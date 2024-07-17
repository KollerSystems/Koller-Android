package com.norbert.koller.shared.fragments

import android.content.Context
import android.view.View
import android.widget.Button
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.NameContentButton
import com.norbert.koller.shared.customviews.NameContentView
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.formatDate

interface ProgramFragmentInterface {

    var ncwDate : NameContentView
    var ncwTime : NameContentView
    var ncbClassroom : NameContentButton
    var ncbTeacher : NameContentButton
    var toGeneralButton : View

    fun findViews(view : View){
        ncwDate = view.findViewById(com.norbert.koller.shared.R.id.ncw_date)
        ncwTime = view.findViewById(com.norbert.koller.shared.R.id.ncw_time)
        ncbClassroom = view.findViewById(com.norbert.koller.shared.R.id.ncb_classroom)
        ncbTeacher = view.findViewById(com.norbert.koller.shared.R.id.ncb_teacher)
        toGeneralButton = view.findViewById(com.norbert.koller.shared.R.id.Button_to_general)
    }

    fun setViews(response : ProgramData, context : Context){
        ncwDate.textContent.text = response.date.formatDate(DateTimeHelper.monthDay)
        ncwTime.textContent.text = ApplicationManager.createClassesText(context, response.lesson, response.length)
        ncbClassroom.buttonContent.text = response.rid.toString()



        ncbTeacher.buttonContent.text = response.teacher!!.name.toString()

    }

    fun teacherClick(response : ProgramData, context : Context){
        (context as MainActivity).addFragment(ApplicationManager.userFragment(response.teacher!!.uid))
    }

    fun classRoomClick(response : ProgramData, context : Context){
        (context as MainActivity).addFragment(ApplicationManager.roomFragment(response.rid))
    }

}