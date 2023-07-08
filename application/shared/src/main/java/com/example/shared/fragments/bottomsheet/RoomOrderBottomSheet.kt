package com.example.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.MyApplication
import com.example.shared.R
import com.example.shared.data.RoomOrderData
import com.example.shared.data.StarData
import com.example.shared.recycleradapter.StarsRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.ArrayList
import kotlin.math.roundToInt

class RoomOrderBottomSheet : BottomSheetDialogFragment()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var starsArrayList: ArrayList<StarData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.bottom_sheet_room_order, container, false)
        MyApplication.setupBottomSheet(dialog!!)
        var finalGrade: TextView = view.findViewById(R.id.room_order_fragment_text_final_rate)
        var teacherName: TextView = view.findViewById(R.id.room_order_fragment_text_rater_name)
        var date: TextView = view.findViewById(R.id.room_order_fragment_text_date)

        var finalGradeFloat : Float = RoomOrderData.instance[0].finalGrade.toFloat() / 2
        if(finalGradeFloat != (finalGradeFloat).roundToInt().toFloat()){
            finalGrade.text = (finalGradeFloat + 0.5f).roundToInt().toString() + ","
        }
        else{
            finalGrade.text = finalGradeFloat.toString()
        }

        teacherName.text = "Name"

        val format = SimpleDateFormat("MM. dd.")
        date.text = format.format(RoomOrderData.instance[0].date)

        val recyclerView : RecyclerView = view.findViewById(R.id.room_order_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        starsArrayList = arrayListOf(
            StarData(getString(R.string.floor), 4f),
            StarData(getString(R.string.beds), 5f)
        )

        var adapter = StarsRecyclerAdapter(starsArrayList)
        recyclerView.adapter = adapter

        return view
    }

    companion object {
        const val TAG = "RoomOrderBottomSheet"
    }
}