package com.example.koller

import APIInterface
import RetrofitHelper
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileBottomSheet : BottomSheetDialogFragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogFull)

    }

    private lateinit var textName : TextView

    private fun GetUserName(){
        val apiInterface = RetrofitHelper.getInstance().create(APIInterface::class.java)
        // launching a new coroutine

        val result = apiInterface.getUsername()

        result.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                textName.text = response.body()
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.bottom_sheet_profile, container, false)

        textName = view.findViewById(R.id.profile_text_name)

        GetUserName()


        val buttonLogout: Button = view.findViewById(R.id.button_logout)
        buttonLogout.setOnClickListener{

            MaterialAlertDialogBuilder(view.context)
                .setTitle("Biztosan ki akarsz jelentkezni?")
                .setIcon(R.drawable.surpriesd)
                .setPositiveButton(
                    "Igen"
                )
                { _, _ ->
                    val intent = Intent(view.context, LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    this.dismiss()
                }
                .setNegativeButton(
                    "Nem"
                )
                { _, _ ->

                }
                .show()
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

        //DEVS

        val cardMarci: MaterialCardView = view.findViewById(R.id.profile_card_marci)

        cardMarci.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Marci599"))
            startActivity(browserIntent)
            this.dismiss()
        }

        val cardAdam: MaterialCardView = view.findViewById(R.id.profile_card_adam)

        cardAdam.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Lukacs-Adam"))
            startActivity(browserIntent)
            this.dismiss()
        }
        val cardGergo: MaterialCardView = view.findViewById(R.id.profile_card_gergo)

        cardGergo.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/lolfail"))
            startActivity(browserIntent)
            this.dismiss()
        }
        val cardGyuri: MaterialCardView = view.findViewById(R.id.profile_card_gyuri)

        cardGyuri.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/kutzlect"))
            startActivity(browserIntent)
            this.dismiss()
        }
        val cardRobi: MaterialCardView = view.findViewById(R.id.profile_card_robi)

        cardRobi.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/IronNight007"))
            startActivity(browserIntent)
            this.dismiss()
        }
        val cardMiki: MaterialCardView = view.findViewById(R.id.profile_card_miki)

        cardMiki.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Ararakalari"))
            startActivity(browserIntent)
            this.dismiss()
        }


        val fbtnEmail: MaterialCardView = view.findViewById(R.id.profile_fbtn_email)

        fbtnEmail.setOnClickListener{

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:contact@norbert.hu?body=Felhasználó azonosítója:")

            startActivity(intent)
            this.dismiss()
        }

        return view
    }

    companion object {
        const val TAG = "ProfileBottomSheet"
    }
}