package com.example.koller

import APIInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.*
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object MyObject {
    var recreated : Boolean = false
}

class MainActivity : AppCompatActivity() {

    private var recreated: Boolean = false
    lateinit var bottomNavigationView : BottomNavigationView

    public lateinit var userName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBar = findViewById<AppBarLayout>(R.id.appbar_user)
        val mainBackground = findViewById<MaterialCardView>(R.id.main_background)
        val layoutCordinator = findViewById<CoordinatorLayout>(R.id.layout_cordinator)

        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        //setSupportActionBar(toolbar)

        appBar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            var collapsedSize : Float = -570f
            //collapsingToolbarLayout.title = verticalOffset.toString()
            mainBackground.alpha = verticalOffset / collapsedSize
        })

        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)

        val defaultAppBarHeight = appBar.layoutParams.height

        navHostFragment.navController.addOnDestinationChangedListener{  controller, destination, arguments ->



            collapsingToolbarLayout.title = destination.label.toString()

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