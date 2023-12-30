package com.norbert.koller.shared

import android.nfc.cardemulation.HostApduService
import android.os.Bundle

class KollerHostApduService : HostApduService() {

    override fun processCommandApdu(commandApdu: ByteArray, extras: Bundle?): ByteArray {

        return processCommandApdu(byteArrayOf(0x2E, 0x38), null)
    }

    override fun onDeactivated(reason: Int) {

    }
}