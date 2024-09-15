package com.norbert.koller.shared

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.toBytes
import com.norbert.koller.shared.managers.toInt

class KollerHostApduService : HostApduService() {

    companion object{
        var handleNFC: ((uid : Int) -> Unit)? = null
    }

    override fun processCommandApdu(commandApdu: ByteArray, extras: Bundle?): ByteArray {

        handleNFC?.invoke(commandApdu.toInt())
        return CacheManager.userData!!.uid.toBytes()
    }

    override fun onDeactivated(reason: Int) {

    }
}