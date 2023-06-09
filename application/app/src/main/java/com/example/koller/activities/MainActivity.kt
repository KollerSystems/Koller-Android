package com.example.koller.activities

import android.R.attr.button
import android.R.attr.colorForeground
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.utils.widget.MotionLabel
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import com.example.koller.MyApplication
import com.example.koller.R
import com.example.koller.fragments.bottomsheet.ProfileBottomSheet
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView


object MyObject {
    var recreated : Boolean = false
}

class MainActivity : AppCompatActivity() {

    private var recreated: Boolean = false
    lateinit var bottomNavigationView : BottomNavigationView

    public lateinit var userName : String
    lateinit var mainTitle : TextView

    protected fun getNavOptions(): NavOptions {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
            .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
            .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
            .build()

        return navOptions
    }

    private var currentTitle : CharSequence? = ""

    fun setToolbarTitle(title : CharSequence?){

        if (title == currentTitle) return
        currentTitle = title

        var destination : NavDestination = navHostFragment.navController.currentDestination!!

        val ssTitle = SpannableString(title)
        ssTitle.setSpan(StyleSpan(Typeface.BOLD), 0, title!!.length, 0)
        ssTitle.setSpan(ForegroundColorSpan(MyApplication.getAttributeColor(this, colorForeground)), 0, title!!.length, 0)

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

    lateinit var navHostFragment : NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyApplication.setupActivity(window)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBar = findViewById<AppBarLayout>(R.id.appbar_user)
        val mainBackground = findViewById<MaterialCardView>(R.id.main_background)
        val layoutCordinator = findViewById<CoordinatorLayout>(R.id.layout_cordinator)
        mainTitle = findViewById(R.id.toolbar_title)

        val backButton : Button = findViewById<Button>(R.id.toolbar_exit)
        backButton.setOnClickListener(){
            onBackPressed()
        }


        appBar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            var collapsedSize : Float = -570f
            //collapsingToolbarLayout.title = verticalOffset.toString()
            mainBackground.alpha = verticalOffset / collapsedSize
        })

        val motionLayout : MotionLayout = findViewById(R.id.main_motion_layout)
        val listener = AppBarLayout.OnOffsetChangedListener{appBar, verticalOffset ->
            val seekPosition = -verticalOffset /appBar.totalScrollRange.toFloat()
            motionLayout.progress = seekPosition
        }
        appBar.addOnOffsetChangedListener(listener)

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)

        val defaultAppBarHeight = appBar.layoutParams.height
        val defaultTitlePadding = mainTitle.paddingLeft

        navHostFragment.navController.addOnDestinationChangedListener{  controller, destination, arguments ->

            setToolbarTitle(destination.label)


            if(destination.id == R.id.homeFragment ||
                destination.id == R.id.calendarFragment ||
                destination.id == R.id.studentHostelFragment ||
                destination.id == R.id.notificationsFragment){

                backButton.visibility = INVISIBLE
                var dp15 : Int = MyApplication.convertDpToPixel(15, this)
                mainTitle.setPadding(dp15,0,dp15,0)

            }
            else{

                backButton.visibility = VISIBLE
                mainTitle.setPadding(defaultTitlePadding,0,defaultTitlePadding,MyApplication.convertSpToPixel(7, this))
            }

            appBar.setExpanded(false)

        }

        val userButton = findViewById<ShapeableImageView>(R.id.user_button)
        userButton.setOnClickListener(){
            onProfileClick()
        }
    }




    fun onProfileClick(){

        val dialog = ProfileBottomSheet()
        dialog.show(supportFragmentManager, ProfileBottomSheet.TAG)
    }

    public fun NFCSuccess(){
        MaterialAlertDialogBuilder(this@MainActivity)
    .setTitle(R.string.nfc_success)
    .setIcon(R.drawable.done_thick)
    .setPositiveButton(
        R.string.ok
    ) { dialogInterface, i -> }
    .show()
    }
}

fun NavController.navigateWithDefaultAnimation(directions: Int) {
    navigate(directions, null, navOptions {
        anim {
            enter = androidx.navigation.ui.R.anim.nav_default_enter_anim
            exit = androidx.navigation.ui.R.anim.nav_default_exit_anim
            popEnter = androidx.navigation.ui.R.anim.nav_default_enter_anim
            popExit = androidx.navigation.ui.R.anim.nav_default_exit_anim
        }
    })
}