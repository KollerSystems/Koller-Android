package com.norbert.koller.teacher.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.NameContentButton
import com.norbert.koller.shared.customviews.NameContentView
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.fragments.DetailsFragment
import com.norbert.koller.shared.fragments.StudyGroupTypeFragment
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.EditSpecificStudyGroupActivity
import com.skydoves.androidveil.VeilLayout
import retrofit2.Response

class StudyGroupTypeFragment(pid : Int? = null) : StudyGroupTypeFragment(pid) {

    override fun getLayout(): Int {
        return R.layout.fragment_study_group_type
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editButton : SimpleCardButton = view.findViewById(R.id.Scb_edit)

        editButton.setOnClickListener {
            val intent = Intent(requireContext(), EditSpecificStudyGroupActivity::class.java)
            startActivity(intent)
        }
    }

}