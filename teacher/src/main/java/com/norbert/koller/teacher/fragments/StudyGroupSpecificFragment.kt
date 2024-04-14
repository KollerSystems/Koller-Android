package com.norbert.koller.teacher.fragments


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.NameContentButton
import com.norbert.koller.shared.customviews.NameContentView
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.fragments.DetailsFragment
import com.norbert.koller.shared.fragments.ProgramFragmentInterface
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.EditStudyGroupActivity
import com.skydoves.androidveil.VeilLayout
import retrofit2.Response


class StudyGroupSpecificFragment(id : Int? = null) : DetailsFragment(id), ProgramFragmentInterface {

    override lateinit var ncwDate : NameContentView
    override lateinit var ncwTime : NameContentView
    override lateinit var ncbClassroom : NameContentButton
    override lateinit var ncbTeacher : NameContentButton

    override fun getDataTag(): String {
        return "study_group"
    }

    override fun apiFunctionToCall(): suspend () -> Response<*> {
        return { RetrofitInstance.api.getStudyGroup(viewModel.id!!)}
    }

    override fun getVeils(): List<VeilLayout> {
        return emptyList()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_user_presence
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

        val toGeneralButton : Button = view.findViewById(R.id.Button_to_general)

        toGeneralButton.setOnClickListener{
            (requireContext() as MainActivity).addFragment(ProgramTypeFragment((viewModel.response.value as StudyGroupData).programID))
        }

        findViews(view)

        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as ProgramData

            (context as MainActivity).setToolbarTitle(response.topic)

            setViews(response, requireContext())

        }
    }


}