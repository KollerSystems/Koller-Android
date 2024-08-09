package com.norbert.koller.shared.fragments.bottomsheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.activities.ManageAccountActivity
import com.norbert.koller.shared.data.DevData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ContentFragmentBsdProfileFooterBinding
import com.norbert.koller.shared.databinding.ContentFragmentBsdProfileHeaderBinding
import com.norbert.koller.shared.fragments.CrossingsFragment
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.managers.setupBottomSheet
import com.norbert.koller.shared.recycleradapters.DevRecyclerAdapter
import kotlinx.coroutines.launch


abstract class ProfileBsdFragment : BottomSheetDialogFragment() {

    abstract fun getHeaderBinding() : ContentFragmentBsdProfileHeaderBinding
    abstract fun getFooterBinding() : ContentFragmentBsdProfileFooterBinding

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogFull
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        dialog!!.setupBottomSheet()

        getHeaderBinding().user.setUser(UserData.instance)
        getHeaderBinding().textName.text = UserData.instance.name
        getHeaderBinding().textDescription.text = UserData.instance.createDescription()

        getHeaderBinding().btnLogout.setOnClickListener{

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

        getHeaderBinding().btnManageAccount.setOnClickListener{


            val intent = Intent(view.context, ManageAccountActivity::class.java)
            startActivity(intent)
        }

        getHeaderBinding().cbRoom.setOnClickListener{

            (requireContext() as MainActivity).addFragment(ApplicationManager.roomFragment(UserData.instance.rid!!))
            this.dismiss()
        }

        getFooterBinding().cbCrossings.setOnClickListener{

            (requireContext() as MainActivity).addFragment(CrossingsFragment(UserData.instance.uid))
            this.dismiss()
        }

        getFooterBinding().cbSettings.setOnClickListener{
            ApplicationManager.openSettings.invoke(requireContext())
        }

        getFooterBinding().cbPrivacyPolicy.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@Marci599"))
            startActivity(browserIntent)
        }

        //DEVS

        getFooterBinding().cardOrganization.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://kollegium.tech/"))
            startActivity(browserIntent)
        }

        getFooterBinding().recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        getFooterBinding().recyclerView.setHasFixedSize(false)

        val devArrayList = arrayListOf(
            DevData("Katona Márton Barnabás", "Főmenedzsment • Front-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_marton)!!, "https://Marci599.github.io"),
            DevData("Bencsik Gergő", "Menedzsment • Back-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_gergo)!!, "https://github.com/lolfail"),
            DevData("Zsiga Róbert", "Back-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_robert)!!, "https://github.com/IronNight007"),
            DevData("Bende Ákos György", "Menedzsment", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_gyorgy)!!, "https://github.com/kutzlect"),
            DevData("Kovács Gábor", "Front-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_gabor)!!, "https://github.com/mrSajt2"),
            DevData("Fehér Dávid", "Back-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_david)!!, "https://github.com/TheBlueLines"),
            DevData("Juhos Gergely János", "Marketing", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_juhos)!!, "https://github.com/enderember003"),
        )

        devArrayList.shuffle()

        getFooterBinding().recyclerView.adapter = DevRecyclerAdapter(devArrayList)

        getFooterBinding().cbSupport.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.buymeacoffee.com/kollersystems"))
            startActivity(browserIntent)

        }

        getFooterBinding().cbGithub.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/KollerSystems"))
            startActivity(browserIntent)
        }

        getFooterBinding().cbEmail.setOnClickListener{

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:contact@norbert.hu?body=Felhasználó azonosítója:")

            startActivity(intent)

        }
    }


    companion object {
        const val TAG = "ProfileBottomSheet"
    }
}