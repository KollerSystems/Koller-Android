package com.norbert.koller.shared.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.fragments.ManageAccountFragment
import com.norbert.koller.shared.data.DevData
import com.norbert.koller.shared.databinding.ContentFragmentBsdfProfileFooterBinding
import com.norbert.koller.shared.databinding.ContentFragmentBsdfProfileHeaderBinding
import com.norbert.koller.shared.fragments.CrossingListFragment
import com.norbert.koller.shared.fragments.KeyPagedFragment
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.DataStoreManager.Companion.loginDataStore
import com.norbert.koller.shared.managers.setupBottomSheet
import com.norbert.koller.shared.recycleradapters.DevRecyclerAdapter
import kotlinx.coroutines.launch
import androidx.core.net.toUri


abstract class ProfileBsdFragment : ScrollBsdfFragment() {

    abstract fun getHeaderBinding() : ContentFragmentBsdfProfileHeaderBinding
    abstract fun getFooterBinding() : ContentFragmentBsdfProfileFooterBinding

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogFull
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        dialog!!.setupBottomSheet()

        getHeaderBinding().user.setUser(CacheManager.getCurrentUserData()!!)
        getHeaderBinding().textName.text = CacheManager.getCurrentUserData()!!.name
        getHeaderBinding().textDescription.text = CacheManager.getCurrentUserData()!!.createDescription()

        getHeaderBinding().btnLogout.setOnClickListener{

            MaterialAlertDialogBuilder(view.context)
                .setMessage(getString(R.string.sure_logout))
                .setPositiveButton(
                    getText(R.string.logout)
                )
                { _, _ ->
                    val dialog = MaterialAlertDialogBuilder(view.context)
                        .setMessage(getString(R.string.loading))
                        .setCancelable(false)
                        .show()

                    lifecycleScope.launch{

                        requireContext().loginDataStore.edit {
                            it.clear()
                        }
                        CacheManager.loginData = null

                        ApplicationManager.openLogin(requireContext())
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

            (context as MainActivity).addFragment(ManageAccountFragment())
            this.dismiss()
        }

        getHeaderBinding().cbRoom.setOnClickListener{

            val fragment = ApplicationManager.roomFragment()
            val bundle = Bundle()
            bundle.putInt("id", CacheManager.getCurrentUserData()!!.rid!!)
            fragment.arguments = bundle
            (requireContext() as MainActivity).addFragment(fragment)
            this.dismiss()
        }

        getHeaderBinding().cbKeys.setOnClickListener{

            val fragment = KeyPagedFragment()
            val bundle = Bundle()
            bundle.putInt("id", CacheManager.getCurrentUserData()!!.uid)
            fragment.arguments = bundle
            (requireContext() as MainActivity).addFragment(fragment)
            this.dismiss()
        }

        getFooterBinding().cbCrossings.setOnClickListener{

            (requireContext() as MainActivity).addFragment(CrossingListFragment())
            this.dismiss()
        }

        getFooterBinding().cbSettings.setOnClickListener{
            ApplicationManager.openSettings.invoke(requireContext())
        }

        getFooterBinding().cbPrivacyPolicy.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW,
                "https://www.youtube.com/@Marci599".toUri())
            startActivity(browserIntent)
        }

        getFooterBinding().cardOrganization.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, "https://kollegium.tech/".toUri())
            startActivity(browserIntent)
        }

        getFooterBinding().recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        getFooterBinding().recyclerView.setHasFixedSize(false)

        val devArrayList = arrayListOf(
            DevData("Katona Márton Barnabás", "Főmenedzsment • Front-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_marton)!!, "https://Marci599.github.io"),
            DevData("Bencsik Gergő", "Menedzsment • Back-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_gergo)!!, "https://github.com/lolfail"),
            DevData("Bende Ákos György", "Menedzsment • Szerver", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_gyorgy)!!, "https://github.com/kutzlect"),
            DevData("Kovács Gábor", "Front-end", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_gabor)!!, "https://github.com/mrSajt2"),
            DevData("Juhos Gergely János", "Marketing", AppCompatResources.getDrawable(requireContext(), R.drawable.pfp_juhos)!!, "https://github.com/enderember003")
        )

        devArrayList.shuffle()

        getFooterBinding().recyclerView.adapter = DevRecyclerAdapter(devArrayList)

        getFooterBinding().cbSupport.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW,
                "https://www.buymeacoffee.com/kollersystems".toUri())
            startActivity(browserIntent)
        }

        getFooterBinding().cbGithub.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW,
                "https://github.com/KollerSystems".toUri())
            startActivity(browserIntent)
        }

        getFooterBinding().cbEmail.setOnClickListener{

            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = ("mailto:contact@norbert.hu?body=" +
                    "Institution ID: -\n" +
                    "App Version: ${getString(R.string.version)}\n" +
                    "User ID: ${CacheManager.currentUserId}\n").toUri()
            startActivity(intent)
        }
    }


    companion object {
        const val TAG = "ProfileBottomSheet"
    }
}