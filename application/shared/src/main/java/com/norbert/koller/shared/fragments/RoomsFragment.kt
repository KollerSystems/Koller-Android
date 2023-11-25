package com.norbert.koller.shared.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.RoomPagingSource
import com.norbert.koller.shared.customview.ExtraEditText
import com.norbert.koller.shared.customview.SuperCoolRecyclerView
import com.norbert.koller.shared.data.FilterBaseData
import com.norbert.koller.shared.helpers.connectToCheckBoxList
import com.norbert.koller.shared.recycleradapter.BaseViewModel
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.shared.recycleradapter.RoomRecyclerAdapter
import com.norbert.koller.shared.setVisibilityBy
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


open class RoomsFragment : Fragment() {

    private lateinit var roomsRecyclerView: SuperCoolRecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mcardSearch : MaterialCardView = view.findViewById(R.id.mcard_search)
        val editTextSearch : ExtraEditText = view.findViewById(R.id.editText_search)
        val buttonSearchCancel : Button = view.findViewById(R.id.button_search)

        val chipGroupFilters : ChipGroup = view.findViewById(R.id.chip_group_filter)

        val lyFilters : LinearLayout = view.findViewById(R.id.ly_filters)


        mcardSearch.setOnClickListener{
            editTextSearch.requestFocus()
        }

        editTextSearch.doOnTextChanged{_,_,_,_->
            buttonSearchCancel.setVisibilityBy(!editTextSearch.text.isNullOrEmpty())
        }

        buttonSearchCancel.setOnClickListener{
            editTextSearch.setText("")
        }

        fun checkIfFiltersShouldBeShowed(){
            if(editTextSearch.isFocused){
                lyFilters.setVisibilityBy(true)
            }
            else{
                var anyOfHas = false
                for (chip : View in chipGroupFilters.children){
                    if(chip.tag is FilterBaseData){
                        anyOfHas = true
                    }
                }
                lyFilters.setVisibilityBy(anyOfHas)
            }
        }

        editTextSearch.setOnFocusChangeListener{focusedView, isFocused ->
            checkIfFiltersShouldBeShowed()
        }

        for (chip : View in chipGroupFilters.children){
            (chip as Chip).doOnTextChanged{_,_,_,_ ->
                checkIfFiltersShouldBeShowed()
                editTextSearch.clearFocus()
            }
        }

        roomsRecyclerView = view.findViewById(R.id.super_cool_recycler_view)
        roomsRecyclerView.recyclerView.layoutManager = LinearLayoutManager(context)

        val chipGroup : ChipGroup = view.findViewById(R.id.chip_group_sort)

        val chipSide : Chip = view.findViewById(R.id.chip_side)

        chipSide.connectToCheckBoxList(childFragmentManager, "Gender", R.string.side, arrayListOf(
            ListItem( getString(R.string.girl), null, AppCompatResources.getDrawable(requireContext(), R.drawable.woman), "0"),
            ListItem( getString(R.string.boy), null, AppCompatResources.getDrawable(requireContext(), R.drawable.man), "1")
        ))

        val roomRecycleAdapter = RoomRecyclerAdapter(chipGroup, listOf(chipSide))
        val viewModel = BaseViewModel { RoomPagingSource(requireContext(), roomRecycleAdapter) }


        roomsRecyclerView.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = roomRecycleAdapter
        }


        roomsRecyclerView.appBar = view.findViewById(R.id.appbar_layout)

        lifecycleScope.launch {
            viewModel.pagingData.collectLatest { pagingData ->
                roomRecycleAdapter.submitData(pagingData)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_rooms, container, false)
    }
}