package com.norbert.koller.teacher.fragments


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.DescriptionButton
import com.norbert.koller.shared.customviews.DescriptionView
import com.norbert.koller.shared.customviews.CardButton
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.fragments.BaseProgramFragmentInterface
import com.norbert.koller.shared.fragments.DetailsFragment
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.EditStudyGroupActivity
import retrofit2.Response


class BaseProgramFragment(id : Int? = null) : DetailsFragment(id), BaseProgramFragmentInterface {


    override lateinit var ncwDate : DescriptionView
    override lateinit var ncwTime : DescriptionView
    override lateinit var ncbClassroom : DescriptionButton
    override lateinit var ncbClass : DescriptionButton
    override lateinit var ncbTeacher : DescriptionButton
    override lateinit var toGeneralButton: View

    override fun getDataTag(): String {
        return "base_program"
    }

    override fun apiFunctionToCall(): suspend () -> Response<*> {
        return { RetrofitInstance.api.getMandatory(viewModel.id!!)}
    }

    override fun getLayout(): Int {
        return R.layout.fragment_program
    }

    override fun getTimeLimit(): Int {
        return DateTimeHelper.TIME_IMPORTANT
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val editButton : CardButton = view.findViewById(R.id.cb_edit)


        editButton.setOnClickListener {
            val intent = Intent(requireContext(), EditStudyGroupActivity::class.java)
            startActivity(intent)
        }

        findViews(view)

        toGeneralButton.setOnClickListener{
            Toast.makeText(requireContext(), "Not implemented hahaha", Toast.LENGTH_SHORT).show()
        }

        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as BaseProgramData

            (context as MainActivity).setToolbarTitle(response.topic)

            setViews(response, requireContext())

            ncbClass.buttonContent.setOnClickListener{
                classClick(response, requireContext())

            }

            ncbTeacher.buttonContent.setOnClickListener{
                teacherClick(response, requireContext())

            }

            ncbClassroom.buttonContent.setOnClickListener{
                classRoomClick(response, requireContext())

            }

        }
    }

}