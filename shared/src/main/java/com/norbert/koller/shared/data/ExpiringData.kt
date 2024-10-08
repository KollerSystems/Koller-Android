package com.norbert.koller.shared.data

import android.content.Context
import android.os.SystemClock
import android.util.Log
import com.norbert.koller.shared.managers.ApplicationManager

abstract class ExpiringData() {
    private var receivedAt : Long = -1
    fun isUnexpired(time : Int) : Boolean{
        val timeSpent = SystemClock.uptimeMillis() - receivedAt
        val timeInSeconds = time * 1000L
        Log.d(receivedAt.toString(), timeInSeconds.toString())
        return timeSpent < timeInSeconds
    }
    fun isValid(context: Context, time : Int) : Boolean{
        return isUnexpired(time) || !ApplicationManager.isOnline(context)
    }

    fun saveReceivedTime(){
        receivedAt = SystemClock.uptimeMillis()
    }


}