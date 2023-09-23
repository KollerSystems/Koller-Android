package com.norbert.koller.shared.activities

import android.os.Bundle
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
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.MyApplication.Comp.getPixelColorFromView
import com.norbert.koller.shared.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.norbert.koller.shared.FragmentHolderFragment
import com.norbert.koller.shared.fragments.CalendarFragment
import com.norbert.koller.shared.fragments.HomeFragment
import com.norbert.koller.shared.fragments.NotificationsFragment
import com.norbert.koller.shared.fragments.StudentHostelFragment
import kotlinx.coroutines.launch


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

    var mainFragmentList : ArrayList<Int> = arrayListOf(0)


    var onlyIcon : Boolean = false

    fun changeSelectedBottomNavigationIcon(selectedItemId : Int){
        onlyIcon = true
        bottomNavigationView.selectedItemId = selectedItemId
        onlyIcon = false
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

    private lateinit var homeFragment: FragmentHolderFragment
    private lateinit var calendarFragment: FragmentHolderFragment
    private lateinit var studentHostelFragment: FragmentHolderFragment
    private lateinit var notificationsFragment: FragmentHolderFragment

    companion object{
        var selectedIndex = 0
    }



    private val fragments: Array<FragmentHolderFragment>
        get() = arrayOf(
            homeFragment,
            calendarFragment,
            studentHostelFragment,
            notificationsFragment
        )

    override fun onBackPressed() {

        appBar.setExpanded(false)
            if (fragments[selectedIndex].childFragmentManager.backStackEntryCount > 0) {

                fragments[selectedIndex].childFragmentManager.popBackStack()
                backButton.post {
                    changeToolbarTitleToCurrentFragmentName(fragments[selectedIndex].childFragmentManager.fragments[0])
                    if (fragments[selectedIndex].childFragmentManager.backStackEntryCount == 0) {
                        showBackButton(false)
                    }
                }

            } else {
                if (mainFragmentList.size == 1) {

                    if (selectedIndex != 0) {
                        selectFragment(0)
                        mainFragmentList = arrayListOf(0)
                    }
                    else{
                        finish()
                    }

                }
                else{

                    mainFragmentList.removeLast()
                    selectFragment(mainFragmentList.last())
                }

            }



        changeToolbarTitleToCurrentFragmentName(fragments[selectedIndex].childFragmentManager.fragments[0])
    }

    fun changeToolbarTitleToCurrentFragmentName(fragment: Fragment){
        setToolbarTitle(MyApplication.getStringResourceByName(this@MainActivity, MyApplication.camelToSnakeCase(fragment.javaClass.simpleName.replace("Fragment", ""))), null)

    }

    fun changeFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = fragments[selectedIndex].childFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            androidx.navigation.ui.R.anim.nav_default_enter_anim,
            androidx.navigation.ui.R.anim.nav_default_exit_anim,
            androidx.navigation.ui.R.anim.nav_default_enter_anim,
            androidx.navigation.ui.R.anim.nav_default_exit_anim
        )
        fragmentTransaction.replace(R.id.inner_fragment_container_view, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commit()

        appBar.setExpanded(false)

        showBackButton(true)

        changeToolbarTitleToCurrentFragmentName(fragment)
    }

    fun onCreated(savedInstanceState: Bundle?) {

        backButton = findViewById(R.id.toolbar_exit)
        backButton.setOnClickListener{
            onBackPressed()
        }

        appBar = findViewById(R.id.appbar)
        val mainBackground = findViewById<MaterialCardView>(R.id.main_background)
        toolbarContainer = findViewById(R.id.toolbar_ly_text_container)
        toolbarTitle = findViewById(R.id.toolbar_title)
        toolbarDescription = findViewById(R.id.toolbar_description)
        defaultTitlePadding = toolbarContainer.paddingLeft

        if (savedInstanceState == null) {
            val homeFragment = FragmentHolderFragment().also { this.homeFragment = it }
            val calendarFragment = FragmentHolderFragment().also { this.calendarFragment = it }
            val studentHostelFragment = FragmentHolderFragment().also { this.studentHostelFragment = it }
            val notificationsFragment = FragmentHolderFragment().also { this.notificationsFragment = it }


            supportFragmentManager.beginTransaction()
                .add(R.id.main_fragment, homeFragment, "homeFragment")
                .add(R.id.main_fragment, calendarFragment, "calendarFragment")
                .add(R.id.main_fragment, studentHostelFragment, "studentHostelFragment")
                .add(R.id.main_fragment, notificationsFragment, "notificationsFragment")
                .selectFragment(selectedIndex)
                .commit()
        } else {
            homeFragment = supportFragmentManager.findFragmentByTag("homeFragment") as FragmentHolderFragment
            calendarFragment = supportFragmentManager.findFragmentByTag("calendarFragment") as FragmentHolderFragment
            studentHostelFragment = supportFragmentManager.findFragmentByTag("studentHostelFragment") as FragmentHolderFragment
            notificationsFragment = supportFragmentManager.findFragmentByTag("notificationsFragment") as FragmentHolderFragment
        }

        homeFragment.startFragment = MyApplication.homeFragment()
        calendarFragment.startFragment = MyApplication.calendarFragment()
        studentHostelFragment.startFragment = MyApplication.studentHostelFragment()
        notificationsFragment.startFragment = NotificationsFragment()

        fragmentManager = findViewById(R.id.main_fragment)






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



        bottomNavigationView.setOnItemSelectedListener { menuItem ->

            if(onlyIcon) return@setOnItemSelectedListener true

            when (menuItem.itemId) {

                R.id.home -> {

                    selectedIndex = 0
                }
                R.id.calendar -> {

                    selectedIndex = 1
                }
                R.id.studentHostel -> {

                    selectedIndex = 2
                }
                R.id.notifications -> {

                    selectedIndex = 3
                }


            }

            selectFragment(selectedIndex)
            return@setOnItemSelectedListener true
        }

        bottomNavigationView.setOnItemReselectedListener { menuItem ->

            fragments[selectedIndex].toDefaultFragment()
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

    fun setToolbarTitle(title : String?, description : String?){

        if (description.isNullOrBlank()) {
            toolbarDescription.visibility = GONE
        }
        else{
            toolbarDescription.visibility = VISIBLE
            toolbarDescription.text = description
        }
        toolbarTitle.text = title
        toolbarTitle.requestLayout()

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

    private fun selectFragment(indexToSelect: Int) {
        selectedIndex = indexToSelect


        val transaction = supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                androidx.navigation.ui.R.anim.nav_default_enter_anim,
                androidx.navigation.ui.R.anim.nav_default_exit_anim,
                androidx.navigation.ui.R.anim.nav_default_enter_anim,
                androidx.navigation.ui.R.anim.nav_default_exit_anim
            )

            .selectFragment(indexToSelect)
            .commit()

        if(fragments[selectedIndex].childFragmentManager.fragments.size != 0)
            changeToolbarTitleToCurrentFragmentName(fragments[selectedIndex].childFragmentManager.fragments[0])


        if(mainFragmentList.contains(indexToSelect)) mainFragmentList.remove(indexToSelect)
        mainFragmentList.add(indexToSelect)

        val selectedId = when (indexToSelect) {

            0 -> {

                R.id.home
            }
            1 -> {

                R.id.calendar
            }
            2 -> {

                R.id.studentHostel
            }
            3 -> {

                R.id.notifications
            }
            else -> 0
        }
        changeSelectedBottomNavigationIcon(selectedId)

        appBar.setExpanded(false)
        if(fragments[selectedIndex].childFragmentManager.backStackEntryCount == 0){
            showBackButton(false)
        }
        else{
            showBackButton(true)
        }


    }

    private fun FragmentTransaction.selectFragment(selectedIndex: Int): FragmentTransaction {
        fragments.forEachIndexed { index, fragment ->
            if (index == selectedIndex) {
                attach(fragment)
            } else {
                detach(fragment)
            }

        }

        return this
    }

}