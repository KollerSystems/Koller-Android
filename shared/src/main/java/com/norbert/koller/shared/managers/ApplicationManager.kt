package com.norbert.koller.shared.managers

import android.app.Application
import android.app.Dialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.icu.text.SimpleDateFormat
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.material.appbar.AppBarLayout
import com.norbert.koller.shared.fragments.CalendarFragment
import com.norbert.koller.shared.fragments.HomeFragment
import com.norbert.koller.shared.fragments.NotificationsFragment
import com.norbert.koller.shared.fragments.RoomFragment
import com.norbert.koller.shared.fragments.StudentHostelFragment
import com.norbert.koller.shared.fragments.UserFragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.color.DynamicColors
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.LoginActivity
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.RoomsFragment
import com.norbert.koller.shared.fragments.UserOutgoingPermanentFragment
import com.norbert.koller.shared.fragments.UserOutgoingTemporaryFragment
import com.norbert.koller.shared.fragments.UsersFragment
import com.norbert.koller.shared.helpers.DateTimeHelper
import java.nio.ByteBuffer
import java.util.Date
import java.util.Locale


open class ApplicationManager : Application() {



    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }


    companion object Comp{

        lateinit var version : String



        lateinit var openSettings: (context : Context) -> Unit
        lateinit var openLogin: (context : Context) -> Unit
        lateinit var openMain: (context : Context) -> Unit
        lateinit var openProfile: (context : Context) -> Unit

        lateinit var loginActivity: () -> LoginActivity

        lateinit var homeFragment: () -> HomeFragment
        lateinit var calendarFragment: () -> CalendarFragment
        lateinit var studentHostelFragment: () -> StudentHostelFragment
        lateinit var notificationFragment: () -> NotificationsFragment

        lateinit var roomFragment: (rid : Int) -> RoomFragment
        lateinit var userFragment: (uid : Int) -> UserFragment

        lateinit var getAppColor: (context : Context) -> Int

        var roomsFragment: (map : MutableMap<String, ArrayList<String>>?) -> RoomsFragment = {map-> RoomsFragment(map) }
        var usersFragment: (map : MutableMap<String, ArrayList<String>>?) -> UsersFragment = {map-> UsersFragment(map) }

        var userOutgoingTemporaryFragment: (userData : UserData?) -> UserOutgoingTemporaryFragment = {userData -> UserOutgoingTemporaryFragment(userData) }
        var userOutgoingPermanentFragment: (userData : UserData?) -> UserOutgoingPermanentFragment = {userData -> UserOutgoingPermanentFragment(userData) }

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
    }
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

fun Window.setToolbarToViewColor(bottomView : View){
    bottomView.post{
        val navViewColor = bottomView.getColorOfPixel(0, 0)
        navigationBarColor = navViewColor
    }
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

fun Context.getStringResourceByName(stringName: String): String? {

    return try {
        val resId = resources.getIdentifier(stringName, "string", packageName)
        getString(resId)
    }
    catch (ex : Exception){
        null
    }

}

fun String.camelToSnakeCase() = fold(StringBuilder(length)) { acc, c ->
    if (c in 'A'..'Z') (if (acc.isNotEmpty()) acc.append('_') else acc).append(c + ('a' - 'A'))
    else acc.append(c)
}.toString()


fun AppBarLayout.setup(){

    setBackgroundColor(context.getAttributeColor(com.google.android.material.R.attr.colorSurfaceContainer))
    addOnOffsetChangedListener { _, verticalOffset ->
        val collapsedSize: Int = resources.getDimensionPixelSize(R.dimen.header_footer_size)
        background.alpha =  ((verticalOffset / -570f) * 255).toInt()
    }
}

fun View.setVisibilityBy(boolean : Boolean){
    if(!boolean){
        visibility = GONE
    } else{
        visibility = VISIBLE
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
