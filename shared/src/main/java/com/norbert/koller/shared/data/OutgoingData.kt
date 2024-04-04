package com.norbert.koller.shared.data

import android.content.Context

class OutgoingData(
    var addresses : MutableList<UserData> = arrayListOf()
) : BaseData() {



    override fun diffrentDecider(context: Context): String {
        return ""
    }

    override fun getMainID(): Int {
        return 0
    }
}