package com.example.koller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBar = findViewById<AppBarLayout>(R.id.appbar)

        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationView.setupWithNavController(navController)

        navHostFragment.navController.addOnDestinationChangedListener{  controller, destination, arguments ->



            collapsingToolbarLayout.title = destination.label.toString()
            appBar.setExpanded(false, true)
        }

        val userButton = findViewById<ShapeableImageView>(R.id.user_button)
        userButton.setOnClickListener(){
            onProfileClick()
        }
    }

    fun onProfileClick(){

        /*MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle(R.string.nfc_success)
            .setIcon(R.drawable.done_thick)
            .setPositiveButton(
                R.string.ok
            ) { dialogInterface, i -> }
            .show()*/


        val dialog = ProfileBottomSheet()
        dialog.show(supportFragmentManager, ProfileBottomSheet.TAG)
    }
}