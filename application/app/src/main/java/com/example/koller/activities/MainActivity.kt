package com.example.koller.activities

import android.R.attr.button
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.utils.widget.MotionLabel
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
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

    protected fun getNavOptions(): NavOptions {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
            .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
            .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
            .build()

        return navOptions
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MyApplication.setupActivity(window)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBar = findViewById<AppBarLayout>(R.id.appbar_user)
        val mainBackground = findViewById<MaterialCardView>(R.id.main_background)
        val layoutCordinator = findViewById<CoordinatorLayout>(R.id.layout_cordinator)
        val mainTitle : MotionLabel = findViewById(R.id.toolbar_title)

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

        navHostFragment.navController.addOnDestinationChangedListener{  controller, destination, arguments ->



            mainTitle.setText(destination.label.toString())






            if(destination.id == R.id.homeFragment ||
                destination.id == R.id.calendarFragment ||
                destination.id == R.id.studentHostelFragment ||
                destination.id == R.id.notificationsFragment){


            }
            else{



            }


            if(destination.id == R.id.userFragment){
                appBar.setExpanded(true, false)
                appBar.layoutParams.height = 0
                layoutCordinator.fitsSystemWindows = false
                appBar.visibility = INVISIBLE
            }
            else{
                appBar.visibility = VISIBLE
                layoutCordinator.fitsSystemWindows = true
                appBar.layoutParams.height = defaultAppBarHeight
                appBar.setExpanded(false, true)

            }
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