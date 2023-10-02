package com.norbert.koller.shared

import android.nfc.cardemulation.HostApduService
import android.os.Bundle

class MyHostApduService : HostApduService() {

    override fun processCommandApdu(commandApdu: ByteArray, extras: Bundle?): ByteArray {

        return "87DE7F7A5C0804006263646566676869".encodeToByteArray()

    }

    override fun onDeactivated(reason: Int) {

    }
}