package com.example.koller.fragments.bottomsheet

import UserData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.koller.MyApplication
import com.example.koller.R
import com.example.koller.activities.SettingsActivity
import com.example.koller.activities.LoginActivity
import com.example.koller.activities.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ProfileBottomSheet : BottomSheetDialogFragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogFull)

    }

    private lateinit var textName : TextView
    private lateinit var textDescription : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.bottom_sheet_profile, container, false)
        MyApplication.setupBottomSheet(dialog!!)
        textName = view.findViewById(R.id.profile_text_name)
        textName.text = UserData.instance.Name

        textDescription = view.findViewById(R.id.profile_text_description)
        textDescription.text = UserData.instance.Group + " • " + UserData.instance.RoomNumber  + " • " + UserData.instance.Class


        val buttonLogout: Button = view.findViewById(R.id.button_logout)
        buttonLogout.setOnClickListener{

            MaterialAlertDialogBuilder(view.context, R.style.ImagePopup)
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
            (requireActivity() as MainActivity).bottomNavigationView.selectedItemId =
                R.id.studentHostelNest
            this.dismiss()
        }

        val cardMyRoom: MaterialCardView = view.findViewById(R.id.profile_card_my_room)

        cardMyRoom.setOnClickListener{
            this.dismiss()
        }

        val fbtnOutgoing: MaterialCardView = view.findViewById(R.id.profile_fbtn_outgoing)

        fbtnOutgoing.setOnClickListener{
            this.dismiss()
        }

        val fbtnGate: MaterialCardView = view.findViewById(R.id.profile_fbtn_gate)

        fbtnGate.setOnClickListener{
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

        val cardDavid: MaterialCardView = view.findViewById(R.id.profile_card_david)

        cardDavid.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/TheBlueLines"))
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