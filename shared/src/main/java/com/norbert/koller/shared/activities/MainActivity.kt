package com.norbert.koller.shared.activities

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.animation.doOnEnd
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import com.norbert.koller.shared.KollerHostApduService
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.AuthenticationManager
import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ActivityMainBinding
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.managers.camelToSnakeCase
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.managers.getStringResourceByName
import com.norbert.koller.shared.managers.setVisibilityBy
import com.norbert.koller.shared.managers.setupPortrait
import com.norbert.koller.shared.viewmodels.MainActivityViewModel

import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.util.Calendar


abstract class MainActivity : AppCompatActivity() {

    var defaultTitlePadding : Int = 0

    lateinit var viewModel: MainActivityViewModel
    abstract fun getAppIcon() : Int
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun bottomNavigationView() : NavigationBarView{
        return (binding.navigationView as NavigationBarView)
    }

    override fun onStart() {
        super.onStart()
        binding.stars.onStart()
    }

    override fun onStop() {
        binding.stars.onStop()
        super.onStop()
    }

    val backgroundAnimatorIn = ValueAnimator.ofFloat(0f, 1f)
    val backgroundAnimatorOut = ValueAnimator.ofFloat(1f, 0f)

    override fun onPostCreate(savedInstanceState: Bundle?) {

        super.onPostCreate(savedInstanceState)





        AuthenticationManager.handleFailedTokenRefresh = {

            lifecycleScope.launch {
                DataStoreManager.remove(this@MainActivity, DataStoreManager.TOKENS)
                finishAffinity()
                ApplicationManager.openActivity(
                    this@MainActivity,
                    ApplicationManager.loginActivity()::class.java
                )
            }

        }

        AuthenticationManager.handleRefreshedTokenSaving = {
            lifecycleScope.launch {
                DataStoreManager.save(
                    this@MainActivity,
                    LoginTokensData.instance!!
                )
            }
        }

        KollerHostApduService.handleNFC = { uid ->
            addFragment(ApplicationManager.userFragment(uid))
        }

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]


        defaultTitlePadding = binding.lyTitleContainer.paddingLeft

        var textView : TextView

        binding.textSwitcher.setFactory {
            textView = TextView(
                this@MainActivity
            )

            textView.setTextAppearance(R.style.MainTitle)
            textView.setTypeface(resources.getFont(R.font.rubik_medium))
            textView
        }
        binding.textSwitcher.measureAllChildren = false




        val landscape = (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
        if(!landscape){
            binding.appBar!!.setupPortrait()
            val listener = AppBarLayout.OnOffsetChangedListener { appBar, verticalOffset ->
                val seekPosition = -verticalOffset / appBar.totalScrollRange.toFloat()
                binding.motionLayout!!.progress = seekPosition
            }
            binding.appBar!!.addOnOffsetChangedListener(listener)
        }
        else{
            binding.imageAppIcon!!.setImageResource(getAppIcon())
            window.statusBarColor = this.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainer)
        }

