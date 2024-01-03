package com.norbert.koller.shared.data

import android.content.Context
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.MyApplication

abstract class BaseData() {
    abstract fun diffrentDecider(context: Context) : String
    abstract fun getMainID() : Int
    val receivedAt : Long = System.currentTimeMillis()

    var testState : String = ""

    fun isValid(context: Context, time : Int) : Boolean{
        return System.currentTimeMillis() - receivedAt < time * 1000L || !MyApplication.isOnline(context)
    }

    fun isUnexpired(time : Int) : Boolean{
        return System.currentTimeMillis() - receivedAt < time * 1000L
    }
}