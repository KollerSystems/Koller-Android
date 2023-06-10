package com.example.koller

import com.example.koller.data.UserData
import android.app.Application
import android.app.Dialog
import android.content.ClipData
import android.content.Context
import android.content.res.Resources
import android.icu.text.SimpleDateFormat
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.material.card.MaterialCardView
import com.google.android.material.color.DynamicColors
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textfield.TextInputLayout


class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    companion object {


        fun timeToString(hours : Int, minutes : Int) : String{
            return (hours).toString().padStart(2, '0')+":"+(minutes).toString().padStart(2, '0')
        }

        fun roundRecyclerItemsVerticallyWithSeparator(context : Context, view : View, position : Int, list : ArrayList<Any>){

            if(position == 0) return

            if(position == list.size -1) {
                roundCardBottom(context, view)
                return
            }

            if (list[position - 1] is String && list[position + 1] !is String) {
                roundCardTop(context, view)
            } else if (list[position - 1] !is String && list[position + 1] is String) {
                roundCardBottom(context, view)
            } else if  (list[position - 1] is String && list[position + 1] is String){
                roundCard(context, view)
            }
            else{
                deroundCard(context, view)
            }

        }


        fun roundRecyclerItemsX(context : Context, view : View, position : Int, size : Int, startAppearance : Int, endAppearance : Int){
            if(position == 0){

                roundCardX(context, view, startAppearance)
            }
            else if (position == size-1){

                roundCardX(context, view, endAppearance)
            }
            else if (size == 1) {

                roundCard(context, view)

            }

        }

        fun roundRecyclerItemsVertically(context : Context, view : View, position : Int, size : Int){
            if(position == 0){

                roundCardTop(context, view)
            }
            else if (position == size-1){

                roundCardBottom(context, view)
            }
            else if (size == 1) {

                roundCard(context, view)

            }

        }

        fun roundRecyclerItemsHorizontally(context : Context, view : View, position : Int, size : Int){
            if(position == 0){

                roundCardLeft(context, view)
            }
            else if (position == size-1){

                roundCardRight(context, view)
            }
            else if (size == 1){

                roundCard(context, view)
            }
        }

        fun roundCardX(context: Context, view : View, overlay : Int){
            val shapeAppearance = ShapeAppearanceModel.builder(
                context,
                overlay,
                0
            ).build()

            (view as MaterialCardView).shapeAppearanceModel = shapeAppearance
        }

        fun roundCardBottom(context: Context, view : View){
            roundCardX(context, view, R.style.overlayRoundedCardBottom)
        }

        fun roundCardTop(context: Context, view : View){
            roundCardX(context, view, R.style.overlayRoundedCardTop)
        }

        fun roundCardLeft(context: Context, view : View){
            roundCardX(context, view, R.style.overlayRoundedCardLeft)
        }

        fun roundCardRight(context: Context, view : View){
            roundCardX(context, view, R.style.overlayRoundedCardRight)
        }

        fun roundCard(context: Context, view : View){
            roundCardX(context, view, R.style.overlayRoundedCard)
        }

        fun deroundCard(context: Context, view : View){

            (view as MaterialCardView).shapeAppearanceModel = ShapeAppearanceModel()
        }


        const val minLengthBeforeDismiss : Int = 3

        val simpleLocalDateFormat = SimpleDateFormat("yyyy. MMM d.")
        val simpleLocalMonthDay = SimpleDateFormat("MMMM d.")
        val simpleLocalShortMonthDay = SimpleDateFormat("MMM d.")
        val simpleTimeFormat = SimpleDateFormat("HH:mm")

        fun createUserDescription(userData : UserData): String{
            return userData.Group + " • " + userData.RoomNumber  + " • " + userData.Class
        }

        fun setupBottomSheet(dialog : Dialog){
            dialog.window!!.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        }

        fun setupActivity(window : Window){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.setDecorFitsSystemWindows(false)
                window.isNavigationBarContrastEnforced = false
            }
        }

        fun setClipboard(context: Context, text: String) {

                val clipboard =
                    context.getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
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
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }

        fun FocusEdittext(ctx : Context, edittext : TextInputLayout){
            edittext.isFocusableInTouchMode = true
            edittext.requestFocus()
            val imm = ctx.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(edittext, InputMethodManager.SHOW_IMPLICIT)
        }

        fun getAttributeColor(
            context: Context,
            attributeId: Int
        ): Int {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(attributeId, typedValue, true)
            val colorRes = typedValue.resourceId
            var color = -1
            try {
                color = context.resources.getColor(colorRes)
            } catch (e: Resources.NotFoundException) {
                Log.w("ERROR", "Not found color resource by id: $colorRes")
            }
            return color
        }

        fun timeTo(to : Int):String{
            return "-\n" + minuteToHourMinuteFormat(to)
        }

        fun timeFromTo(from : Int, to : Int):String{
            return minuteToHourMinuteFormat(from) + "\n-\n" + minuteToHourMinuteFormat(to)
        }

        fun timeFrom(from : Int):String{
            return minuteToHourMinuteFormat(from) + "\n-"
        }

        fun minuteToHourMinuteFormat(minute : Int):String{


            var hours : Int = ((minute) / 60)
            var minutesWithoutHours : Int = (minute - (hours * 60))

            return (hours.toString()+":"+minutesWithoutHours.toString().padStart(2, '0'))
        }
    }

}