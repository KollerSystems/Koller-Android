package com.norbert.koller.teacher.activities

import android.R.attr.button
import android.R.attr.text
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.blue
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.slider.Slider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.data.RoomOrderConditionsBase
import com.norbert.koller.shared.data.RoomOrderData
import com.norbert.koller.shared.fragments.UserOutgoingPermanentFragment
import com.norbert.koller.shared.fragments.UserOutgoingTemporaryFragment
import com.norbert.koller.shared.fragments.UserOutgoingViewPagerAdapter
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.fragments.FragmentRoomRateContent
import com.norbert.koller.teacher.recycleradapter.RoomRateRecyclerAdapter
import kotlin.math.roundToInt
import com.norbert.koller.shared.R as Rs


class RoomRateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_rate)

        MyApplication.setToolbarToBottomViewColor(findViewById(R.id.bottom_view), window)

        val viewPager : ViewPager2 = findViewById(R.id.view_pager)
        val tabLayout : TabLayout = findViewById(R.id.tab_layout)

        val adapter = RoomRateViewPagerAdapter(this)

        viewPager.adapter = adapter


        TabLayoutMediator(tabLayout, viewPager){tab,position->
            tab.text = (position + 200).toString()
        }.attach()


        val mainBackground : MaterialCardView = findViewById(R.id.main_background)
        val appBar : AppBarLayout = findViewById(R.id.appbar)

        appBar.addOnOffsetChangedListener { _, verticalOffset ->
            val collapsedSize: Float = -570f
            mainBackground.alpha = verticalOffset / collapsedSize
        }
    }
}

class RoomRateViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity)
{
    override fun getItemCount(): Int {
        return 50
    }

    override fun createFragment(position: Int): Fragment {

        return FragmentRoomRateContent()
    }

}