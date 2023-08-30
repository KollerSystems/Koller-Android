package com.norbert.koller.teacher.activities

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.slider.Slider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.data.RoomOrderConditionsBase
import com.norbert.koller.shared.data.RoomOrderData
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragment
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.recycleradapter.RoomRateRecyclerAdapter
import kotlin.math.roundToInt
import com.norbert.koller.shared.R as Rs


class RoomRateActivity : AppCompatActivity() {

    override fun onBackPressed() {

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.are_you_sure_discard_all_grade))
            .setPositiveButton(getString(Rs.string.yes)) { _, _ ->
                super.onBackPressed()
            }
            .setNegativeButton(getString(Rs.string.no)){ _, _ ->

            }
            .setNeutralButton(getString(Rs.string.create_a_draft)){ _, _ ->
                super.onBackPressed()
            }
            .show()


    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_rate)

        MyApplication.setToolbarToBottomViewColor(findViewById(R.id.bottom_view), window)

        val viewPager : ViewPager2 = findViewById(R.id.view_pager)
        val tabLayout : TabLayout = findViewById(R.id.tab_layout)

        val adapter = RoomRateViewPagerAdapter()

        viewPager.adapter = adapter


        TabLayoutMediator(tabLayout, viewPager){tab,position->
            tab.text = (position + 200).toString()
        }.attach()


        val mainBackground : MaterialCardView = findViewById(R.id.main_background)
        val appBar : AppBarLayout = findViewById(R.id.appbar)

        MyApplication.setupActivityToolbar(mainBackground,appBar)

        val backButton : Button = findViewById(R.id.toolbar_exit)

        backButton.setOnClickListener{
            onBackPressed()
        }

        val buttonPublishAll : Button = findViewById(R.id.button_publish_all)
        val buttonMore : Button = findViewById(R.id.button_more)

        buttonPublishAll.setOnClickListener{
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.are_you_sure_publish_all_grade))
                .setPositiveButton(getString(Rs.string.yes)) { _, _ ->
                    finish()
                }
                .setNegativeButton(getString(Rs.string.no)){ _, _ ->

                }
                .show()
        }

        buttonMore.setOnClickListener{
            val dialog = ItemListDialogFragment()
            dialog.show(supportFragmentManager, ItemListDialogFragment.TAG)

            dialog.list = arrayListOf(
                ListItem({
                    MaterialAlertDialogBuilder(this)
                        .setTitle(getString(R.string.are_you_sure_delete_all_grade))
                        .setPositiveButton(getString(Rs.string.yes)) { _, _ ->

                        }
                        .setNegativeButton(getString(Rs.string.no)){ _, _ ->

                        }
                        .show()
                }, getString(Rs.string.delete_all), null, AppCompatResources.getDrawable(this@RoomRateActivity, Rs.drawable.close))
            )
        }
    }
}

class RoomRateViewPagerAdapter() : RecyclerView.Adapter<RoomRateViewPagerAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_room_rate_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //val currentItem = roomOrderConditionsData[position]




        holder.recyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        holder.recyclerView.setHasFixedSize(true)

        val data : ArrayList<RoomOrderConditionsBase> = RoomOrderData.instance[0].Conditions


        holder.recyclerView.adapter = RoomRateRecyclerAdapter(data)


        var finalRateChanged : Boolean = false

        holder.lyContent.post {
            holder.lyContent.setPadding(0, 0, 0, holder.lyFooter.height)
            holder.textFinalRate.layoutParams.width = holder.textFinalRate.height
            holder.textFinalRate.requestLayout()
        }








        fun setValue(){
            val roundedToInt = holder.sliderFinalRate.value.roundToInt()
            if(holder.sliderFinalRate.value != roundedToInt.toFloat()){
                holder.textFinalRate.text = "${(holder.sliderFinalRate.value + 0.5f).roundToInt()},"
            }
            else{
                holder.textFinalRate.text = roundedToInt.toString()
            }

            holder.chipFinalRateSlider.isChecked = true
            holder.sliderFinalRate.alpha = 1f

            holder.textFinalRate.background = AppCompatResources.getDrawable(holder.itemView.context, com.norbert.koller.shared.R.drawable.circle)

            var textDrawable: Drawable = holder.textFinalRate.background
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
            val color = MyApplication.getAttributeColor(holder.itemView.context, colorAttr)
            DrawableCompat.setTint(textDrawable, Color.argb(122, Color.red(color), Color.green(color), Color.blue(color)))
            holder.textFinalRate.background = textDrawable
        }

        holder.sliderFinalRate.addOnChangeListener{slider, value, bool ->
            setValue()
            finalRateChanged = true
        }


        holder.sliderFinalRate.setOnTouchListener { view, motionEvent ->


            when(motionEvent.action){
                MotionEvent.ACTION_UP ->{

                    holder.chipFinalRateSlider.post{

                        if(!finalRateChanged) {

                            if (!holder.chipFinalRateSlider.isChecked) {
                                setValue()
                            } else {
                                holder.chipFinalRateSlider.isChecked = false
                                holder.sliderFinalRate.alpha = 0.25f
                                holder.textFinalRate.text = holder.itemView.context.getString(com.norbert.koller.shared.R.string.none)
                                holder.textFinalRate.background = AppCompatResources.getDrawable(holder.itemView.context, com.norbert.koller.shared.R.drawable.circle_outline)
                                holder.textFinalRate.backgroundTintList = null
                            }


                        }
                        else{
                            finalRateChanged = false
                        }
                    }





                }
            }


            return@setOnTouchListener holder.sliderFinalRate.onTouchEvent(motionEvent)
        }
    }

    override fun getItemCount(): Int {
        return 100
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val recyclerView : RecyclerView = itemView.findViewById(com.norbert.koller.shared.R.id.recycler_view)
        val lyContent : LinearLayout = itemView.findViewById(R.id.ly_content)

        val lyFooter : LinearLayout = itemView.findViewById(R.id.ly_fixed_footer)


        val chipFinalRateSlider : Chip = itemView.findViewById(R.id.chip_final_rate_slider)
        val sliderFinalRate : Slider = itemView.findViewById(R.id.slider_final_rate)
        val textFinalRate : TextView = itemView.findViewById(R.id.text_final_rate)
    }


}