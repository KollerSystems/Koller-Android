package com.norbert.koller.teacher

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.fragments.DetailsFragment
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.teacher.managers.ApplicationManager
import com.skydoves.androidveil.VeilLayout
import retrofit2.Response

class ProgramTypeFragment : DetailsFragment() {
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
    }
}