package com.norbert.koller.shared.helpers

import androidx.appcompat.content.res.AppCompatResources
import androidx.core.util.Pair
import androidx.fragment.app.FragmentManager
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.checkByPass
import com.norbert.koller.shared.data.FilterDateData
import com.norbert.koller.shared.data.FiltersData
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragment
import com.norbert.koller.shared.recycleradapter.BasePagingSource
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.shared.restoreDropDown
import com.norbert.koller.shared.viewmodels.BaseViewModel
import java.util.Arrays

class ChipHelper {


}

fun Chip.connectToDateRangePicker(fragmentManager : FragmentManager, filterName : String = "Date", viewModel: BaseViewModel){


    fun setChip(dateString : String){
        checkByPass(true)
        text = dateString
        this.addCloseOption {
            viewModel.dateFilters.remove(filterName)
            resetSimpleChip(context.getString(R.string.date))
        }

    }

    if(viewModel.dateFilters.containsKey(filterName)){
        setChip(dateDoubleToString(viewModel.dateFilters[filterName]!!))
    }
    else{
        resetSimpleChip(context.getString(R.string.date))
    }

    setOnClickListener {

        val dprdb = MaterialDatePicker.Builder.dateRangePicker()

        if (viewModel.dateFilters.containsKey(filterName)) {
            dprdb.setSelection(viewModel.dateFilters[filterName])
        }

        val drpd = dprdb.build()

        drpd.addOnPositiveButtonClickListener { selection ->

            val dateString = dateDoubleToString(selection)
            if(text.toString() != dateString) {
                viewModel.dateFilters[filterName] = selection
                setChip(dateDoubleToString(selection))
            }
        }

        drpd.show(fragmentManager, "MATERIAL_DATE_RANGE_PICKER")

    }
}

fun Chip.connectToCheckBoxList(fragmentManager: FragmentManager, filterName : String, localizedFilterId : Int, arrayList : ArrayList<ListItem>, viewModel: BaseViewModel){

    fun resetChip(localizedNameSting : String){
        viewModel.filters.remove(filterName)
        resetSimpleChip(localizedNameSting)
    }

    fun setChip(localizedNameSting : String){
        checkByPass(true)
        text = localizedNameSting
        this.addCloseOption {
            resetChip(context.getString(localizedFilterId))
        }
    }

    if(viewModel.filters.containsKey(filterName)){
        setChip(arrayToString(viewModel.filters[filterName]!!))
    }
    else{
        resetSimpleChip(context.getString(localizedFilterId))
    }

    setOnClickListener {


        val dialog = ItemListDialogFragment(arrayList, viewModel.filters[filterName])


        dialog.getValuesOnFinish = {values, locNames ->

            if(values.size != 0) {
                val locNamesString = arrayToString(locNames)
                if(text.toString() != locNamesString) {
                    viewModel.filters[filterName] = values
                    setChip(locNamesString)
                }
            }
            else{
                val string = context.getString(localizedFilterId)
                if(text.toString() != string) {
                    viewModel.filters.remove(filterName)
                    resetChip(string)
                }
            }

        }

        dialog.show(fragmentManager, ItemListDialogFragment.TAG)
    }
}

fun arrayToString(arrayList: ArrayList<String>) : String{
    return Arrays.toString(arrayList.toArray()).replace("[", "").replace("]", "")
}

fun dateDoubleToString(date : Pair<Long, Long>) : String{
    var stringForChip = java.text.SimpleDateFormat(DateTimeHelper.shortMonthDayFormat).format(date.first)
    if (date.first != date.second) {
        stringForChip += " - ${java.text.SimpleDateFormat(DateTimeHelper.shortMonthDayFormat).format(date.second)}"
    }

    return stringForChip
}

fun Chip.addCloseOption(onClose: (()-> Unit)){
    closeIcon = AppCompatResources.getDrawable(context, R.drawable.close_thick)
    setOnCloseIconClickListener {
        onClose()
    }
}

fun Chip.resetSimpleChip(localizedFilterString : String){
    restoreDropDown()
    checkByPass(false)
    text = localizedFilterString

}
