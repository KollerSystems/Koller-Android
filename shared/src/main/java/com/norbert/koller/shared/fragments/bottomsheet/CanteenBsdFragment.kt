package com.norbert.koller.shared.fragments.bottomsheet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.norbert.koller.shared.R
import com.norbert.koller.shared.customviews.EditText
import com.norbert.koller.shared.data.CanteenData
import com.norbert.koller.shared.databinding.FragmentBsdCanteenBinding
import com.norbert.koller.shared.viewmodels.ResponseViewModel


class CanteenBsdFragment(val canteenData: CanteenData? = null) : ScrollBsdFragment() {

    lateinit var binding : FragmentBsdCanteenBinding
    lateinit var viewModel: ResponseViewModel

    override fun getContent(inflater: LayoutInflater): ViewGroup {
        binding = FragmentBsdCanteenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]

        if(canteenData != null){
            viewModel.response.value = canteenData
        }

        val canteenData = (viewModel.response.value as CanteenData)
        val title = setTitle(canteenData.foodName)
        title.setTextIsSelectable(true)
        val actionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                menu.add(0, 1, 0, R.string.search_on_google)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
                return false
            }

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                return if (1 == item.itemId) {
                    val start = title.selectionStart
                    val end = title.selectionEnd

                    // Kijelölt szöveg lekérdezése
                    val selectedText = title.text.substring(start, end)
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?tbm=isch&q=${selectedText}"))
                    startActivity(intent)
                    true
                } else {
                    false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                // No action needed
            }
        }

        title.customSelectionActionModeCallback = actionModeCallback
    }

    companion object {
        const val TAG = "CanteenBsdFragment"
    }
}