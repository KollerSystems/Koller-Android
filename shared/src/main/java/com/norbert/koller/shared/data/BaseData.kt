package com.norbert.koller.shared.data

import android.content.Context
import android.os.SystemClock
import android.system.SystemCleaner
import android.util.Log
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager

abstract class BaseData() : ExpiringData() {
    abstract fun diffrentDecider(context: Context) : String
    abstract fun getMainID() : Int
    open fun getTitle(): String{return ""}
    open fun getDescription(): String{return ""}




    var testState : String = ""




    open fun updateValues(baseData: BaseData){

    }
}