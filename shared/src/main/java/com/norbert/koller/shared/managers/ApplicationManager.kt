package com.norbert.koller.shared.managers

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.chip.Chip
import com.google.android.material.color.DynamicColors
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.CalendarFragment
import com.norbert.koller.shared.fragments.HomeFragment
import com.norbert.koller.shared.fragments.NotificationsFragment
import com.norbert.koller.shared.fragments.OutgoingListFragment
import com.norbert.koller.shared.fragments.RoomFragment
import com.norbert.koller.shared.fragments.RoomListFragment
import com.norbert.koller.shared.fragments.RoomTidinessListFragment
import com.norbert.koller.shared.fragments.StudentHostelFragment
import com.norbert.koller.shared.fragments.StudyGroupTypeListFragment
import com.norbert.koller.shared.fragments.UserFragment
import com.norbert.koller.shared.fragments.UserListFragment
import com.norbert.koller.shared.helpers.DateTimeHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.nio.ByteBuffer
import java.util.Date
import java.util.Locale


open class ApplicationManager : Application() {

    private val TAG: String = ApplicationManager::class.java.getSimpleName()
    private var mVisibleCount = 0
    private var mInBackground = false

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)


    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            }

            override fun onActivityStarted(activity: Activity) {
                mVisibleCount++
                if (mInBackground && mVisibleCount > 0) {
                    mInBackground = false
                    Log.i(TAG, "App in foreground")
                }
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityStopped(activity: Activity) {
                mVisibleCount--
                if (mVisibleCount == 0) {
                    if (activity.isFinishing) {
                        Log.i(TAG, "App is finishing")
                    } else {
                        mInBackground = true
                        Log.i(TAG, "App in background")

                        applicationScope.launch {
                            DataStoreManager.saveCache(applicationContext)
                        }

                    }
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })


    }

    fun isAppInBackground(): Boolean {
        return mInBackground
    }

    fun isAppVisible(): Boolean {
        return mVisibleCount > 0
    }

    fun getVisibleCount(): Int {
        return mVisibleCount
    }

    companion object Comp{

        lateinit var version : String



        lateinit var openSettings: (context : Context) -> Unit
        lateinit var openLogin: (context : Context) -> Unit
        lateinit var openMain: (context : Context) -> Unit
        lateinit var openProfile: (context : Context) -> Unit

        lateinit var homeFragment: () -> HomeFragment
        lateinit var calendarFragment: () -> CalendarFragment
        lateinit var studentHostelFragment: () -> StudentHostelFragment
        lateinit var notificationFragment: () -> NotificationsFragment
        lateinit var roomTidinessListFragment: () -> RoomTidinessListFragment

        lateinit var roomFragment: () -> RoomFragment
        lateinit var userFragment: () -> UserFragment

        lateinit var getAppColor: (context : Context) -> Int

        var roomListFragment: () -> RoomListFragment = { RoomListFragment() }
        var userListFragment: () -> UserListFragment = { UserListFragment() }

        lateinit var studyGroupsFragment: () -> StudyGroupTypeListFragment

        var outgoingListFragment: () -> OutgoingListFragment = { OutgoingListFragment() }

        const val minLengthBeforeDismiss : Int = 3

        fun searchApiWithRegex(searchValue : String) : String{
            return "/${searchWithRegex(searchValue)}/"
        }

        fun searchWithRegex(searchValue : String) : String{
            return /*"(?i)*/"\\b(?:\\S*[_ -])*${Regex.escape(searchValue.trim())}\\S*\\b"
        }

        fun openActivity(context : Context, className : Class<*>){
            val intent = Intent(context, className)
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        fun waitForChange(onChange : (() -> Unit)?, vararg editTexts: EditText) {

            for (editText in editTexts) {
                editText.doOnTextChanged { _, _, _, _ ->
                    onChange?.invoke()
                }
            }
        }

        fun waitForChange(onChange : (() -> Unit)?, vararg textInputLayouts: TextInputLayout) {

            for (textInputLayout in textInputLayouts) {
                textInputLayout.editText!!.doOnTextChanged { _, _, _, _ ->
                    onChange?.invoke()
                }
            }
        }

        fun allFilled(vararg textInputLayouts: TextInputLayout) : Boolean{
            var isAllFilled = true
            for (textInputLayout in textInputLayouts){
                if(textInputLayout.editText!!.text.isNullOrBlank()){
                    isAllFilled = false
                }
            }
            return isAllFilled
        }

        fun setClipboard(context: Context, text: String) {

            val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("Koller", text)
            clipboard.setPrimaryClip(clip)
        }

        fun longEnoughToAsk(til : TextInputLayout): Boolean{
            return til.editText!!.text.toString().replace(" ", "").length>= minLengthBeforeDismiss
        }

        fun convertDpToPixel(dp: Int, context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                context.resources.displayMetrics
            ).toInt()
        }

        fun convertSpToPixel(sp: Int, context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                sp.toFloat(),
                context.resources.displayMetrics
            ).toInt()
        }

        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {

                        return true
                    }
                }
            }
            return false
        }

        fun createClassesText(context: Context, lesson : Int, length : Int) : String{

            var description = ""

            for (i in lesson..<lesson+length){

                val orderedNumber = (i + 1).orderSingleNumber(context)


                if(i < lesson+length-1-1) {

                    description += "${orderedNumber}, "
                }
                else if (i < lesson+length-1){
                    description += "${orderedNumber} ${context.getString(R.string.and)} "
                }
                else{
                    description += "${orderedNumber} ${ context.getString(R.string.lesson).lowercase()}"
                }
            }

            return description
        }

        fun openAppOrPlayStore(context : Context, packageName : String){
            var intent: Intent? = context.packageManager.getLaunchIntentForPackage(packageName)

            if (intent == null) {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
            }
            context.startActivity(intent)
        }

        fun openPlayStore(context : Context, packageName : String){

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
            context.startActivity(intent)
        }
    }
}

