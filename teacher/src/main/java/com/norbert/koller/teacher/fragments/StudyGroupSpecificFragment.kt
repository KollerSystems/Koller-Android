package com.norbert.koller.teacher.fragments


import android.content.Intent
import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.DescriptionButton
import com.norbert.koller.shared.customviews.DescriptionView
import com.norbert.koller.shared.customviews.CardButton
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.fragments.DetailsFragment
import com.norbert.koller.shared.fragments.ProgramFragmentInterface
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.EditStudyGroupActivity
import com.norbert.koller.teacher.databinding.FragmentProgramBinding
import com.norbert.koller.teacher.databinding.FragmentStudyGroupTypeBinding
import retrofit2.Response


class StudyGroupSpecificFragment(id : Int? = null) : DetailsFragment(id), ProgramFragmentInterface {

    lateinit var binding : FragmentProgramBinding

    override lateinit var ncwDate : DescriptionView
    override lateinit var ncwTime : DescriptionView
    override lateinit var ncbClassroom : DescriptionButton
    override lateinit var ncbTeacher : DescriptionButton
    override lateinit var toGeneralButton: View

    override fun apiFunctionToCall(): suspend () -> Response<*> {
        return { RetrofitInstance.api.getStudyGroup(viewModel.id!!)}
    }

    override fun createRootView(): View {
        binding = FragmentProgramBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun getTimeLimit(): Int {
        return DateTimeHelper.TIME_IMPORTANT
    }

    override fun getDataType(): Class<*> {
        return StudyGroupData::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.cbEdit.setOnClickListener {
            val intent = Intent(requireContext(), EditStudyGroupActivity::class.java)
            startActivity(intent)
        }

        findViews(view)

        toGeneralButton.setOnClickListener{
            (requireContext() as MainActivity).addFragment(StudyGroupTypeFragment((viewModel.response.value as StudyGroupData).programID))
        }


        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as ProgramData

            (context as MainActivity).setToolbarTitle(response.topic)

            setViews(response, requireContext())

            ncbTeacher.getTextDescription().setOnClickListener{
                teacherClick(response, requireContext())
            }

            ncbClassroom.getTextDescription().setOnClickListener{
                classRoomClick(response, requireContext())
            }

        }
    }

    override fun getFragmentTitle(): String? {
        return getString(com.norbert.koller.shared.R.string.study_group)
    }


}