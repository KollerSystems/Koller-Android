package com.norbert.koller.teacher.activities

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.Slider
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.data.RoomOrderConditionsBase
import com.norbert.koller.shared.data.RoomOrderData
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.recycleradapter.RoomRateRecyclerAdapter
import java.util.Locale
import kotlin.math.roundToInt
import com.norbert.koller.shared.R as Rs


class RoomRateActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_rate)

        MyApplication.setToolbarToBottomViewColor(findViewById(R.id.bottom_view), window)

        val recyclerView : RecyclerView = findViewById(com.norbert.koller.shared.R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this@RoomRateActivity)
        recyclerView.setHasFixedSize(true)

        val data : ArrayList<RoomOrderConditionsBase> = RoomOrderData.instance[0].Conditions


        recyclerView.adapter = RoomRateRecyclerAdapter(data)

        val lyContent : LinearLayout = findViewById(R.id.ly_content)

        val lyFooter : LinearLayout = findViewById(R.id.ly_fixed_footer)



        val chipFinalRateNone : Chip = findViewById(R.id.chip_final_rate_none)
        val chipFinalRateSlider : Chip = findViewById(R.id.chip_final_rate_slider)
        val sliderFinalRate : Slider = findViewById(R.id.slider_final_rate)
        val textFinalRate : TextView = findViewById(R.id.text_final_rate)

        lyContent.post{
            lyContent.setPadding(0,0,0,lyFooter.height)
            textFinalRate.layoutParams.width = textFinalRate.height
        }

        chipFinalRateNone.setOnClickListener{
            chipFinalRateNone.isChecked = true
            chipFinalRateSlider.isChecked = false
            sliderFinalRate.alpha = 0.5f
            textFinalRate.text = getString(Rs.string.none)
        }


        fun setValue(){
            if(sliderFinalRate.value != (sliderFinalRate.value).roundToInt().toFloat()){
                textFinalRate.text = "${(sliderFinalRate.value + 0.5f).roundToInt()},"
            }
            else{
                textFinalRate.text = sliderFinalRate.value.roundToInt().toString()
            }
            chipFinalRateNone.isChecked = false
            chipFinalRateSlider.isChecked = true
            sliderFinalRate.alpha = 1f
        }

        sliderFinalRate.addOnChangeListener{slider, value, bool ->
            setValue()
        }


        sliderFinalRate.setOnTouchListener { view, motionEvent ->


            when(motionEvent.action){
                MotionEvent.ACTION_UP ->{
                    setValue()
                }
            }


            return@setOnTouchListener sliderFinalRate.onTouchEvent(motionEvent)
        }


    }

}