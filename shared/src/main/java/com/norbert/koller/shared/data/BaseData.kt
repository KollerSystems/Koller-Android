package com.norbert.koller.shared.data

import android.content.Context
import android.os.SystemClock
import android.system.SystemCleaner
import android.util.Log
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager

abstract class BaseData() {
    abstract fun diffrentDecider(context: Context) : String
    abstract fun getMainID() : Int
    private var receivedAt : Long = -1

    fun saveReceivedTime(){
        receivedAt = SystemClock.uptimeMillis()
    }


    var testState : String = ""

    fun isValid(context: Context, time : Int) : Boolean{
        return isUnexpired(time) || !ApplicationManager.isOnline(context)
    }

    fun isUnexpired(time : Int) : Boolean{
        val timeSpent = SystemClock.uptimeMillis() - receivedAt
        val timeInSeconds = time * 1000L
        Log.d(receivedAt.toString(), timeInSeconds.toString())
        return timeSpent < timeInSeconds
    }
}