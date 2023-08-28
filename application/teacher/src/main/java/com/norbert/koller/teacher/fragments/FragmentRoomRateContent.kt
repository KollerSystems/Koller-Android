package com.norbert.koller.teacher.fragments

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.slider.Slider
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.data.RoomOrderConditionsBase
import com.norbert.koller.shared.data.RoomOrderData
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.recycleradapter.RoomRateRecyclerAdapter
import kotlin.math.roundToInt


class FragmentRoomRateContent : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_rate_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView : RecyclerView = view.findViewById(com.norbert.koller.shared.R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        val data : ArrayList<RoomOrderConditionsBase> = RoomOrderData.instance[0].Conditions


        recyclerView.adapter = RoomRateRecyclerAdapter(data)

        val lyContent : LinearLayout = view.findViewById(R.id.ly_content)

        val lyFooter : LinearLayout = view.findViewById(R.id.ly_fixed_footer)



        val chipFinalRateNone : Chip = view.findViewById(R.id.chip_final_rate_none)
        val chipFinalRateSlider : Chip = view.findViewById(R.id.chip_final_rate_slider)
        val sliderFinalRate : Slider = view.findViewById(R.id.slider_final_rate)
        val textFinalRate : TextView = view.findViewById(R.id.text_final_rate)

        lyContent.post {
            lyContent.setPadding(0, 0, 0, lyFooter.height)
            textFinalRate.layoutParams.width = textFinalRate.height
            textFinalRate.requestLayout()
        }


        chipFinalRateNone.setOnClickListener{
            chipFinalRateNone.isChecked = true
            chipFinalRateSlider.isChecked = false
            sliderFinalRate.alpha = 0.25f
            textFinalRate.text = getString(com.norbert.koller.shared.R.string.none)
            textFinalRate.background = AppCompatResources.getDrawable(requireContext(), com.norbert.koller.shared.R.drawable.circle_outline)
            textFinalRate.backgroundTintList = null

        }


        fun setValue(){
            val roundedToInt = sliderFinalRate.value.roundToInt()
            if(sliderFinalRate.value != roundedToInt.toFloat()){
                textFinalRate.text = "${(sliderFinalRate.value + 0.5f).roundToInt()},"
            }
            else{
                textFinalRate.text = roundedToInt.toString()
            }

            chipFinalRateNone.isChecked = false
            chipFinalRateSlider.isChecked = true
            sliderFinalRate.alpha = 1f

            textFinalRate.background = AppCompatResources.getDrawable(requireContext(), com.norbert.koller.shared.R.drawable.circle)

            var textDrawable: Drawable = textFinalRate.background
            textDrawable = DrawableCompat.wrap(textDrawable)
            var colorAttr = 0


            when(roundedToInt){
                1 ->{
                    colorAttr = com.norbert.koller.shared.R.attr.colorWorstInverse
                }
                2 ->{
                    colorAttr = com.norbert.koller.shared.R.attr.colorBadInverse
                }
                3 ->{
                    colorAttr = com.norbert.koller.shared.R.attr.colorOkInverse
                }
                4 ->{
                    colorAttr = com.norbert.koller.shared.R.attr.colorGoodInverse
                }
                5 ->{
                    colorAttr = com.norbert.koller.shared.R.attr.colorBestInverse
                }
            }
            val color = MyApplication.getAttributeColor(requireContext(), colorAttr)
            DrawableCompat.setTint(textDrawable, Color.argb(122, Color.red(color), Color.green(color), Color.blue(color)))
            textFinalRate.background = textDrawable
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