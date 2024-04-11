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
import com.norbert.koller.shared.customviews.NameContentButton
import com.norbert.koller.shared.customviews.NameContentView
import com.norbert.koller.shared.customviews.SimpleCardButton
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.fragments.ProgramFragmentInterface
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.formatDate
import com.norbert.koller.shared.managers.setToolbarToViewColor
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.EditStudyGroupActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class UserPresenceFragment(val program : ProgramData? = null) : Fragment(), ProgramFragmentInterface {


    override lateinit var ncwDate : NameContentView
    override lateinit var ncwTime : NameContentView
    override lateinit var ncbClassroom : NameContentButton
    override lateinit var ncbClass : NameContentButton
    override lateinit var ncbTeacher : NameContentButton

    lateinit var viewModel : ResponseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_presence, container, false)
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

        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]

        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as ProgramData

            (context as MainActivity).setToolbarTitle(response.topic)

            setViews(response, requireContext())

        }

        if(!viewModel.response.isInitialized) {
            viewModel.response.value = program
        }
    }

}