package com.norbert.koller.reader

import android.app.PendingIntent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.nfc.tech.NfcA

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var textKey : TextView
    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var pendingIntent: PendingIntent
    private var myTag: Tag? = null

    private var callback : NfcAdapter.ReaderCallback = NfcAdapter.ReaderCallback { tag : Tag ->


        val mifareClassic = MifareClassic.get(tag)

        try {
            mifareClassic.connect()

            var textToDisplay = ""

            val sectorSector = 1
            mifareClassic.authenticateSectorWithKeyA(sectorSector, MifareClassic.KEY_DEFAULT)

            val blockCount = mifareClassic.getBlockCountInSector(sectorSector)
            textToDisplay += "Szektor $sectorSector\n"
            for (blockIndex in 0 until blockCount) {
                val blockNumber = mifareClassic.sectorToBlock(sectorSector) + blockIndex
                val blockData = mifareClassic.readBlock(blockNumber)
                val hexData = bytesToHex(blockData)
                textToDisplay += "$hexData\n"
            }


            textKey.post {
                textKey.text = textToDisplay
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            mifareClassic.close()

        }
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (i in bytes.indices) {
            val v = bytes[i].toInt() and 0xFF
            hexChars[i * 2] = "0123456789ABCDEF"[v shr 4]
            hexChars[i * 2 + 1] = "0123456789ABCDEF"[v and 0x0F]
        }
        return String(hexChars)
    }

    override fun onResume() {
        super.onResume()

        nfcAdapter.enableReaderMode(this, callback, NfcAdapter.FLAG_READER_NFC_A, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableReaderMode(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textKey = findViewById(R.id.text_key)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    }
}