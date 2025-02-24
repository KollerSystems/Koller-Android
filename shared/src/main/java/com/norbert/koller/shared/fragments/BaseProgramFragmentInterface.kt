package com.norbert.koller.shared.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.DescriptionButton
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.data.FilterConfigData
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.managers.ApplicationManager

interface BaseProgramFragmentInterface : ProgramFragmentInterface {

    var ncbClass : DescriptionButton

    override fun findViews(view : View){

        super.findViews(view)
        ncbClass = view.findViewById(com.norbert.koller.shared.R.id.db_class)
    }

    override fun setViews(response : ProgramData, context : Context){

        super.setViews(response, context)

        response as BaseProgramData

        ncbClass.visibility = View.VISIBLE
        ncbClass.getTextDescription().text = response.class_.class_
        ncbClass.getTextDescription().setOnClickListener {

        }


    }

    fun classClick(response : BaseProgramData, context : Context){
        val userFragment = ApplicationManager.userListFragment()
        val bundle = Bundle()
        bundle.putParcelable("filters", FilterConfigData(mutableMapOf(Pair("Class.ID", mutableSetOf(response.class_.id)))))
        userFragment.arguments = bundle
        (context as MainActivity).addFragment(userFragment)
    }

}