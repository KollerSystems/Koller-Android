package com.norbert.koller.shared

import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import java.nio.charset.Charset

class MyHostApduService : HostApduService() {

    override fun processCommandApdu(commandApdu: ByteArray, extras: Bundle?): ByteArray {

        return "Hello Word".toByteArray(Charset.defaultCharset())

    }

    override fun onDeactivated(reason: Int) {

    }
}