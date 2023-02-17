package com.example.koller

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.PathInterpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class WelcomeActivity : AppCompatActivity() {

    public lateinit var viewPager: ViewPager2
    public lateinit var tabs: TabLayout

    public fun ScrollForward(){
        viewPager!!.setCurrentItem(viewPager!!.currentItem+1, true)
    }

    public fun ScrollBackward(){
        viewPager!!.setCurrentItem(viewPager!!.currentItem-1, true)
    }

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
                    tab.view.setPadding(0,0,0,0)
                }
                1->{
                    tab.text = "Új jelszó"
                }
                2->{
                    tab.text = "Alapvető adatok"
                }
                3->{
                    tab.text = "Érdeklődési köre"
                }
                4->{
                    tab.text = "Személyiséged"
                }
                5->{
                    tab.view.minimumWidth = 1
                    tab.view.setPadding(0,0,0,0)
                }
            }
        }.attach()

        val btnBackward : Button = findViewById(R.id.welcome_backward)
        val btnForward : Button = findViewById(R.id.welcome_foreward)

        btnBackward.setOnClickListener(){
            ScrollBackward()
        }

        btnForward.setOnClickListener(){
            ScrollForward()
        }


        val defaultTabTransY : Float = tabs.translationY
        val defaultBtnNavigationTransY : Float = btnNavigation.translationY

        //val path = Path().cubicTo(10f, 10f, w, h/2, 10f, h-10f)
        val easeInInterpolator = PathInterpolator(0.550f, 0.055f, 0.675f, 0.190f)
        val easeOutInterpolator = PathInterpolator(0.215f, 0.610f, 0.355f, 1.000f)

        fun animateOutNavigation(){
            ObjectAnimator.ofFloat(tabs, "translationY", defaultTabTransY).apply {
                duration = 250
                interpolator = easeInInterpolator
                start()
            }

            ObjectAnimator.ofFloat(btnNavigation, "translationY", defaultBtnNavigationTransY).apply {
                duration = 250
                interpolator = easeInInterpolator
                start()

                doOnEnd {
                    tabs.visibility = GONE
                    btnNavigation.visibility = GONE
                }
            }
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0 || position == 5) {
                    animateOutNavigation()
                }
                else{

                    if (position == 0 + 1){
                        btnBackward.text = getString(R.string.back)
                        btnForward.text = getString(R.string.next)
                    }
                    else if (position == 5 - 1){
                        btnForward.text = getString(R.string.finnish)
                        btnBackward.text = getString(R.string.backward)
                    }
                    else{
                        btnBackward.text = getString(R.string.backward)
                        btnForward.text = getString(R.string.next)
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
                super.onPageSelected(position)



            }
        })

    }
    //override fun onDestroy() {
    //    super.onDestroy()
    //    viewPager.unregisterOnPageChangeCallback(this)
    //}
}