        binding.buttonBack.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }



        bottomNavigationView().setOnItemSelectedListener { menuItem ->


            changeBackStackState(menuItem.itemId)


            return@setOnItemSelectedListener true
        }

        bottomNavigationView().setOnItemReselectedListener {

            dropAllFragments()

        }

        binding.cardUser.setOnClickListener{
            ApplicationManager.openProfile.invoke(this)
        }

        Picasso.get()
            .load(UserData.instance.picture)
            .noPlaceholder()
            .into(binding.imageUser)


        if(savedInstanceState == null) {
            changeBackStackState(R.id.home)
            binding.textTitleDescription.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            viewModel.descriptionHeight = binding.textTitleDescription.measuredHeight;
        }
        else{
            val id = supportFragmentManager.fragments[0].javaClass.simpleName.replace("Fragment", "").camelToSnakeCase()
            val title = this.getStringResourceByName(id)
            setToolbarTitle(title)
            showNightBgIfNeeded(id)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 112)
        }

        binding.appBar?.setExpanded(false)
        showBackButtonIfNeeded()

        backgroundAnimatorIn.addUpdateListener { valueAnimator ->
            binding.stars.alpha = valueAnimator.animatedValue as Float
        }
        backgroundAnimatorOut.addUpdateListener { valueAnimator ->
            binding.stars.alpha = valueAnimator.animatedValue as Float
        }
        backgroundAnimatorIn.duration = 200
        backgroundAnimatorOut.duration = 200

        supportFragmentManager.addOnBackStackChangedListener {
            val id = supportFragmentManager.fragments[0].javaClass.simpleName.replace("Fragment", "").camelToSnakeCase()
            val title = this.getStringResourceByName(id)
            if(!title.isNullOrBlank()) {
                setToolbarTitle(title)
            }
            binding.appBar?.setExpanded(false)

            showNightBgIfNeeded(id)



            showBackButtonIfNeeded()

            viewModel.lastFragmentId = id

        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {


            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ){
                handleBackPress()
            }
        }
    }

    private fun handleBackPress(){
        binding.appBar?.setExpanded(false)
        if (supportFragmentManager.backStackEntryCount > 1) {
            dropLastFragment()

        } else {
            if (viewModel.mainFragmentList.size == 1) {

                if (bottomNavigationView().selectedItemId != R.id.home) {
                    bottomNavigationView().selectedItemId = R.id.home
                    viewModel.mainFragmentList = arrayListOf(0)
                }
                else{

                    finish()
                }

            }
            else{

                viewModel.mainFragmentList.removeLast()
                bottomNavigationView().selectedItemId = viewModel.mainFragmentList.last()

            }

        }
    }



    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        handleBackPress()
    }


