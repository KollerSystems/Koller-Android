package com.norbert.koller.shared.fragments.bottomsheet

import com.norbert.koller.shared.data.UserData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.norbert.koller.shared.activities.MainActivity
import kotlinx.coroutines.launch


open class ProfileBottomSheet : BottomSheetDialogFragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogFull)

    }

    private lateinit var textName : TextView
    private lateinit var textDescription : TextView

    lateinit var realView : View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        MyApplication.setupBottomSheet(dialog!!)
        textName = realView.findViewById(R.id.profile_text_name)
        textName.text = UserData.instance.Name

        textDescription = realView.findViewById(R.id.profile_text_description)
        textDescription.text = MyApplication.createUserDescription(UserData.instance)


        val buttonLogout: Button = realView.findViewById(R.id.button_logout)
        buttonLogout.setOnClickListener{

            MaterialAlertDialogBuilder(realView.context, R.style.ImagePopup)
                .setTitle("Biztosan ki akarsz jelentkezni?")
                .setIcon(R.drawable.surpriesd)
                .setPositiveButton(
                    "Igen"
                )
                { _, _ ->
                    val dialog = MaterialAlertDialogBuilder(realView.context)
                        .setTitle("Kijelentkezés...")
                        .setCancelable(false)
                        .show()

                    lifecycleScope.launch{

                        DataStoreManager.remove(requireActivity(), DataStoreManager.REFRESH_TOKEN_NAME)
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

        val btnManageAccount: Button = realView.findViewById(R.id.profile_btn_manage_account)

        btnManageAccount.setOnClickListener{

            this.dismiss()
        }

        val cardMyRoom: View = realView.findViewById(R.id.profile_card_my_room)

        cardMyRoom.setOnClickListener{

            (requireContext() as MainActivity).changeFragment(MyApplication.roomFragment(UserData.instance.RID!!))
            this.dismiss()
        }

        val fbtnGate: View = realView.findViewById(R.id.profile_fbtn_gate)

        fbtnGate.setOnClickListener{

            UserExitsAndEntrancesFragment.open(requireContext(), UserData.instance.UID)
            this.dismiss()
        }

        val cardDevices: View = realView.findViewById(R.id.profile_card_devices)

        cardDevices.setOnClickListener{
            val intent = Intent(realView.context, DevicesActivity::class.java)
            startActivity(intent)
            this.dismiss()
        }

        val fbtnSettings: View = realView.findViewById(R.id.profile_fbtn_settings)

        fbtnSettings.setOnClickListener{
            MyApplication.openSettings.invoke(requireContext())
            this.dismiss()
        }

        val fbtnPrivacyPolicy: View = realView.findViewById(R.id.profile_fbtn_privacy_policy)

        fbtnPrivacyPolicy.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/orgs/4E-6F-72-62-65-72-74"))
            startActivity(browserIntent)
            this.dismiss()
        }

        //DEVS

        val recyclerView : RecyclerView = realView.findViewById(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)

        val devArrayList = arrayListOf(
            DevData("Katona Márton B.", "Menedzsment és Android", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp4)!!, "https://github.com/Marci599"),
            DevData("Bencsik Gergő B.", "API és Adatbázis", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp5)!!, "https://github.com/lolfail"),
            DevData("Zsiga Róbert", "Adatbázis", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp3)!!, "https://github.com/IronNight007"),
            DevData("Fehér Dávid", "Windows back-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp7)!!, "https://github.com/TheBlueLines"),
            DevData("Várnagy Miklós T.", "Windows front-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp1)!!, "https://github.com/Ararakalari"),
            DevData("Bende Ákos Gy.", "Szerver", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp6)!!, "https://github.com/kutzlect"),
        )

        recyclerView.adapter = DevRecyclerAdapter(devArrayList)

        val cardSupport: View = realView.findViewById(R.id.profile_card_support)

        cardSupport.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.buymeacoffee.com/"))
            startActivity(browserIntent)
            this.dismiss()
        }

        val cardBug: View = realView.findViewById(R.id.profile_card_bug)

        cardBug.setOnClickListener{
            val intent = Intent(realView.context, FeedbackActivity::class.java)
            startActivity(intent)
            this.dismiss()
        }

        val cardIdea: View = realView.findViewById(R.id.profile_card_idea)

        cardIdea.setOnClickListener{
            val intent = Intent(realView.context, FeedbackActivity::class.java)
            startActivity(intent)
            this.dismiss()
        }

        val fbtnEmail: View = realView.findViewById(R.id.profile_fbtn_email)

        fbtnEmail.setOnClickListener{

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:contact@norbert.hu?body=Felhasználó azonosítója:")

            startActivity(intent)
            this.dismiss()
        }

        return realView
    }

    companion object {
        const val TAG = "ProfileBottomSheet"
    }
}