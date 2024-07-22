package com.norbert.koller.shared.fragments.bottomsheet

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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.activities.ManageAccountActivity
import com.norbert.koller.shared.customviews.UserView
import com.norbert.koller.shared.data.DevData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.CrossingsFragment
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.managers.setupBottomSheet
import com.norbert.koller.shared.recycleradapters.DevRecyclerAdapter
import kotlinx.coroutines.launch


open class ProfileBsdFragment : BottomSheetDialogFragment() {


    override fun getTheme(): Int {
        return R.style.BottomSheetDialogFull
    }

    private lateinit var textName : TextView
    private lateinit var textDescription : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        dialog!!.setupBottomSheet()

        val badgeUser : UserView = view.findViewById(R.id.badge_user)
        badgeUser.setUser(UserData.instance)


        textName = view.findViewById(R.id.profile_text_name)
        textName.text = UserData.instance.name

        textDescription = view.findViewById(R.id.profile_text_description)
        textDescription.text = UserData.instance.createDescription()


        val buttonLogout: Button = view.findViewById(R.id.button_logout)
        buttonLogout.setOnClickListener{

            MaterialAlertDialogBuilder(view.context)
                .setTitle("Biztosan ki akarsz jelentkezni?")
                .setPositiveButton(
                    getText(R.string.yes)
                )
                { _, _ ->
                    val dialog = MaterialAlertDialogBuilder(view.context)
                        .setTitle("Kijelentkezés...")
                        .setCancelable(false)
                        .show()

                    lifecycleScope.launch{

                        DataStoreManager.remove(requireActivity(), DataStoreManager.TOKENS)
                        ApplicationManager.openLogin.invoke(requireContext())
                        requireActivity().finish()
                        dialog.dismiss()
                        dismiss()
                    }


                }
                .setNegativeButton(
                    getText(R.string.cancel)
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

            (requireContext() as MainActivity).addFragment(ApplicationManager.roomFragment(UserData.instance.rid!!))
            this.dismiss()
        }

        val fbtnGate: View = view.findViewById(R.id.profile_fbtn_gate)

        fbtnGate.setOnClickListener{

            (requireContext() as MainActivity).addFragment(CrossingsFragment(UserData.instance.uid))
            this.dismiss()
        }

        val fbtnSettings: View = view.findViewById(R.id.profile_fbtn_settings)

        fbtnSettings.setOnClickListener{
            ApplicationManager.openSettings.invoke(requireContext())
        }

        val fbtnPrivacyPolicy: View = view.findViewById(R.id.profile_fbtn_privacy_policy)

        fbtnPrivacyPolicy.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@Marci599"))
            startActivity(browserIntent)
        }

        //DEVS

        val fbtnOrganization: View = view.findViewById(R.id.card_organization)

        fbtnOrganization.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://kollegium.tech/"))
            startActivity(browserIntent)
        }

        val RecyclerView : RecyclerView = view.findViewById(R.id.recycler_view)

        RecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        RecyclerView.setHasFixedSize(false)

        val devArrayList = arrayListOf(
            DevData("Katona Márton Barnabás", "Főmenedzsment • Front-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_marton)!!, "https://Marci599.github.io"),
            DevData("Bencsik Gergő", "Menedzsment • Back-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_gergo)!!, "https://github.com/lolfail"),
            DevData("Zsiga Róbert", "Back-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_robert)!!, "https://github.com/IronNight007"),
            DevData("Bende Ákos György", "Menedzsment", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_gyorgy)!!, "https://github.com/kutzlect"),
            DevData("Kovács Gábor", "Front-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_gabor)!!, "https://github.com/mrSajt2"),
            DevData("Fehér Dávid", "Back-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_david)!!, "https://github.com/TheBlueLines"),
            DevData("Juhos Gergely János", "Marketing", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_juhos)!!, "https://github.com/enderember003"),
        )

        RecyclerView.adapter = DevRecyclerAdapter(devArrayList)

        val cardSupport: View = view.findViewById(R.id.profile_card_support)

        cardSupport.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.buymeacoffee.com/kollersystems"))
            startActivity(browserIntent)

        }

        val cardGitHub: View = view.findViewById(R.id.profile_card_github)

        cardGitHub.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/KollerSystems"))
            startActivity(browserIntent)
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