fun showNightBgIfNeeded(id : String){
    when (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
        Configuration.UI_MODE_NIGHT_YES -> {
            val c: Calendar = Calendar.getInstance()
            val hours: Float =
                ((c.get(Calendar.SECOND) / 60f / 60f + c.get(Calendar.MINUTE) / 60f + c.get(
                    Calendar.HOUR_OF_DAY
                ))) + SettingsActivity.timeOffset
            if (id == "home" && (hours > 22 || hours < 3)) {
                binding.appBar?.background = AppCompatResources.getDrawable(this, R.drawable.separator)
                backgroundAnimatorOut.cancel()
                binding.stars.setVisibilityBy(true)
                backgroundAnimatorIn.start()

            } else if (viewModel.lastFragmentId == "home" && binding.stars.visibility == VISIBLE) {
                binding.appBar?.background =
                    ColorDrawable(getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainer))
                backgroundAnimatorIn.cancel()
                backgroundAnimatorOut.doOnEnd {
                    binding.stars.setVisibilityBy(false)
                }
                backgroundAnimatorOut.reverse()
                backgroundAnimatorOut.start()
            }
        }
        Configuration.UI_MODE_NIGHT_NO -> {}
        Configuration.UI_MODE_NIGHT_UNDEFINED -> {}
    }
}

    fun dropLastFragment(){
        supportFragmentManager.popBackStack()


    }

    fun getSnackBar(text : String, time: Int): Snackbar {

        return Snackbar
            .make(
                binding.fragmentContainer,
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


        if(idToSelect != bottomNavigationView().selectedItemId) {
            supportFragmentManager.saveBackStack(bottomNavigationView().selectedItemId.toString())

            viewModel.savedBackStacks.add(bottomNavigationView().selectedItemId)
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


    fun replaceFragmentWithoutBackStack(fragment: Fragment, selectedItemId : Int = bottomNavigationView().selectedItemId) : FragmentTransaction{
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment, "${selectedItemId}${supportFragmentManager.backStackEntryCount}")
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commit()
        return fragmentTransaction
    }


    fun replaceFragment(fragment: Fragment, selectedItemId : Int = bottomNavigationView().selectedItemId) : FragmentTransaction {
        val fragmentTransaction = replaceFragmentWithoutBackStack(fragment, selectedItemId)
        fragmentTransaction.addToBackStack(selectedItemId.toString())

        return fragmentTransaction
    }

    fun addFragmentWithTransition(fragment: Fragment, view : View, name : String) : FragmentTransaction {



        fragment.sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragment_container
            fadeMode = MaterialContainerTransform.FADE_MODE_CROSS
        }

        val fragmentTransaction = addFragment(fragment)
        fragmentTransaction.addSharedElement(view, name)

        return fragmentTransaction
    }




    @SuppressLint("PrivateResource")
    fun showBackButtonIfNeeded(){

        val toPadding: Int
        val toAlpha: Float

        if (supportFragmentManager.backStackEntryCount == 1) {

            toPadding = resources.getDimensionPixelSize(R.dimen.card_padding)
            toAlpha = 0f
            binding.buttonBack.isClickable = false

        } else {

            toPadding = defaultTitlePadding
            toAlpha = 1f
            binding.buttonBack.isClickable = true
        }

        val buttonAnimator = ValueAnimator.ofFloat(binding.buttonBack.alpha, toAlpha)
        val animator : ValueAnimator
        if(binding.imageAppIcon == null) {
            animator = ValueAnimator.ofInt(binding.lyTitleContainer.paddingRight, toPadding)
            animator.addUpdateListener { valueAnimator ->
                binding.lyTitleContainer.setPadding(
                    valueAnimator.animatedValue as Int,
                    0,
                    valueAnimator.animatedValue as Int,
                    0
                )
            }
        }
        else{
            animator = ValueAnimator.ofFloat(binding.imageAppIcon!!.alpha, (toAlpha - 1) * -1)
            animator.addUpdateListener { valueAnimator ->
                binding.imageAppIcon!!.alpha = valueAnimator.animatedValue as Float
            }
        }
        buttonAnimator.addUpdateListener { valueAnimator ->
            binding.buttonBack.alpha = valueAnimator.animatedValue as Float
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

    @SuppressLint("PrivateResource")
    fun setToolbarTitle(title : String?, description : String = ""){

        val descriptionChanges = description != binding.textTitleDescription.text.toString()
        if(title == (binding.textSwitcher.currentView as TextView).text.toString() && !descriptionChanges) {

            return
        }

        Log.d("RETGIJASDIKUFHASDIOLCHBHJYXFV", "${description} :::::: ${binding.textTitleDescription.text.toString()}")

        if(descriptionChanges) {
            if (description.isBlank()) {

                val animator = ValueAnimator.ofInt(binding.textTitleDescription.height, 0)
                animator.addUpdateListener { valueAnimator ->
                    binding.textTitleDescription.height = valueAnimator.animatedValue as Int
                }

                animator.setDuration(resources.getInteger(R.integer.default_transition).toLong())
                animator.interpolator = AnimationUtils.loadInterpolator(
                    this,
                    com.google.android.material.R.interpolator.m3_sys_motion_easing_emphasized
                )
                animator.start()

            } else if(binding.textTitleDescription.text.toString().isBlank()) {

                val animator = ValueAnimator.ofInt(binding.textTitleDescription.height, viewModel.descriptionHeight)
                animator.addUpdateListener { valueAnimator ->
                    binding.textTitleDescription.height = valueAnimator.animatedValue as Int
                }

                animator.setDuration(resources.getInteger(R.integer.default_transition).toLong())
                animator.interpolator = AnimationUtils.loadInterpolator(
                    this,
                    com.google.android.material.R.interpolator.m3_sys_motion_easing_emphasized
                )
                animator.start()
            }
            binding.textTitleDescription.text = description
        }
        binding.textSwitcher.setText(title)

        binding.textSwitcher.requestLayout()

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