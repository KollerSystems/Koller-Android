package com.norbert.koller.shared.activities

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.animation.doOnEnd
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.camelToSnakeCase
import com.norbert.koller.shared.getColorOfPixel
import com.norbert.koller.shared.getStringResourceByName


abstract class MainActivity : AppCompatActivity() {

    lateinit var toolbarContainer : LinearLayout
    lateinit var toolbarTitle : TextView
    lateinit var toolbarDescription : TextView

    lateinit var bottomNavigationView : BottomNavigationView

    var defaultTitlePadding : Int = 0

    lateinit var appBar : AppBarLayout
    lateinit var backButton : Button

    var mainFragmentList : ArrayList<Int> = arrayListOf()

    var savedBackStacks : MutableSet<Int> = mutableSetOf()

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putIntArray("savedBackStacks", savedBackStacks.toIntArray())
        outState.putIntArray("mainFragmentList", mainFragmentList.toIntArray())
        super.onSaveInstanceState(outState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

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


            changeBackStackState(menuItem.itemId)


            return@setOnItemSelectedListener true
        }

        bottomNavigationView.setOnItemReselectedListener { menuItem ->

            dropAllFragments()

        }

        val userButton = findViewById<ShapeableImageView>(R.id.user_button)
        userButton.setOnClickListener{
            MyApplication.openProfile.invoke(this)
        }


        bottomNavigationView.post{
            val navViewColor = bottomNavigationView.getColorOfPixel(0,0)
            window.navigationBarColor = navViewColor
        }

        if(savedInstanceState == null) {
            changeBackStackState(R.id.home)
        }
        else{
            savedBackStacks = savedInstanceState.getIntArray("savedBackStacks")!!.toMutableSet()
            mainFragmentList = savedInstanceState.getIntArray("mainFragmentList")!!.toCollection(java.util.ArrayList())
            changeToolbarTitleToCurrentFragmentName()
            showBackButtonIfNeeded()
        }

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 112);
    }

    fun showBackButton(show : Boolean){

        val toPadding : Int
        val toAlpha : Float

        if(!show){

            val dp15 : Int = MyApplication.convertDpToPixel(15, this)
            toPadding = dp15
            toAlpha = 0f
            backButton.isClickable = false

        }
        else{

            toPadding = defaultTitlePadding
            toAlpha = 1f
            backButton.isClickable = true
        }
        val animator = ValueAnimator.ofInt(toolbarContainer.paddingRight, toPadding)
        val buttonAnimator = ValueAnimator.ofFloat(backButton.alpha, toAlpha)

        animator.addUpdateListener { valueAnimator -> toolbarContainer.setPadding(valueAnimator.animatedValue as Int, 0, valueAnimator.animatedValue as Int, 0) }
        buttonAnimator.addUpdateListener { valueAnimator -> backButton.alpha = valueAnimator.animatedValue as Float }

        val animationSet = AnimatorSet()
        animationSet.playTogether(
            animator,
            buttonAnimator)
        animationSet.setDuration(resources.getInteger(R.integer.default_transition).toLong())
        animationSet.interpolator = AnimationUtils.loadInterpolator(this, R.anim.ease_out_cubic)
        animationSet.start()
    }



    override fun onBackPressed() {

        appBar.setExpanded(false)
            if (supportFragmentManager.backStackEntryCount > 1) {
                dropLastFragment()

            } else {
                if (mainFragmentList.size == 1) {

                    if (bottomNavigationView.selectedItemId != R.id.home) {
                        bottomNavigationView.selectedItemId = R.id.home
                        mainFragmentList = arrayListOf(0)
                    }
                    else{
                        finish()
                    }

                }
                else{

                    mainFragmentList.removeLast()
                    bottomNavigationView.selectedItemId = mainFragmentList.last()

                }

            }


    }

    fun changeToolbarTitleToCurrentFragmentName(){
        toolbarTitle.post{
            if(supportFragmentManager.backStackEntryCount == 0)
                return@post

            setToolbarTitle(this.getStringResourceByName(supportFragmentManager.fragments[0].javaClass.simpleName.replace("Fragment", "").camelToSnakeCase()), null)
        }
    }

    fun dropLastFragment(){
        supportFragmentManager.popBackStack()

        updateValuesOnFragmentReplace()
        showBackButtonIfNeeded()
    }

    fun dropAllFragments(){
        if(supportFragmentManager.backStackEntryCount <=1)
            return

        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val fragment = when (bottomNavigationView.selectedItemId) {
            R.id.home -> {
                MyApplication.homeFragment()
            }

            R.id.calendar -> {
                MyApplication.calendarFragment()
            }

            R.id.studentHostel -> {
                MyApplication.studentHostelFragment()
            }

            R.id.notifications -> {
                MyApplication.notificationFragment()
            }

            else -> {
                Fragment()
            }
        }

        replaceFragment(fragment)

        updateValuesOnFragmentReplace()
        showBackButton(false)
    }

    fun addFragment(fragment: Fragment){
        replaceFragment(fragment)

        updateValuesOnFragmentReplace()
        showBackButton(true)
    }

    fun changeBackStackState(idToSelect : Int){


        if(idToSelect != bottomNavigationView.selectedItemId) {
            supportFragmentManager.saveBackStack(bottomNavigationView.selectedItemId.toString())
            savedBackStacks.add(bottomNavigationView.selectedItemId)
        }

        supportFragmentManager.restoreBackStack(idToSelect.toString())

        if(!savedBackStacks.contains(idToSelect)) {
            val fragment = when (idToSelect) {
                R.id.home -> {
                    MyApplication.homeFragment()
                }

                R.id.calendar -> {
                    MyApplication.calendarFragment()
                }

                R.id.studentHostel -> {
                    MyApplication.studentHostelFragment()
                }

                R.id.notifications -> {
                    MyApplication.notificationFragment()
                }
                else -> {
                    Fragment()
                }
            }
            replaceFragment(fragment, idToSelect)
        }



        if (mainFragmentList.contains(idToSelect)) {
            mainFragmentList.remove(idToSelect)
        }
        mainFragmentList.add(idToSelect)

        //Toast.makeText(this, mainFragmentList.toString(), Toast.LENGTH_LONG).show()

        updateValuesOnFragmentReplace()
        showBackButtonIfNeeded()

    }

    fun replaceFragment(fragment: Fragment, selectedItemId : Int = bottomNavigationView.selectedItemId){
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.anim_in,
            R.anim.anim_out,
            R.anim.anim_in,
            R.anim.anim_out
        )
        fragmentTransaction.replace(R.id.main_fragment, fragment, "${selectedItemId}+${supportFragmentManager.backStackEntryCount}")
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.addToBackStack(selectedItemId.toString())
        fragmentTransaction.commit()

    }

    fun updateValuesOnFragmentReplace(){
        appBar.setExpanded(false)
        changeToolbarTitleToCurrentFragmentName()
    }

    fun showBackButtonIfNeeded(){
        toolbarTitle.post {
            if (supportFragmentManager.backStackEntryCount == 1) {
                showBackButton(false)
            } else {
                showBackButton(true)
            }
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
}