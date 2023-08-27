package com.norbert.koller.shared.data

import android.content.Context

abstract class BaseData {
    abstract fun diffrentDecider(context: Context) : String

    abstract fun getID() : Int
}