package com.example.shared.api

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.widget.Toast

class KollerHostApduService : HostApduService() {

    override fun processCommandApdu(commandApdu: ByteArray, extras: Bundle?): ByteArray {

        return processCommandApdu(byteArrayOf(0x2E, 0x38), null)
    }

    override fun onDeactivated(reason: Int) {

    }
}