package com.norbert.koller.student.activities

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.PathInterpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.student.R
import com.norbert.koller.shared.R as Rs
import com.norbert.koller.student.fragments.WelcomeFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabView
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.MyApplication


class WelcomeActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2
    lateinit var tabs: TabLayout

    fun scrollForward(){
        viewPager.setCurrentItem(viewPager.currentItem+1, true)
    }

    fun scrollBackward(){
        viewPager.setCurrentItem(viewPager.currentItem-1, true)
    }

    var navigatedAtLEastOnce : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        tabs = findViewById(R.id.welcome_tabs)
        val btnNavigation: CardView = findViewById(R.id.welcome_btn_navigation)
        viewPager = findViewById(R.id.welcome_view_pager)
        viewPager.isUserInputEnabled = false

        val adapter = WelcomeFragmentAdapter(supportFragmentManager, lifecycle)

        viewPager.adapter = adapter


        TabLayoutMediator(tabs, viewPager){tab,position->
            when(position){
                0->{
                    tab.view.minimumWidth = 1
                    tab.view.post{
                        tab.view.layoutParams.width = MyApplication.convertDpToPixel(10, tab.view.context)
                    }

                    tab.view.setPadding(0,0,0,0)
                }
                1->{
                    tab.text = "Intézmény azonosító"
                }
                2->{
                    tab.text = "Adatok"
                }
                3->{
                    tab.text = "Személyes preferenciák"
                }
                4->{
                    tab.text = "Összegzés"
                }
                5->{
                    tab.text = "Jelszó"
                }
                6->{
                    tab.view.minimumWidth = 1
                    tab.view.post{
                        tab.view.layoutParams.width = MyApplication.convertDpToPixel(10, tab.view.context)
                    }
                    tab.view.setPadding(0,0,0,0)
                }
            }

            tab.view.isClickable = false

        }.attach()

        val btnBackward : Button = findViewById(R.id.welcome_backward)
        val btnForward : Button = findViewById(R.id.welcome_foreward)

        btnBackward.setOnClickListener {
            scrollBackward()
        }

        btnForward.setOnClickListener {
            if(viewPager.currentItem != viewPager.adapter!!.itemCount - 1 - 1) {
                scrollForward()
            }
            else{
                MaterialAlertDialogBuilder(this@WelcomeActivity)
                    .setTitle("Mindent megnéztél kétszer is?")
                    .setMessage("Amint a befejezés gombra nyomsz, adataid elküldésre kerülnek, amiket egészen addig nem fogsz tudni módosítani, amíg fel nem vesznek.")
                    .setPositiveButton(getString(Rs.string.finnish)) { _, _ ->
                        scrollForward()
                    }
                    .setNegativeButton(getString(Rs.string.cancel)) { _, _ ->

                    }
                    .show()
            }
        }


        val defaultBtnNavigationTransY : Float = btnNavigation.translationY

        //val path = Path().cubicTo(10f, 10f, w, h/2, 10f, h-10f)
        val easeInInterpolator = PathInterpolator(0.550f, 0.055f, 0.675f, 0.190f)
        val easeOutInterpolator = PathInterpolator(0.215f, 0.610f, 0.355f, 1.000f)

        fun animateOutNavigation(){
            tabs.layoutParams.height = tabs.height

            ObjectAnimator.ofFloat(tabs, "translationY", tabs.layoutParams.height * -1f).apply {
                duration = 250
                interpolator = easeInInterpolator
                start()
            }

            ObjectAnimator.ofFloat(btnNavigation, "translationY", defaultBtnNavigationTransY).apply {
                duration = 250
                interpolator = easeInInterpolator
                start()

                doOnEnd {
                    tabs.visibility = INVISIBLE
                    btnNavigation.visibility = INVISIBLE
                }
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0 || position == viewPager.adapter!!.itemCount - 1) {
                    if(navigatedAtLEastOnce)
                        animateOutNavigation()
                }
                else{

                    if (position == 0 + 1){
                        btnBackward.text = getString(Rs.string.back)
                        btnForward.text = getString(Rs.string.next)
                    }
                    else if (position == viewPager.adapter!!.itemCount - 1 - 1){
                        btnForward.text = getString(Rs.string.finnish)
                        btnBackward.text = getString(Rs.string.backward)
                    }
                    else{
                        btnBackward.text = getString(Rs.string.backward)
                        btnForward.text = getString(Rs.string.next)
                    }

                    tabs.visibility = VISIBLE
                    btnNavigation.visibility = VISIBLE

                    ObjectAnimator.ofFloat(tabs, "translationY", 0f).apply {
                        duration = 250
                        interpolator = easeOutInterpolator
                        start()
                    }

                    ObjectAnimator.ofFloat(btnNavigation, "translationY", 0f).apply {
                        duration = 250
                        interpolator = easeOutInterpolator
                        start()
                    }
                }

                navigatedAtLEastOnce = true
                super.onPageSelected(position)

            }


        })
    }

    override fun onBackPressed() {
        if(viewPager.currentItem != 0) {

            if (viewPager.currentItem == tabs.tabCount-1){
                finishAffinity()

            }
            else{
                scrollBackward()
            }
        }
        else{
            finish()
        }
    }
    //override fun onDestroy() {
    //    super.onDestroy()
    //    viewPager.unregisterOnPageChangeCallback(this)
    //}
}
