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


class UserPresenceFragment(val program : ProgramData? = null) : Fragment() {


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

        val ss = SpannableString("Az alábbi adatok csak erre a kiválasztott szakkörre vonatkoznak. Szakkör megtekintése")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                Toast.makeText(requireContext(), "Not implemented hahaha", Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        ss.setSpan(clickableSpan, 65, 85, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val textView = view.findViewById(R.id.Text_hint) as TextView
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()

        val ncwDate : NameContentView = view.findViewById(com.norbert.koller.shared.R.id.ncw_date)
        val ncwTime : NameContentView = view.findViewById(com.norbert.koller.shared.R.id.ncw_time)
        val ncbClassroom : NameContentButton = view.findViewById(com.norbert.koller.shared.R.id.ncb_classroom)
        val ncbClass : NameContentButton = view.findViewById(com.norbert.koller.shared.R.id.ncb_class)
        val ncbTeacher : NameContentButton = view.findViewById(com.norbert.koller.shared.R.id.ncb_teacher)

        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]

        viewModel.response.observe(viewLifecycleOwner) {response ->

            response as ProgramData


            (context as MainActivity).setToolbarTitle(response.topic)



            ncwDate.textContent.text = response.date.formatDate(DateTimeHelper.monthDay)
            ncwTime.textContent.text = ApplicationManager.createClassesText(requireContext(), response.lesson, response.length)
            ncbClassroom.buttonContent.text = response.rid.toString()

            if(response is BaseProgramData) {
                ncbClass.visibility = View.VISIBLE
                ncbClass.buttonContent.text = response.class_.class_
                ncbClass.buttonContent.setOnClickListener {
                    val userFragment = ApplicationManager.usersFragment(null)
                        .setFilter("Class.ID", response.class_.id.toString())
                    (requireContext() as MainActivity).addFragment(userFragment)
                }
            }


            ncbTeacher.buttonContent.text = response.teacher!!.name.toString()

            ncbClassroom.buttonContent.setOnClickListener {
                (requireContext() as MainActivity).addFragment(ApplicationManager.roomFragment(response.rid))
            }

            ncbTeacher.buttonContent.setOnClickListener {

                (requireContext() as MainActivity).addFragment(ApplicationManager.userFragment(response.teacher!!.uid))
            }

        }

        if(!viewModel.response.isInitialized) {
            viewModel.response.value = program
        }
    }

}