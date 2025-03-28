package com.norbert.koller.shared.activities

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewGroup.VISIBLE
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.animation.doOnEnd
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateMargins
import androidx.core.view.updatePadding
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import com.norbert.koller.shared.CacheActivity
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.AuthenticationManager
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ActivityMainBinding
import com.norbert.koller.shared.fragments.CalendarFragment
import com.norbert.koller.shared.fragments.FragmentInMainActivity
import com.norbert.koller.shared.fragments.HomeFragment
import com.norbert.koller.shared.fragments.StatisticsFragment
import com.norbert.koller.shared.helpers.ApiHelper
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.managers.DataStoreManager.Companion.loginDataStore
import com.norbert.koller.shared.managers.DataStoreManager.Companion.tipDataStore
import com.norbert.koller.shared.managers.camelToSnakeCase
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.managers.setVisibilityBy
import com.norbert.koller.shared.managers.setupPortrait
import com.norbert.koller.shared.viewmodels.MainActivityViewModel
import com.norbert.koller.shared.widgets.CanteenWidgetProvider
import com.norbert.koller.shared.widgets.NowWidgetProvider
import com.norbert.koller.shared.widgets.WidgetHelper
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Calendar


abstract class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel
    private lateinit var binding : ActivityMainBinding
    private var nfcAdapter: NfcAdapter? = null
    var isKeyboardShowing: Boolean = false
    var defaultTitlePadding : Int = 0
    var statusBarHeight : Int = 0
    private lateinit var connectivityManager: ConnectivityManager
    val backgroundAnimatorIn = ValueAnimator.ofFloat(0f, 1f)
    val backgroundAnimatorOut = ValueAnimator.ofFloat(1f, 0f)

    var onEditModeChange: ((Boolean) -> Unit)? = null
    var onCancelEditMode: (() -> Unit)? = null

    @OptIn(ExperimentalStdlibApi::class)
    private var nfcCallback : NfcAdapter.ReaderCallback = NfcAdapter.ReaderCallback { tag : Tag ->

        val mifareClassic = MifareClassic.get(tag)

        try {
            mifareClassic.connect()

            val sectorIndex = 1

            mifareClassic.authenticateSectorWithKeyA(sectorIndex, "A0A1A2A3A4A5".hexToByteArray())

            val blockIndex = mifareClassic.sectorToBlock(sectorIndex)
            val blockData = mifareClassic.readBlock(blockIndex)

            binding.root.post{
                val fragment = ApplicationManager.userFragment()
                val bundle = Bundle()
                bundle.putInt("id", byteArrayToInt(blockData))
                fragment.arguments = bundle
                addFragment(fragment)
                viewModel.currentBottomSheetDialogFragment?.dismiss()
                //TODO: ellenőrizni, hogy vannak-e alert dialogok, amik ilyenkor bent ragadhatnak, vagy igazából bármi.
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            mifareClassic.close()
        }
    }

    abstract fun getAppIcon() : Int

    fun getNavigationView() : NavigationBarView{
        return (binding.navigationView as NavigationBarView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootView: View = findViewById(android.R.id.content)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        fun showNavigationViewLabelIfNeeded(){
            lifecycleScope.launch {

                var opened = 0
                val data = tipDataStore.data.first()[intPreferencesKey("APP_OPEN_COUNT")]
                if(data != null) opened = data
                if(opened < 10){
                    getNavigationView().labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
                }
                else{
                    getNavigationView().itemPaddingBottom = 0
                    getNavigationView().itemPaddingTop = 0
                }

                if(savedInstanceState == null){

                    opened++
                    tipDataStore.edit {
                        it[intPreferencesKey("APP_OPEN_COUNT")] = opened
                    }
                }
            }
        }

        fun handleKeyboardSize(){
            rootView.viewTreeObserver.addOnGlobalLayoutListener(
                OnGlobalLayoutListener {
                    val r = Rect()
                    rootView.getWindowVisibleDisplayFrame(r)
                    val screenHeight: Int = rootView.rootView.height

                    val keypadHeight = screenHeight - r.bottom

                    if (keypadHeight > screenHeight * 0.15) {
                        // keyboard is opened
                        if (!isKeyboardShowing) {
                            isKeyboardShowing = true
                            keyboardVisibilityChanged(true)
                        }
                    } else {
                        // keyboard is closed
                        if (isKeyboardShowing) {
                            isKeyboardShowing = false
                            keyboardVisibilityChanged(false)
                        }
                    }
                }
            )
        }

        fun handleConnectivity(){
            if(binding.cl != null){
                ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
                    statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top
                    binding.internetStatus.updatePadding(top = binding.internetStatus.paddingBottom + statusBarHeight)
                    insets
                }
            }

            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()

            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    runOnUiThread {
                        onOnline()
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    runOnUiThread {
                        onOffline()
                    }
                }
            }

            connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
            connectivityManager.requestNetwork(networkRequest, networkCallback)
        }

        binding.textSwitcher.measureAllChildren = false
        setTextSwitcherFactory(R.font.rubik_medium)

        showNavigationViewLabelIfNeeded()

        handleConnectivity()

        handleKeyboardSize()

        if(binding.navigationView is BottomNavigationView){
            binding.manageBar.root.isClickable = false
            binding.manageBar.button.isClickable = false
            binding.manageBar.root.isVisible = true
            binding.manageBar.root.alpha = 0f
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        defaultTitlePadding = binding.lyTitleContainer.paddingLeft

        fun refreshUserData(){
            if(CacheManager.currentUserId == -2) return
            RetrofitInstance.communicate(lifecycleScope,
                RetrofitInstance.api::getCurrentUser,
                {
                    it as UserData
                    it.saveReceivedTime()
                    CacheManager.updateCurrentUserData(it)
                },
                { _, _ ->
                    ApiHelper.createSnackBar(this, getString(R.string.failed_to_refresh_user)){
                        refreshUserData()
                    }
                })

        }

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

        if(ApplicationManager.isOnline(this)){
            onOnline()
        }
        else{
            onOffline()
        }

        AuthenticationManager.handleFailedTokenRefresh = {
            lifecycleScope.launch {
                loginDataStore.edit {
                    it.remove(DataStoreManager.TOKENS)
                }
                finishAffinity()
                ApplicationManager.openLogin(this@MainActivity)
            }
        }

        AuthenticationManager.handleRefreshedTokenSaving = {
            lifecycleScope.launch {
                DataStoreManager.saveTokens(this@MainActivity)
            }
        }

        if(binding.appBar != null){
            binding.appBar!!.setupPortrait()
            val listener = AppBarLayout.OnOffsetChangedListener { appBar, verticalOffset ->
                val seekPosition = -verticalOffset / appBar.totalScrollRange.toFloat()
                binding.motionLayout!!.progress = seekPosition
            }
            binding.appBar!!.addOnOffsetChangedListener(listener)
        }
        else{
            binding.imageAppIcon!!.setImageResource(getAppIcon())
        }

        binding.buttonBack.setOnClickListener{
            handleBackPress()
        }

        getNavigationView().setOnItemSelectedListener { menuItem ->
            changeBackStackState(menuItem.itemId)
            return@setOnItemSelectedListener true
        }

        getNavigationView().setOnItemReselectedListener {
            dropAllFragments()
        }

        binding.cardUser.setOnLongClickListener{
            ApplicationManager.openActivity(this, CacheActivity::class.java)
            return@setOnLongClickListener true
        }

        binding.cardUser.setOnClickListener{
            ApplicationManager.openProfile.invoke(this)
        }

        Picasso.get()
            .load(CacheManager.getCurrentUserData()?.picture)
            .noPlaceholder()
            .into(binding.imageUser)

        if(savedInstanceState == null) {
            if(!CacheManager.getCurrentUserData()!!.isValid(this, DateTimeHelper.TIME_IMPORTANT)) {
                refreshUserData()
            }
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

    override fun onPause() {
        super.onPause()

        nfcAdapter?.disableReaderMode(this)
    }

    override fun onResume() {
        super.onResume()

        fun handleWidgetAction(){

            viewModel.currentBottomSheetDialogFragment?.dismiss()
            //TODO: ellenőrizni, hogy vannak-e alert dialogok, amik ilyenkor bent ragadhatnak, vagy igazából bármi.

            when (WidgetHelper.widgetTag) {
                NowWidgetProvider.TAG -> {
                    if (getNavigationView().selectedItemId != R.id.home) {
                        getNavigationView().selectedItemId = R.id.home
                    }

                    getNavigationView().post {
                        if (supportFragmentManager.fragments[0] !is HomeFragment) {
                            addFragment(ApplicationManager.homeFragment())
                        }
                    }
                }
                CanteenWidgetProvider.TAG -> {
                    if (getNavigationView().selectedItemId != R.id.calendar) {
                        getNavigationView().selectedItemId = R.id.calendar
                    }

                    getNavigationView().post {
                        if (supportFragmentManager.fragments[0] !is CalendarFragment) {
                            addFragment(ApplicationManager.calendarFragment())
                        }

                        getNavigationView().post {
                            getNavigationView().post {
                                (supportFragmentManager.fragments[0] as CalendarFragment).getViewPager().currentItem = 2
                            }
                        }
                    }
                }
            }
            WidgetHelper.widgetTag = null
        }

        nfcAdapter?.enableReaderMode(this, nfcCallback, NfcAdapter.FLAG_READER_NFC_A, null)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ){
                handleBackPress()
            }
        }

        if (WidgetHelper.widgetTag != null) {
            handleWidgetAction()
        }
    }

    override fun onStart() {
        super.onStart()
        binding.stars.onStart()
    }

    override fun onStop() {
        binding.stars.onStop()
        super.onStop()
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        handleBackPress()
    }

    private fun handleBackPress(){

        if(onCancelEditMode != null){
            onCancelEditMode!!.invoke()
            disableEditMode()
            return
        }

        binding.appBar?.setExpanded(false)
        if (supportFragmentManager.backStackEntryCount > 1) {
            dropLastFragment()
        } else {
            if (viewModel.mainFragmentList.size == 1) {
                if (getNavigationView().selectedItemId != R.id.home) {
                    getNavigationView().selectedItemId = R.id.home
                    viewModel.mainFragmentList = arrayListOf(0)
                }
                else{
                    finish()
                }
            }
            else{
                viewModel.mainFragmentList.removeAt(viewModel.mainFragmentList.size-1)
                getNavigationView().selectedItemId = viewModel.mainFragmentList.last()
            }
        }
    }

    fun keyboardVisibilityChanged(opened: Boolean) {

        if(binding.cl != null){
            (binding.cl!!.layoutParams as MarginLayoutParams).updateMargins(bottom = if (opened) 0 else resources.getDimensionPixelSize(R.dimen.header_footer_size))
            binding.cl!!.forceLayout()

            binding.manageBar.root.isVisible = !opened
            binding.navigationView.isVisible = !opened
        }
    }

    fun byteArrayToInt(bytes: ByteArray): Int {
        var value = 0
        for (b in bytes) {
            value = (value shl 8) + (b.toInt() and 0xFF)
        }
        return value
    }

    fun setTextSwitcherFactory(fontId : Int){

        binding.textSwitcher.removeAllViews()

        var textView : TextView
        binding.textSwitcher.setFactory {
            textView = TextView(this@MainActivity)
            textView.setTextAppearance(R.style.MainTitle)
            textView.typeface = resources.getFont(fontId)
            textView
        }
    }

    fun enableEditMode(){

        onEditModeChange?.invoke(true)

        ManageActivity.displayButton(binding.manageBar.button, getString(R.string.remove), R.drawable.delete_forever)

        binding.cardUser.isClickable = false
        binding.cardUser.isEnabled = false

        binding.manageBar.root.isClickable = true
        binding.manageBar.button.isClickable = true

        binding.buttonBack.setIconResource(R.drawable.close)

        animateEditMode(0f, 0, resources.getDimensionPixelSize(R.dimen.header_footer_size))
    }


    fun disableEditMode(){

        onEditModeChange?.invoke(false)
        onCancelEditMode = null

        binding.cardUser.isClickable = true
        binding.cardUser.isEnabled = true

        binding.manageBar.root.isClickable = false
        binding.manageBar.button.isClickable = false

        binding.buttonBack.setIconResource(R.drawable.arrow_back)

        setToolbarTitle((supportFragmentManager.fragments[0] as FragmentInMainActivity).getFragmentTitle())

        animateEditMode(1f, ApplicationManager.convertDpToPixel(80, this), 0)
    }

    fun animateEditMode(toAlpha : Float, toWidth : Int, toHeight : Int){

        //TODO: kitalálni egy jó módját annak, hogy ne lehessen kattintani a navigation view-t, ameddig átvált

        val animationSet = AnimatorSet()

        val userAnimator = ValueAnimator.ofFloat(binding.cardUser.alpha, toAlpha)
        userAnimator.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Float
            binding.cardUser.alpha = value
        }

        if(binding.navigationView is BottomNavigationView){
            val bottomNavigationViewAnimator = ValueAnimator.ofFloat(binding.manageBar.root.alpha, (toAlpha - 1) * -1)
            bottomNavigationViewAnimator.addUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Float
                binding.manageBar.root.alpha = value
            }

            animationSet.playTogether(
                userAnimator,
                bottomNavigationViewAnimator,
            )
        }
        else{
            val manageBarAnimator = ValueAnimator.ofInt(binding.manageBar.root.height, toHeight)
            manageBarAnimator.addUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Int
                binding.manageBar.root.layoutParams.height = value
                binding.lyChange!!.requestLayout()
            }

            val navigationRailAnimator = ValueAnimator.ofInt(binding.navigationView.width, toWidth)
            navigationRailAnimator.addUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Int
                binding.navigationView.layoutParams.width = value

            }

            animationSet.playTogether(
                userAnimator,
                navigationRailAnimator,
                manageBarAnimator
            )
        }

        animationSet.duration = resources.getInteger(R.integer.default_transition).toLong()
        animationSet.interpolator = AnimationUtils.loadInterpolator(
            this,
            com.google.android.material.R.interpolator.m3_sys_motion_easing_emphasized
        )
        animationSet.start()
    }

    fun onOnline(){
        binding.internetStatus.isVisible = false
        if(binding.cl != null){
            binding.cl?.updatePadding(top = 0)
        }
        else{
            window.statusBarColor = getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainer)
        }
    }

    fun onOffline(){
        binding.internetStatus.isVisible = true
        if(binding.cl != null){
            binding.internetStatus.post{
                binding.cl!!.updatePadding(top = binding.internetStatus.height - binding.internetStatus.paddingTop + binding.internetStatus.paddingBottom)
            }
        }
        else{
            window.statusBarColor = getAttributeColor(com.google.android.material.R.attr.colorErrorContainer)
        }
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

    fun addFragment(fragment: Fragment) : FragmentTransaction{
        val fragmentTransaction = replaceFragment(fragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

        return fragmentTransaction
    }

    fun dropLastFragment(){
        supportFragmentManager.popBackStack()
    }

    fun dropAllFragments(){
        if(supportFragmentManager.backStackEntryCount <=1)
            return

        repeat(supportFragmentManager.backStackEntryCount-1){
            supportFragmentManager.popBackStack()
        }
    }

    fun changeBackStackState(idToSelect : Int){

        if(idToSelect != getNavigationView().selectedItemId) {
            supportFragmentManager.saveBackStack(getNavigationView().selectedItemId.toString())
            viewModel.savedBackStacks.add(getNavigationView().selectedItemId)
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

                R.id.browse -> {
                    ApplicationManager.browseFragment()
                }

                R.id.statistics -> {
                    StatisticsFragment()
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

    private fun replaceFragmentWithoutBackStack(fragment: Fragment, selectedItemId : Int = getNavigationView().selectedItemId) : FragmentTransaction{

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment, "${selectedItemId}${supportFragmentManager.backStackEntryCount}")
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commit()
        return fragmentTransaction
    }

    private fun replaceFragment(fragment: Fragment, selectedItemId : Int = getNavigationView().selectedItemId) : FragmentTransaction {

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
    fun setToolbarTitle(title : String?, description : String = ""){

        val descriptionChanges = description != binding.textTitleDescription.text.toString()
        if(title == (binding.textSwitcher.currentView as TextView).text.toString() && !descriptionChanges)
            return

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

    fun getSnackBar(text : String, time: Int): Snackbar {
        return Snackbar.make(binding.fragmentContainer, text, time)
    }
}