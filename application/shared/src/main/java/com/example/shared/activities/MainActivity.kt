package com.example.shared.activities

import android.graphics.Typeface
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.shared.MyApplication
import com.example.shared.MyApplication.Comp.getPixelColorFromView
import com.example.shared.R
import com.example.shared.fragments.HomeFragment
import com.example.shared.fragments.UsersFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView


abstract class MainActivity : AppCompatActivity() {

    private var currentTitle : CharSequence? = ""
    lateinit var fragmentManager : FragmentContainerView
    lateinit var mainTitle : TextView

    lateinit var bottomNavigationView : BottomNavigationView

    lateinit var navController : NavController
    var defaultTitlePadding : Int = 0

    lateinit var appBar : AppBarLayout
    lateinit var backButton : Button

    public fun ChangeFragment(fragment: Fragment){
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

        if(0 == R.string.home ||
            0 == R.string.calendar ||
            0 == R.string.student_hostel ||
            0 == R.string.notifications){

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

    fun onCreated() {


        fragmentManager = findViewById(R.id.main_fragment)

        appBar = findViewById<AppBarLayout>(R.id.appbar_user)
        val mainBackground = findViewById<MaterialCardView>(R.id.main_background)
        mainTitle = findViewById(R.id.toolbar_title)

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

        defaultTitlePadding = mainTitle.paddingLeft



       bottomNavigationView.setOnItemSelectedListener() { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    ChangeFragment(MyApplication.homeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.calendar -> {
                    ChangeFragment(MyApplication.calendarFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.studentHostel -> {
                    ChangeFragment(MyApplication.studentHostelFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.notifications -> {
                    ChangeFragment(MyApplication.notificationFragment())
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

    }

    fun setToolbarTitle(title : CharSequence?, description : CharSequence?){

        if (title == currentTitle) return
        currentTitle = title

        val ssTitle = SpannableString(title)
        ssTitle.setSpan(StyleSpan(Typeface.BOLD), 0, title!!.length, 0)
        ssTitle.setSpan(ForegroundColorSpan(MyApplication.getAttributeColor(this, R.attr.colorForeground)), 0, title!!.length, 0)

        val nestLabel = description

        if (nestLabel != null && nestLabel != description && nestLabel != "main") {

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
            .setIcon(R.drawable.done_thick)
            .setPositiveButton(
                com.example.shared.R.string.ok
            ) { dialogInterface, i -> }
            .show()
    }

}