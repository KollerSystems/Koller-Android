package com.norbert.koller.teacher.fragments


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.NameContentButton
import com.norbert.koller.shared.customviews.NameContentView
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.fragments.BaseProgramFragmentInterface
import com.norbert.koller.shared.fragments.DetailsFragment
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.EditStudyGroupActivity
import com.skydoves.androidveil.VeilLayout
import retrofit2.Response


class BaseProgramFragment(id : Int? = null) : DetailsFragment(id), BaseProgramFragmentInterface {


    override lateinit var ncwDate : NameContentView
    override lateinit var ncwTime : NameContentView
    override lateinit var ncbClassroom : NameContentButton
    override lateinit var ncbClass : NameContentButton
    override lateinit var ncbTeacher : NameContentButton
    override lateinit var toGeneralButton: Button

    override fun getDataTag(): String {
        return "base_program"
    }

    override fun apiFunctionToCall(): suspend () -> Response<*> {
        return { RetrofitInstance.api.getMandatory(viewModel.id!!)}
    }

    override fun getVeils(): List<VeilLayout> {
        return emptyList()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_program
    }

    override fun getTimeLimit(): Int {
        return DateTimeHelper.TIME_IMPORTANT
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val editButton : SimpleCardButton = view.findViewById(R.id.Scb_edit)


        editButton.setOnClickListener {
            val intent = Intent(requireContext(), EditStudyGroupActivity::class.java)
            startActivity(intent)
        }

        findViews(view)

        toGeneralButton.setOnClickListener{
            Toast.makeText(requireContext(), "Not implemented hahaha", Toast.LENGTH_SHORT).show()
        }

        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as ProgramData

            (context as MainActivity).setToolbarTitle(response.topic)

            setViews(response, requireContext())

        }
    }

}