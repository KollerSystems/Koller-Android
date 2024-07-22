package com.norbert.koller.student.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.view.animation.PathInterpolator
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.marginBottom
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.student.pageradapters.RegisterPagerAdapter
import com.norbert.koller.student.databinding.ActivityRegisterBinding
import com.norbert.koller.shared.R as Rs


class RegisterActivity : AppCompatActivity() {

    var bottomPadding : Int = 0
    var topPadding : Int = 0

    lateinit var binding : ActivityRegisterBinding

    fun scrollForward(){
        binding.viewPager.setCurrentItem(binding.viewPager.currentItem+1, true)
    }

    fun scrollBackward(){
        binding.viewPager.setCurrentItem(binding.viewPager.currentItem-1, true)
    }

    var navigatedAtLEastOnce : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.viewPager.isUserInputEnabled = false

        val adapter = RegisterPagerAdapter(supportFragmentManager, lifecycle)

        binding.viewPager.adapter = adapter


        TabLayoutMediator(binding.tabs, binding.viewPager){tab,position->
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


        binding.buttonBack.setOnClickListener {
            if(!binding.buttonBack.isClickable) return@setOnClickListener

            binding.buttonNext.isClickable = false
            binding.buttonBack.isClickable = false
            scrollBackward()
        }

        binding.buttonNext.setOnClickListener {

            if(!binding.buttonNext.isClickable) return@setOnClickListener
            if(binding.viewPager.currentItem != binding.viewPager.adapter!!.itemCount - 1 - 1) {
                binding.buttonNext.isClickable = false
                binding.buttonBack.isClickable = false
                scrollForward()
            }
            else{
                MaterialAlertDialogBuilder(this@RegisterActivity)
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


        val defaultBtnNavigationTransY : Float = binding.cardButtons.translationY

        //val path = Path().cubicTo(10f, 10f, w, h/2, 10f, h-10f)
        val easeInInterpolator = PathInterpolator(0.550f, 0.055f, 0.675f, 0.190f)
        val easeOutInterpolator = PathInterpolator(0.215f, 0.610f, 0.355f, 1.000f)

        fun animateNavigation(toTabs : Float, toNavButtons : Float, interpolatorId : Int) : AnimatorSet{
            binding.tabs.layoutParams.height = binding.tabs.height

            val tabAnimator = ObjectAnimator.ofFloat(binding.tabs, "translationY", toTabs)
            val naviButtonAnimator = ObjectAnimator.ofFloat(binding.cardButtons, "translationY", toNavButtons)

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(tabAnimator, naviButtonAnimator)
            animatorSet.duration = resources.getInteger(com.norbert.koller.shared.R.integer.default_transition).toLong()
            animatorSet.interpolator = AnimationUtils.loadInterpolator(this, interpolatorId)
            return animatorSet
        }

        @SuppressLint("PrivateResource")
        fun animateOutNavigation(){
            val animatorSet = animateNavigation(binding.tabs.layoutParams.height * -1f, defaultBtnNavigationTransY, com.google.android.material.R.interpolator.m3_sys_motion_easing_emphasized_accelerate)
            animatorSet.doOnEnd {
                binding.tabs.visibility = INVISIBLE
                binding.cardButtons.visibility = INVISIBLE
            }
            animatorSet.start()
        }

        @SuppressLint("PrivateResource")
        fun animateInNavigation(){
            binding.tabs.visibility = VISIBLE
            binding.cardButtons.visibility = VISIBLE

            val animatorSet = animateNavigation(0f, 0f, com.google.android.material.R.interpolator.m3_sys_motion_easing_emphasized_decelerate)
            animatorSet.start()
        }

        val appPadding = resources.getDimensionPixelSize(com.norbert.koller.shared.R.dimen.application_padding)

        bottomPadding = binding.cardButtons.layoutParams.height + binding.cardButtons.marginBottom + appPadding


        val statusBarHeight = resources.getDimensionPixelSize(
            resources.getIdentifier("status_bar_height", "dimen", "android")
        )
        binding.tabs.post {
            topPadding = binding.tabs.height - statusBarHeight + appPadding
        }


        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {


                if (position == 0 || position == binding.viewPager.adapter!!.itemCount - 1) {
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

        onBackPressedDispatcher.addCallback(this){
            if(binding.viewPager.currentItem != 0) {

                if (binding.viewPager.currentItem == binding.tabs.tabCount-1){
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
    }

    fun onFragmentResume(){
        binding.buttonNext.isClickable = true
        binding.buttonBack.isClickable = true
        if (binding.viewPager.currentItem == 0 + 1){
            binding.buttonBack.text = getString(Rs.string.back)
            binding.buttonNext.text = getString(Rs.string.next)
        }
        else if (binding.viewPager.currentItem == binding.viewPager.adapter!!.itemCount - 1 - 1){
            binding.buttonNext.text = getString(Rs.string.finnish)
            binding.buttonBack.text = getString(Rs.string.backward)
        }
        else{
            binding.buttonBack.text = getString(Rs.string.backward)
            binding.buttonNext.text = getString(Rs.string.next)
        }
    }
}
