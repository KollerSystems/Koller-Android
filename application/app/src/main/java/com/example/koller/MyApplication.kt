package com.example.koller

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.color.DynamicColors
import com.google.android.material.textfield.TextInputLayout


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    companion object {
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