package com.example.shared.activities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Typeface
import android.os.Bundle
import android.os.Environment
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.shared.MyApplication
import com.example.shared.MyApplication.Comp.getPixelColorFromView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import java.io.File
import com.example.shared.R


abstract class MainActivity : AppCompatActivity() {

    private var currentTitle : CharSequence? = ""
    lateinit var navHostFragment : NavHostFragment
    lateinit var mainTitle : TextView

    lateinit var bottomNavigationView : BottomNavigationView

    lateinit var navController : NavController



    fun onCreated() {


        navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBar = findViewById<AppBarLayout>(R.id.appbar_user)
        val mainBackground = findViewById<MaterialCardView>(R.id.main_background)
        mainTitle = findViewById(R.id.toolbar_title)

        val backButton : Button = findViewById(R.id.toolbar_exit)
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
        bottomNavigationView.setupWithNavController(navController)

        val defaultAppBarHeight = appBar.layoutParams.height
        val defaultTitlePadding = mainTitle.paddingLeft

        navHostFragment.navController.addOnDestinationChangedListener{  controller, destination, arguments ->

            setToolbarTitle(destination.label)


            if(destination.id == R.id.homeFragment ||
                destination.id == R.id.calendarFragment ||
                destination.id == R.id.studentHostelFragment ||
                destination.id == R.id.notificationsFragment){

                backButton.visibility = AppBarLayout.INVISIBLE
                var dp15 : Int = MyApplication.convertDpToPixel(15, this)
                mainTitle.setPadding(dp15,0,dp15,0)

            }
            else{

                backButton.visibility = AppBarLayout.VISIBLE
                mainTitle.setPadding(defaultTitlePadding,0,defaultTitlePadding,
                    MyApplication.convertSpToPixel(7, this))
            }

            appBar.setExpanded(false)

        }

        val userButton = findViewById<ShapeableImageView>(R.id.user_button)
        userButton.setOnClickListener{
            MyApplication.openProfile.invoke(this)
        }


        bottomNavigationView.post{
            val navViewColor = getPixelColorFromView(bottomNavigationView,0,0)
            window.navigationBarColor = navViewColor
        }

    }

    fun setToolbarTitle(title : CharSequence?){

        if (title == currentTitle) return
        currentTitle = title

        var destination : NavDestination = navHostFragment.navController.currentDestination!!

        val ssTitle = SpannableString(title)
        ssTitle.setSpan(StyleSpan(Typeface.BOLD), 0, title!!.length, 0)
        ssTitle.setSpan(ForegroundColorSpan(MyApplication.getAttributeColor(this, R.attr.colorForeground)), 0, title!!.length, 0)

        val nestLabel = destination.parent?.label

        if (nestLabel != null && nestLabel != destination.label && nestLabel != "main") {

            val ssNest = SpannableString(nestLabel)
            ssNest.setSpan(RelativeSizeSpan(0.666f), 0, nestLabel.length, 0) // set size

            ssNest.setSpan(StyleSpan(0), 0, nestLabel.length, 0)
            mainTitle.text = TextUtils.concat(ssNest, "\n", ssTitle)
        }
        else{
            mainTitle.text = ssTitle
        }

    }

    public fun NFCSuccess(){
        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle(com.example.shared.R.string.nfc_success)
            .setIcon(com.example.shared.R.drawable.done_thick)
            .setPositiveButton(
                com.example.shared.R.string.ok
            ) { dialogInterface, i -> }
            .show()
    }

}