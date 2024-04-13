package com.norbert.koller.teacher.fragments


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.norbert.koller.shared.R as Rs
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.NameContentButton
import com.norbert.koller.shared.customviews.NameContentView
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.fragments.DetailsFragment
import com.norbert.koller.shared.fragments.ProgramFragmentInterface
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.formatDate
import com.norbert.koller.shared.managers.setToolbarToViewColor
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.EditStudyGroupActivity
import com.skydoves.androidveil.VeilLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response


class UserPresenceFragment(id : Int? = null) : DetailsFragment(id), ProgramFragmentInterface {


    override lateinit var ncwDate : NameContentView
    override lateinit var ncwTime : NameContentView
    override lateinit var ncbClassroom : NameContentButton
    override lateinit var ncbClass : NameContentButton
    override lateinit var ncbTeacher : NameContentButton

    override fun getDataTag(): String {
        return "program"
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
            Toast.makeText(requireContext(), "Not implemented hahaha", Toast.LENGTH_SHORT).show()
        }

        findViews(view)

        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as ProgramData

            (context as MainActivity).setToolbarTitle(response.topic)

            setViews(response, requireContext())

        }
    }

}