package com.norbert.koller.shared.activities

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.res.Configuration
import android.graphics.drawable.ColorDrawable
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
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
import com.norbert.koller.shared.fragments.UserFragment
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.managers.camelToSnakeCase
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.managers.setVisibilityBy
import com.norbert.koller.shared.managers.setupPortrait
import com.norbert.koller.shared.managers.toInt
import com.norbert.koller.shared.viewmodels.MainActivityViewModel

import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Calendar


abstract class MainActivity : AppCompatActivity() {

    private lateinit var nfcAdapter: NfcAdapter

    @OptIn(ExperimentalStdlibApi::class)
    private var callback : NfcAdapter.ReaderCallback = NfcAdapter.ReaderCallback { tag : Tag ->


        val mifareClassic = MifareClassic.get(tag)

        try {
            mifareClassic.connect()

            var fullInt = ""

            val sectorSector = 1
            mifareClassic.authenticateSectorWithKeyA(sectorSector, "A0A1A2A3A4A5".hexToByteArray())

            val blockCount = mifareClassic.getBlockCountInSector(sectorSector)
            for (blockIndex in 0 until blockCount) {
                val blockNumber = mifareClassic.sectorToBlock(sectorSector) + blockIndex
                val blockData = mifareClassic.readBlock(blockNumber)
                val hexData = blockData.toInt()
                fullInt += hexData.toString()
            }
            binding.root.post{
                addFragment(ApplicationManager.userFragment(fullInt.dropLast(14).toInt()))
            }


        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            mifareClassic.close()

        }
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val v = bytes[i].toInt() and 0xFF
            hexChars[i * 2] = "0123456789ABCDEF"[v shr 4]
            hexChars[i * 2 + 1] = "0123456789ABCDEF"[v and 0x0F]
        }
        return String(hexChars)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableReaderMode(this)
    }

    override fun onResume() {
        super.onResume()


        nfcAdapter.enableReaderMode(this, callback, NfcAdapter.FLAG_READER_NFC_A, null)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ){
                handleBackPress()
            }
        }
    }

    var defaultTitlePadding : Int = 0

    lateinit var viewModel: MainActivityViewModel
    abstract fun getAppIcon() : Int
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

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
                    LoginActivity()::class.java
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
            binding.appBar?.setExpanded(false)

            showNightBgIfNeeded(id)



            showBackButtonIfNeeded()

            viewModel.lastFragmentId = id

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


    private fun replaceFragmentWithoutBackStack(fragment: Fragment, selectedItemId : Int = bottomNavigationView().selectedItemId) : FragmentTransaction{
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment, "${selectedItemId}${supportFragmentManager.backStackEntryCount}")
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commit()
        return fragmentTransaction
    }


    private fun replaceFragment(fragment: Fragment, selectedItemId : Int = bottomNavigationView().selectedItemId) : FragmentTransaction {
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



    private fun showBackButtonIfNeeded(){

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


}