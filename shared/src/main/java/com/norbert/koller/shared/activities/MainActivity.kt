package com.norbert.koller.shared.activities

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextSwitcher
import android.widget.TextView
import android.widget.ViewSwitcher
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.camelToSnakeCase
import com.norbert.koller.shared.managers.getColorOfPixel
import com.norbert.koller.shared.managers.getStringResourceByName
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.shared.viewmodels.MainActivityViewModel
import com.squareup.picasso.Picasso
import org.w3c.dom.Text


abstract class MainActivity : AppCompatActivity() {

    lateinit var toolbarContainer : LinearLayout
    lateinit var toolbarTitleSwitcher : TextSwitcher
    lateinit var toolbarDescription : TextView

    lateinit var bottomNavigationView : BottomNavigationView

    var defaultTitlePadding : Int = 0

    lateinit var appBar : AppBarLayout
    lateinit var backButton : Button

    lateinit var viewModel: MainActivityViewModel

    lateinit var mainFragment: FragmentContainerView


    override fun onPostCreate(savedInstanceState: Bundle?) {

        super.onPostCreate(savedInstanceState)
        ApplicationManager.currentContext = this

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        mainFragment = findViewById(R.id.main_fragment)

        backButton = findViewById(R.id.toolbar_exit)
        backButton.setOnClickListener{
            onBackPressed()
        }

        appBar = findViewById(R.id.appbar)
        toolbarContainer = findViewById(R.id.toolbar_ly_text_container)
        toolbarTitleSwitcher = findViewById(R.id.text_switcher)
        toolbarDescription = findViewById(R.id.toolbar_description)
        defaultTitlePadding = toolbarContainer.paddingLeft

        var textView : TextView

        toolbarTitleSwitcher.setFactory {
            textView = TextView(
                this@MainActivity
            )

            textView.setTextAppearance(R.style.MainTitle)
            textView.setTypeface(resources.getFont(R.font.rubik_medium))
            textView
        }

        appBar.setup()

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

        bottomNavigationView.setOnItemReselectedListener {

            dropAllFragments()

        }

        val userImage = findViewById<ImageView>(R.id.user_image)
        val userCard = findViewById<MaterialCardView>(R.id.user_card)
        userCard.setOnClickListener{
            ApplicationManager.openProfile.invoke(this)
        }

        Picasso.get()
            .load(UserData.instance.picture)
            .noPlaceholder()
            .into(userImage)

        bottomNavigationView.post{
            val navViewColor = bottomNavigationView.getColorOfPixel(0,0)
            window.navigationBarColor = navViewColor
        }

        if(savedInstanceState == null) {
            changeBackStackState(R.id.home)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 112)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            setToolbarTitle(this.getStringResourceByName(supportFragmentManager.fragments[0].javaClass.simpleName.replace("Fragment", "").camelToSnakeCase()), null)
            appBar.setExpanded(false)
            showBackButtonIfNeeded()

        }
    }





    override fun onBackPressed() {

        appBar.setExpanded(false)
            if (supportFragmentManager.backStackEntryCount > 1) {
                dropLastFragment()

            } else {
                if (viewModel.mainFragmentList.size == 1) {

                    if (bottomNavigationView.selectedItemId != R.id.home) {
                        bottomNavigationView.selectedItemId = R.id.home
                        viewModel.mainFragmentList = arrayListOf(0)
                    }
                    else{
                        super.onBackPressed()
                        finish()
                    }

                }
                else{

                    viewModel.mainFragmentList.removeLast()
                    bottomNavigationView.selectedItemId = viewModel.mainFragmentList.last()

                }

            }


    }


    fun dropLastFragment(){
        supportFragmentManager.popBackStack()


    }

    fun getSnackBar(text : String, time: Int): Snackbar {

        return Snackbar
            .make(
                mainFragment,
                text,
                time
            )
    }

    fun dropAllFragments(){
        if(supportFragmentManager.backStackEntryCount <=1)
            return

        repeat(supportFragmentManager.backStackEntryCount-1){
            supportFragmentManager.popBackStack()
        }



    }

    fun addFragment(fragment: Fragment) : FragmentTransaction{
        val fragmentTransaction = replaceFragment(fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)




        return fragmentTransaction
    }


    fun changeBackStackState(idToSelect : Int){


        if(idToSelect != bottomNavigationView.selectedItemId) {
            supportFragmentManager.saveBackStack(bottomNavigationView.selectedItemId.toString())

            viewModel.savedBackStacks.add(bottomNavigationView.selectedItemId)
        }

        supportFragmentManager.restoreBackStack(idToSelect.toString())

        if(!viewModel.savedBackStacks.contains(idToSelect)){
            val fragment = when (idToSelect) {
                R.id.home -> {
                    ApplicationManager.homeFragment()
                }

                R.id.calendar -> {
                    ApplicationManager.calendarFragment()
                }

                R.id.studentHostel -> {
                    ApplicationManager.studentHostelFragment()
                }

                R.id.notifications -> {
                    ApplicationManager.notificationFragment()
                }
                else -> {
                    Fragment()
                }
            }

            val fragmentTransaction = replaceFragment(fragment, idToSelect)
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        }







        if (viewModel.mainFragmentList.contains(idToSelect)) {
            viewModel.mainFragmentList.remove(idToSelect)
        }
        viewModel.mainFragmentList.add(idToSelect)





    }


    fun replaceFragmentWithoutBackStack(fragment: Fragment, selectedItemId : Int = bottomNavigationView.selectedItemId) : FragmentTransaction{
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment, fragment, "${selectedItemId}${supportFragmentManager.backStackEntryCount}")
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commit()
        return fragmentTransaction
    }


    fun replaceFragment(fragment: Fragment, selectedItemId : Int = bottomNavigationView.selectedItemId) : FragmentTransaction {
        val fragmentTransaction = replaceFragmentWithoutBackStack(fragment, selectedItemId)
        fragmentTransaction.addToBackStack(selectedItemId.toString())

        return fragmentTransaction
    }

    fun addFragmentWithTransition(fragment: Fragment, view : View, name : String) : FragmentTransaction {

        supportFragmentManager.fragments[0].exitTransition = MaterialElevationScale(/* growing= */ false)

        fragment.sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.main_fragment
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }

        val fragmentTransaction = addFragment(fragment)
        fragmentTransaction.addSharedElement(view, name)

        return fragmentTransaction
    }




    fun showBackButtonIfNeeded(){

        val toPadding: Int
        val toAlpha: Float

        if (supportFragmentManager.backStackEntryCount == 1) {

            val dp15: Int = ApplicationManager.convertDpToPixel(15, this)
            toPadding = dp15
            toAlpha = 0f
            backButton.isClickable = false

        } else {

            toPadding = defaultTitlePadding
            toAlpha = 1f
            backButton.isClickable = true
        }
        val animator = ValueAnimator.ofInt(toolbarContainer.paddingRight, toPadding)
        val buttonAnimator = ValueAnimator.ofFloat(backButton.alpha, toAlpha)

        animator.addUpdateListener { valueAnimator ->
            toolbarContainer.setPadding(
                valueAnimator.animatedValue as Int,
                0,
                valueAnimator.animatedValue as Int,
                0
            )
        }
        buttonAnimator.addUpdateListener { valueAnimator ->
            backButton.alpha = valueAnimator.animatedValue as Float
        }

        val animationSet = AnimatorSet()
        animationSet.playTogether(
            animator,
            buttonAnimator
        )
        animationSet.setDuration(resources.getInteger(R.integer.default_transition).toLong())
        animationSet.interpolator = AnimationUtils.loadInterpolator(
            this,
            com.google.android.material.R.interpolator.m3_sys_motion_easing_emphasized
        )
        animationSet.start()
    }

    fun setToolbarTitle(title : String?, description : String?){

        if(title.toString() == (toolbarTitleSwitcher.currentView as TextView).text.toString() &&
            description.toString() == toolbarDescription.text.toString()){
            return
        }

        if (description.isNullOrBlank()) {
            toolbarDescription.visibility = GONE
        }
        else{
            toolbarDescription.visibility = VISIBLE
            toolbarDescription.text = description
        }
        toolbarTitleSwitcher.setText(title)
        toolbarTitleSwitcher.requestLayout()

    }

    fun NFCSuccess(){
        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle(R.string.nfc_success)
            .setIcon(R.drawable.check_thick)
            .setPositiveButton(
                R.string.ok
            ) { _, _ -> }
            .show()
    }
}