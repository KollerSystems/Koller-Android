package com.norbert.koller.shared.activities

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.MyApplication.Comp.getPixelColorFromView
import com.norbert.koller.shared.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView


abstract class MainActivity : AppCompatActivity() {

    lateinit var fragmentManager : FragmentContainerView
    lateinit var toolbarContainer : LinearLayout
    lateinit var toolbarTitle : TextView
    lateinit var toolbarDescription : TextView

    lateinit var bottomNavigationView : BottomNavigationView

    lateinit var navController : NavController
    var defaultTitlePadding : Int = 0

    lateinit var appBar : AppBarLayout
    lateinit var backButton : Button


    var onlyIcon : Boolean = false

    fun changeSelectedBottomNavigationIcon(selectedItemId : Int){
        onlyIcon = true
        bottomNavigationView.selectedItemId = selectedItemId
        onlyIcon = false
    }

    fun changeFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            androidx.navigation.ui.R.anim.nav_default_enter_anim,
            androidx.navigation.ui.R.anim.nav_default_exit_anim,
            androidx.navigation.ui.R.anim.nav_default_enter_anim,
            androidx.navigation.ui.R.anim.nav_default_exit_anim
        )
        fragmentTransaction.replace(R.id.main_fragment, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commit()

        appBar.setExpanded(false)
    }

    fun showBackButton(show : Boolean){
        if(!show){

            backButton.visibility = AppBarLayout.INVISIBLE
            val dp15 : Int = MyApplication.convertDpToPixel(15, this)
            toolbarContainer.setPadding(dp15,0,dp15,0)

        }
        else{

            backButton.visibility = AppBarLayout.VISIBLE
            toolbarContainer.setPadding(defaultTitlePadding,0,defaultTitlePadding, 0)
        }
    }


    fun onCreated() {


        fragmentManager = findViewById(R.id.main_fragment)

        appBar = findViewById(R.id.appbar_user)
        val mainBackground = findViewById<MaterialCardView>(R.id.main_background)
        toolbarContainer = findViewById(R.id.toolbar_ly_text_container)
        toolbarTitle = findViewById(R.id.toolbar_title)
        toolbarDescription = findViewById(R.id.toolbar_description)

        backButton = findViewById(R.id.toolbar_exit)
        backButton.setOnClickListener{
            onBackPressed()
        }


        appBar.addOnOffsetChangedListener { _, verticalOffset ->
            val collapsedSize: Float = -570f
            mainBackground.alpha = verticalOffset / collapsedSize
        }

        val motionLayout : MotionLayout = findViewById(R.id.main_motion_layout)
        val listener = AppBarLayout.OnOffsetChangedListener { appBar, verticalOffset ->
            val seekPosition = -verticalOffset / appBar.totalScrollRange.toFloat()
            motionLayout.progress = seekPosition
        }
        appBar.addOnOffsetChangedListener(listener)

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)

        defaultTitlePadding = toolbarContainer.paddingLeft

       bottomNavigationView.setOnItemSelectedListener { menuItem ->

           if(onlyIcon) return@setOnItemSelectedListener true

            when (menuItem.itemId) {

                R.id.home -> {
                    changeFragment(MyApplication.homeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.calendar -> {
                    changeFragment(MyApplication.calendarFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.studentHostel -> {
                    changeFragment(MyApplication.studentHostelFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.notifications -> {
                    changeFragment(MyApplication.notificationFragment())
                    return@setOnItemSelectedListener true
                }


            }
            false
        }

        val userButton = findViewById<ShapeableImageView>(R.id.user_button)
        userButton.setOnClickListener{
            MyApplication.openProfile.invoke(this)
        }


        bottomNavigationView.post{
            val navViewColor = getPixelColorFromView(bottomNavigationView,0,0)
            window.navigationBarColor = navViewColor
        }

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment, MyApplication.homeFragment())
        fragmentTransaction.commit()

    }

    fun setToolbarTitle(title : String?, description : String?){

        if (description.isNullOrBlank()) {
            toolbarDescription.visibility = GONE
        }
        else{
            toolbarDescription.visibility = VISIBLE
            toolbarDescription.text = description
        }
        toolbarTitle.text = title

    }

    fun NFCSuccess(){
        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle(R.string.nfc_success)
            .setIcon(R.drawable.done_thick)
            .setPositiveButton(
                R.string.ok
            ) { dialogInterface, i -> }
            .show()
    }

}