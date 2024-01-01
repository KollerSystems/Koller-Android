package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.RoomOrderData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.managers.setupBottomSheet
import java.text.SimpleDateFormat
import java.util.ArrayList
import kotlin.math.roundToInt

class RoomOrderBottomSheet : BottomSheetDialogFragment()  {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_bshd_room_order, container, false)
        dialog!!.setupBottomSheet()
        val finalGrade: TextView = view.findViewById(R.id.room_order_fragment_text_final_rate)
        val teacherName: TextView = view.findViewById(R.id.room_order_fragment_text_rater_name)
        val date: TextView = view.findViewById(R.id.room_order_fragment_text_date)

        val finalGradeFloat : Float = RoomOrderData.instance[0].FinalGrade.toFloat() / 2
        if(finalGradeFloat != (finalGradeFloat).roundToInt().toFloat()){
            finalGrade.text = "${(finalGradeFloat + 0.5f).roundToInt()},"
        }
        else{
            finalGrade.text = finalGradeFloat.toString()
        }

        teacherName.text = "Name"

        val format = SimpleDateFormat("MM. dd.")
        date.text = format.format(RoomOrderData.instance[0].date)

        val RecyclerView : RecyclerView = view.findViewById(R.id.room_order_recycler_view)

        RecyclerView.layoutManager = LinearLayoutManager(context)
        RecyclerView.setHasFixedSize(true)

        /*starsArrayList = arrayListOf(
            StarData(getString(R.string.floor), 4f),
            StarData(getString(R.string.beds), 5f)
        )*/

        //val adapter = StarsRecyclerAdapter(starsArrayList)
        //RecyclerView.adapter = adapter

        return view
    }

    companion object {
        const val TAG = "RoomOrderBottomSheet"
    }
}