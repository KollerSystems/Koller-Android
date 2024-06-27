package com.norbert.koller.shared.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.view.animation.PathInterpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.core.view.marginBottom
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.WelcomeFragmentBase
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.recycleradapters.WelcomeFragmentAdapter
import com.norbert.koller.shared.R as Rs


class WelcomeActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2
    lateinit var tabs: TabLayout
    var currentFragment : WelcomeFragmentBase? = null
    lateinit var btnForward : Button
    lateinit var btnBackward : Button
    var bottomPadding : Int = 0
    var topPadding : Int = 0

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
                        tab.view.layoutParams.width = ApplicationManager.convertDpToPixel(10, tab.view.context)
                    }

                    tab.view.setPadding(0,0,0,0)
                }
                1->{
                    tab.text = getString(com.norbert.koller.shared.R.string.student_hostel)
                }
                2->{
                    tab.text = getString(com.norbert.koller.shared.R.string.student)
                }
                3->{
                    tab.text = "Gondviselő"
                }
                4->{
                    tab.text = "Közeli rokon"
                }
                5->{
                    tab.text = getString(com.norbert.koller.shared.R.string.school)
                }
                6->{
                    tab.text = "Összegzés"
                }
                7->{
                    tab.view.minimumWidth = 1
                    tab.view.post{
                        tab.view.layoutParams.width = ApplicationManager.convertDpToPixel(10, tab.view.context)
                    }
                    tab.view.setPadding(0,0,0,0)
                }
            }

            tab.view.isClickable = false

        }.attach()

        btnBackward = findViewById(R.id.welcome_backward)
        btnForward = findViewById(R.id.welcome_foreward)

        btnBackward.setOnClickListener {
            if(!btnBackward.isClickable) return@setOnClickListener

            btnForward.isClickable = false
            btnBackward.isClickable = false
            scrollBackward()
        }

        btnForward.setOnClickListener {

            if(!btnForward.isClickable) return@setOnClickListener
            if(viewPager.currentItem != viewPager.adapter!!.itemCount - 1 - 1) {
                btnForward.isClickable = false
                btnBackward.isClickable = false
                scrollForward()
            }
            else{
                MaterialAlertDialogBuilder(this@WelcomeActivity)
                    .setTitle("Mindent megnéztél kétszer is?")
                    .setMessage("Amint a befejezés gombra nyomsz, adataid elküldésre kerülnek, amiket egészen addig nem fogsz tudni módosítani, amíg jelentkezésed elfogadásra nem kerül.")
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

        fun animateNavigation(toTabs : Float, toNavButtons : Float, interpolatorId : Int) : AnimatorSet{
            tabs.layoutParams.height = tabs.height

            val tabAnimator = ObjectAnimator.ofFloat(tabs, "translationY", toTabs)
            val naviButtonAnimator = ObjectAnimator.ofFloat(btnNavigation, "translationY", toNavButtons)

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(tabAnimator, naviButtonAnimator)
            animatorSet.duration = resources.getInteger(com.norbert.koller.shared.R.integer.default_transition).toLong()
            animatorSet.interpolator = AnimationUtils.loadInterpolator(this, interpolatorId)
            return animatorSet
        }

        @SuppressLint("PrivateResource")
        fun animateOutNavigation(){
            val animatorSet = animateNavigation(tabs.layoutParams.height * -1f, defaultBtnNavigationTransY, com.google.android.material.R.interpolator.m3_sys_motion_easing_emphasized_accelerate)
            animatorSet.doOnEnd {
                tabs.visibility = INVISIBLE
                btnNavigation.visibility = INVISIBLE
            }
            animatorSet.start()
        }

        @SuppressLint("PrivateResource")
        fun animateInNavigation(){
            tabs.visibility = VISIBLE
            btnNavigation.visibility = VISIBLE

            val animatorSet = animateNavigation(0f, 0f, com.google.android.material.R.interpolator.m3_sys_motion_easing_emphasized_decelerate)
            animatorSet.start()
        }

        val appPadding = resources.getDimensionPixelSize(com.norbert.koller.shared.R.dimen.application_padding)

        bottomPadding = btnNavigation.layoutParams.height + btnNavigation.marginBottom + appPadding


        val statusBarHeight = resources.getDimensionPixelSize(
            resources.getIdentifier("status_bar_height", "dimen", "android")
        )
        tabs.post {
            topPadding = tabs.height - statusBarHeight + appPadding
        }


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {


                if (position == 0 || position == viewPager.adapter!!.itemCount - 1) {
                    if(navigatedAtLEastOnce)
                        animateOutNavigation()



                }
                else{

                    animateInNavigation()
                }

                navigatedAtLEastOnce = true
                super.onPageSelected(position)
            }


        })
    }

    fun onFragmentResume(){
        btnForward.isClickable = true
        btnBackward.isClickable = true
        if (viewPager.currentItem == 0 + 1){
            btnBackward.text = getString(Rs.string.back)
            btnForward.text = getString(Rs.string.next)
        }
        else if (viewPager.currentItem == viewPager.adapter!!.itemCount - 1 - 1){
            btnForward.text = getString(Rs.string.finnish)
            btnBackward.text = getString(Rs.string.backward)
        }
        else{
            btnBackward.text = getString(Rs.string.backward)
            btnForward.text = getString(Rs.string.next)
        }
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
