package com.example.koller

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBar = findViewById<AppBarLayout>(R.id.appbar)
        val mainBackground = findViewById<MaterialCardView>(R.id.main_background)

        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        //setSupportActionBar(toolbar)

        appBar.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            var collapsedSize : Float = -570f
            //collapsingToolbarLayout.title = verticalOffset.toString()
            mainBackground.alpha = verticalOffset / collapsedSize
        })

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