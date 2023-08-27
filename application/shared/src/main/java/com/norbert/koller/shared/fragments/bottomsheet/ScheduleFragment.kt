package com.norbert.koller.shared.fragments.bottomsheet

import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.CreateNewPostActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker

class ScheduleFragment : BottomSheetDialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_schedule, container, false)

        val tilDate : TextInputLayout = view.findViewById(R.id.schedule_til_date)
        val tilTime : TextInputLayout = view.findViewById(R.id.schedule_til_time)

        val parentActivity = (requireActivity() as CreateNewPostActivity)

        if(parentActivity.scheduleDate != 0L){
            val calendar: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = parentActivity.scheduleDate

            tilDate.editText!!.setText(MyApplication.simpleLocalDateFormat.format(calendar.time))
        }
        if(parentActivity.scheduleTime != 0){

            val hours: Int = parentActivity.scheduleTime / 60
            val minutes: Int = parentActivity.scheduleTime % 60

            tilTime.editText!!.setText("${hours.toString().padStart(2,'0')}:${minutes.toString().padStart(2,'0')}")
        }

        val buttonSchedule : Button = view.findViewById(R.id.schedule_button)

        buttonSchedule.setOnClickListener{
            dismiss()
            requireActivity().finish()
        }

        fun checkInputsRefreshButton(){
            buttonSchedule.isEnabled = (tilDate.editText!!.text?.length ?: 0) > 0 && (tilTime.editText!!.text?.length ?: 0) > 0
        }

        checkInputsRefreshButton()

        tilDate.editText!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                checkInputsRefreshButton()
            }
        })

        tilTime.editText!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                checkInputsRefreshButton()
            }
        })


        val dpd =  MaterialDatePicker.Builder.datePicker()
            .build()

        tilDate.editText!!.setOnClickListener{

            dpd.show(requireActivity().supportFragmentManager,  "MATERIAL_DATE_PICKER")

        }

        dpd.addOnPositiveButtonClickListener {

            parentActivity.scheduleDate = it

            tilDate.editText!!.setText(dpd.headerText)
        }

        val tpd =  MaterialTimePicker.Builder()
            .build()

        tilTime.editText!!.setOnClickListener{
            tpd.show(requireActivity().supportFragmentManager,  "MATERIAL_TIME_PICKER")
        }

        tpd.addOnPositiveButtonClickListener {

            parentActivity.scheduleTime = tpd.hour * 60 + tpd.minute

            tilTime.editText!!.setText(MyApplication.timeToString(tpd.hour, tpd.minute))
        }

        return view
    }

    companion object {
        const val TAG = "ScheduleBottomSheet"
    }
}