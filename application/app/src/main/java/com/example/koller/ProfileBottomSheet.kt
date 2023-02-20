package com.example.koller

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.checkbox.MaterialCheckBox
import okhttp3.*
import java.io.IOException
import java.util.*


class ProfileBottomSheet : BottomSheetDialogFragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogFull)

    }
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.bottom_sheet_profile, container, false)

        val buttonLogout: Button = view.findViewById(R.id.button_logout)
        buttonLogout.setOnClickListener{
            this.dismiss()
            val intent = Intent(view.context, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        fun run(url: String) {
            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) = println(response.body()?.string())
            })
        }

        return view
    }

    companion object {
        const val TAG = "ProfileBottomSheet"
    }
}