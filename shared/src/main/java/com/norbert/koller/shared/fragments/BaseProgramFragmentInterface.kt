package com.norbert.koller.shared.fragments

import android.content.Context
import android.view.View
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.NameContentButton
import com.norbert.koller.shared.customviews.NameContentView
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.formatDate

interface BaseProgramFragmentInterface : ProgramFragmentInterface {

    var ncbClass : NameContentButton

    override fun findViews(view : View){

        super.findViews(view)
        ncbClass = view.findViewById(com.norbert.koller.shared.R.id.ncb_class)
    }

    override fun setViews(response : ProgramData, context : Context){

        super.setViews(response, context)

        response as BaseProgramData

        ncbClass.visibility = View.VISIBLE
        ncbClass.buttonContent.text = response.class_.class_
        ncbClass.buttonContent.setOnClickListener {
            val userFragment = ApplicationManager.usersFragment(null)
                .setFilter("Class.ID", response.class_.id.toString())
            (context as MainActivity).addFragment(userFragment)
        }


    }

}