fun ViewPager2.next(){
    setCurrentItem(currentItem+1, true)
}

fun ViewPager2.back(){
    setCurrentItem(currentItem-1, true)
}

fun Int.toBytes(): ByteArray =
    ByteBuffer.allocate(Int.SIZE_BYTES).putInt(this).array()

fun ByteArray.toInt(): Int =
    ByteBuffer.wrap(this).int


fun Chip.checkByPass(isChecked : Boolean){
    isCheckable = true
    this.isChecked = isChecked
    isCheckable = false
}

fun Chip.restoreDropDown(){
    closeIcon = AppCompatResources.getDrawable(context, R.drawable.arrow_drop)
    setOnCloseIconClickListener(null)
}

fun String.orderSingleNumber(context : Context) : String{

    return when (last()){
        '1' -> this + context.getString(R.string.st)
        '2' -> this + context.getString(R.string.nd)
        '3' -> this + context.getString(R.string.rd)
        else -> this + context.getString(R.string.th)
    }
}

fun Int.orderSingleNumber(context : Context) : String{

    return this.toString().orderSingleNumber(context)
}

fun Char.orderSingleNumber(context : Context) : String{

    return this.toString().orderSingleNumber(context)
}

fun View.getColorOfPixel(x: Int, y: Int): Int {
    val drawingCache = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(drawingCache)
    draw(canvas)
    return drawingCache.getPixel(x, y)
}

fun Dialog.setupBottomSheet(){
    window!!.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
}

fun Context.getAttributeColor(attributeId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attributeId, typedValue, true)
    val colorRes = typedValue.resourceId
    var color = -1
    try {
        color = ContextCompat.getColor(this, colorRes)
    } catch (e: Resources.NotFoundException) {
        Log.w("ERROR", "Not found color resource by id: $colorRes")
    }
    return color
}

fun String.camelToSnakeCase() = fold(StringBuilder(length)) { acc, c ->
    if (c in 'A'..'Z') (if (acc.isNotEmpty()) acc.append('_') else acc).append(c + ('a' - 'A'))
    else acc.append(c)
}.toString()

fun AppBarLayout.setupPortrait() : Button{
    val backButton : Button = findViewById(R.id.button_back)

    addOnOffsetChangedListener { _, verticalOffset ->
        val collapsedSize: Int = resources.getDimensionPixelSize(R.dimen.header_footer_size)
        background.alpha =  ((verticalOffset / -570f) * 255).toInt()
    }

    return backButton
}

fun AppBarLayout.setupLandscape() : Button{
    val backButton : Button = findViewById(R.id.button_back)
    (backButton.layoutParams as MarginLayoutParams).marginStart = context.resources.getDimensionPixelSize(R.dimen.card_padding)

    setExpanded(false)
    val layout = getChildAt(0) as CollapsingToolbarLayout
    post {
        layoutParams = CoordinatorLayout.LayoutParams(
            MATCH_PARENT,
            resources.getDimensionPixelSize(R.dimen.header_footer_size) + 1
        )
    }


    (context as Activity).window.statusBarColor = solidColor

    layout.titleCollapseMode = CollapsingToolbarLayout.TITLE_COLLAPSE_MODE_FADE
    layout.scrimVisibleHeightTrigger = 10000

    val toolbar = layout.getChildAt(0) as androidx.appcompat.widget.Toolbar
    toolbar.titleMarginStart = ApplicationManager.convertDpToPixel(80, context)

    (context as Activity).window.statusBarColor = context.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainer)

    return backButton
}

fun AppBarLayout.setup() : Button {

    val backButton : Button
    setBackgroundColor(context.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainer))
    val landscape = (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
    if (landscape) {
        backButton = setupLandscape()

    } else {
        backButton = setupPortrait()
    }
    return backButton
}

fun View.setVisibilityBy(boolean : Boolean){
    visibility = if(!boolean){
        GONE
    } else{
        VISIBLE
    }
}

fun View.setVisibilityBy(drawable : Drawable?){
    if(drawable == null){
        visibility = GONE
    } else{
        visibility = VISIBLE
    }
}

fun TextView.setVisibilityBy(int : Int){
    if(int == 0){
        visibility = GONE
    } else{
        visibility = VISIBLE
        text = int.toString()
    }
}

fun TextView.setVisibilityBy(string : String?){
    if(string.isNullOrBlank()){
        visibility = GONE
    } else{
        visibility = VISIBLE
        text = string
    }
}

fun TextView.setVisibilityBy(date : Date?){
    if(date == null){
        visibility = GONE
    } else{
        visibility = VISIBLE
        text = date.formatDate(DateTimeHelper.shortMonthDayTimeFormat)
    }
}

fun String.formatDate(format : String): String {
    return java.text.SimpleDateFormat(format, Locale.forLanguageTag("hu")).format(this)
}

fun Date.formatDate(format : String): String {
    return java.text.SimpleDateFormat(format, Locale.forLanguageTag("hu")).format(this)
}

fun Long.formatDate(format : String): String {
    return java.text.SimpleDateFormat(format, Locale.forLanguageTag("hu")).format(this)
}
