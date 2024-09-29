package com.norbert.koller.shared

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.toBytes
import com.norbert.koller.shared.managers.toInt

class KollerHostApduService : HostApduService() {

    override fun processCommandApdu(commandApdu: ByteArray, extras: Bundle?): ByteArray {

        return commandApdu
    }

    override fun onDeactivated(reason: Int) {

    }
}