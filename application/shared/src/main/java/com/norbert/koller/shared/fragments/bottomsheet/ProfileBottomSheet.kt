package com.norbert.koller.shared.fragments.bottomsheet

import com.norbert.koller.shared.data.UserData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.DataStoreManager
import com.norbert.koller.shared.activities.DevicesActivity
import com.norbert.koller.shared.activities.FeedbackActivity
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.DevData
import com.norbert.koller.shared.fragments.UserExitsAndEntrancesFragment
import com.norbert.koller.shared.recycleradapter.DevRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.activities.ManageAccountActivity
import com.norbert.koller.shared.activities.MainActivity
import kotlinx.coroutines.launch


open class ProfileBottomSheet : BottomSheetDialogFragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogFull)

    }

    private lateinit var textName : TextView
    private lateinit var textDescription : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyApplication.setupBottomSheet(dialog!!)

        val textVersion : TextView = view.findViewById(R.id.text_version)
        textVersion.text = MyApplication.version

        textName = view.findViewById(R.id.profile_text_name)
        textName.text = UserData.instance.Name

        textDescription = view.findViewById(R.id.profile_text_description)
        textDescription.text = MyApplication.createUserDescription(UserData.instance)


        val buttonLogout: Button = view.findViewById(R.id.button_logout)
        buttonLogout.setOnClickListener{

            MaterialAlertDialogBuilder(view.context, R.style.ImagePopup)
                .setTitle("Biztosan ki akarsz jelentkezni?")
                .setIcon(R.drawable.surpriesd)
                .setPositiveButton(
                    "Igen"
                )
                { _, _ ->
                    val dialog = MaterialAlertDialogBuilder(view.context)
                        .setTitle("Kijelentkezés...")
                        .setCancelable(false)
                        .show()

                    lifecycleScope.launch{

                        DataStoreManager.remove(requireActivity(), DataStoreManager.TOKENS)
                        MyApplication.openLogin.invoke(requireContext())
                        requireActivity().finish()
                        dialog.dismiss()
                        dismiss()
                    }


                }
                .setNegativeButton(
                    "Nem"
                )
                { _, _ ->

                }
                .show()
        }

        val btnManageAccount: Button = view.findViewById(R.id.btn_manage_account)

        btnManageAccount.setOnClickListener{


            val intent = Intent(view.context, ManageAccountActivity::class.java)
            startActivity(intent)
        }

        val cardMyRoom: View = view.findViewById(R.id.profile_card_my_room)

        cardMyRoom.setOnClickListener{

            (requireContext() as MainActivity).changeFragment(MyApplication.roomFragment(UserData.instance.RID!!))
            this.dismiss()
        }

        val fbtnGate: View = view.findViewById(R.id.profile_fbtn_gate)

        fbtnGate.setOnClickListener{

            (requireContext() as MainActivity).changeFragment(UserExitsAndEntrancesFragment(UserData.instance.UID))
            this.dismiss()
        }

        val cardDevices: View = view.findViewById(R.id.profile_card_devices)

        cardDevices.setOnClickListener{
            val intent = Intent(view.context, DevicesActivity::class.java)
            startActivity(intent)
        }

        val fbtnSettings: View = view.findViewById(R.id.profile_fbtn_settings)

        fbtnSettings.setOnClickListener{
            MyApplication.openSettings.invoke(requireContext())
        }

        val fbtnPrivacyPolicy: View = view.findViewById(R.id.profile_fbtn_privacy_policy)

        fbtnPrivacyPolicy.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/orgs/4E-6F-72-62-65-72-74"))
            startActivity(browserIntent)
        }

        //DEVS

        val recyclerView : RecyclerView = view.findViewById(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)

        val devArrayList = arrayListOf(
            DevData("Katona Márton B.", "Menedzsment és Android", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp4)!!, "https://github.com/Marci599"),
            DevData("Bencsik Gergő", "API és Adatbázis", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp5)!!, "https://github.com/lolfail"),
            DevData("Zsiga Róbert", "Adatbázis", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp3)!!, "https://github.com/IronNight007"),
            DevData("Fehér Dávid", "Windows back-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp7)!!, "https://github.com/TheBlueLines"),
            DevData("Várnagy Miklós T.", "Windows front-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp1)!!, "https://github.com/Ararakalari"),
            DevData("Bende Ákos Gy.", "Szerver", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp6)!!, "https://github.com/kutzlect"),
        )

        recyclerView.adapter = DevRecyclerAdapter(devArrayList)

        val cardSupport: View = view.findViewById(R.id.profile_card_support)

        cardSupport.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.buymeacoffee.com/norberttechnologies"))
            startActivity(browserIntent)

        }

        val cardBug: View = view.findViewById(R.id.profile_card_bug)

        cardBug.setOnClickListener{
            val intent = Intent(view.context, FeedbackActivity::class.java)
            startActivity(intent)

        }

        val cardIdea: View = view.findViewById(R.id.profile_card_idea)

        cardIdea.setOnClickListener{
            val intent = Intent(view.context, FeedbackActivity::class.java)
            startActivity(intent)

        }

        val fbtnEmail: View = view.findViewById(R.id.profile_fbtn_email)

        fbtnEmail.setOnClickListener{

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:contact@norbert.hu?body=Felhasználó azonosítója:")

            startActivity(intent)

        }
    }


    companion object {
        const val TAG = "ProfileBottomSheet"
    }
}