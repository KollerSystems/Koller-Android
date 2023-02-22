package com.example.koller

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.card.MaterialCardView
import okhttp3.*
import java.io.IOException


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
            val intent = Intent(view.context, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            this.dismiss()
        }

        val btnManageAccount: Button = view.findViewById(R.id.profile_btn_manage_account)

        btnManageAccount.setOnClickListener{
            (requireActivity() as MainActivity).bottomNavigationView.selectedItemId = R.id.studentHostelFragment
            this.dismiss()
        }

        val fbtnOutgoing: MaterialCardView = view.findViewById(R.id.profile_fbtn_outgoing)

        fbtnOutgoing.setOnClickListener{
            (requireActivity() as MainActivity).bottomNavigationView.selectedItemId = R.id.studentHostelFragment
            findNavController().navigate(R.id.action_studentHostelFragment_to_userOutgoingFragment)
            this.dismiss()
        }

        val fbtnGate: MaterialCardView = view.findViewById(R.id.profile_fbtn_gate)

        fbtnGate.setOnClickListener{
            (requireActivity() as MainActivity).bottomNavigationView.selectedItemId = R.id.studentHostelFragment
            findNavController().navigate(R.id.action_studentHostelFragment_to_userGateFragment)
            this.dismiss()
        }

        val fbtnSettings: MaterialCardView = view.findViewById(R.id.profile_fbtn_settings)

        fbtnSettings.setOnClickListener{
            val intent = Intent(view.context, SettingsActivity::class.java)
            startActivity(intent)
            this.dismiss()
        }

        val fbtnPrivacyPolicy: MaterialCardView = view.findViewById(R.id.profile_fbtn_privacy_policy)

        fbtnPrivacyPolicy.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/orgs/4E-6F-72-62-65-72-74"))
            startActivity(browserIntent)
            this.dismiss()
        }

        val fbtnEmailPolicy: MaterialCardView = view.findViewById(R.id.profile_fbtn_email)

        fbtnEmailPolicy.setOnClickListener{

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:contact@norbert.hu?body=Felhasználó azonosítója:")

            startActivity(intent)
            this.dismiss()
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