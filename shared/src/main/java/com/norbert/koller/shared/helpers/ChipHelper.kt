package com.norbert.koller.shared.helpers

import androidx.appcompat.content.res.AppCompatResources
import androidx.core.util.Pair
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewModelScope
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.fragments.bottomsheet.ListApiBsdFragment
import com.norbert.koller.shared.managers.checkByPass
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListStaticBsdFragment
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.formatDate
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.managers.restoreDropDown
import com.norbert.koller.shared.viewmodels.BaseViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
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

fun Chip.resetChip(localizedNameSting : String, viewModel: BaseViewModel, filterName : String){
    viewModel.filters.remove(filterName)
    resetSimpleChip(localizedNameSting)
}

fun Chip.setChip(localizedNameSting : String, localizedFilterId : Int, viewModel: BaseViewModel, filterName : String){
    checkByPass(true)
    text = localizedNameSting
    this.addCloseOption {
        resetChip(context.getString(localizedFilterId), viewModel, filterName)
    }
}

fun Chip.createAndSetChipText(viewModel: BaseViewModel, filterName: String, tag: String, localizedFilterId : Int){
    val strings : ArrayList<String> = arrayListOf()

    for (id in viewModel.filters[filterName]!!){
        for (elements in CacheManager.savedListsOfValues[tag]!!){
            if(id == elements.getMainID().toString()){
                strings.add(elements.getTitle())
            }
        }
    }
    setChip(arrayToString(strings), localizedFilterId, viewModel, filterName)

}

fun Chip.handleValuesOnFinish(fragmentManager : FragmentManager, dialog : ListBsdFragment, viewModel: BaseViewModel, filterName: String, localizedFilterId: Int){

    dialog.show(fragmentManager, ListBsdFragment.TAG)

    if(dialog.getValuesOnFinish == null){
    dialog.getValuesOnFinish = { values, locNames ->


        if (values.size != 0) {
            val locNamesString = arrayToString(locNames)
            if (text.toString() != locNamesString) {
                viewModel.filters[filterName] = values
                setChip(locNamesString, localizedFilterId, viewModel, filterName)
            }
        } else {

            val string = context.getString(localizedFilterId)
            if (text.toString() != string) {
                viewModel.filters.remove(filterName)
                resetChip(string, viewModel, filterName)
            }
        }
    }
    }
}

fun Chip.connectToCheckBoxList(fragmentManager: FragmentManager, filterName : String, localizedFilterId : Int, getValues: suspend () -> Response<*>, viewModel: BaseViewModel, tag : String, collapseText : Boolean){

    if(viewModel.filters.containsKey(filterName)){


         viewModel.viewModelScope.launch {

            if(CacheManager.savedListsOfValues.containsKey(tag)){
                createAndSetChipText(viewModel, filterName, tag, localizedFilterId)
            }
            else{
                setChip(context.getString(R.string.loading), localizedFilterId, viewModel, filterName)

                RetrofitInstance.communicate(getValues, {
                    CacheManager.savedListsOfValues[tag] = it as ArrayList<BaseData>
                    createAndSetChipText(viewModel, filterName, tag, localizedFilterId)

                },{
                        error, errorBody ->
                    setChip(context.getString(R.string.an_error_occurred), localizedFilterId, viewModel, filterName)
                })
            }




        }


    }
    else{
        resetSimpleChip(context.getString(localizedFilterId))
    }

    setOnClickListener {


        val dialog = ListApiBsdFragment(getValues, viewModel.filters[filterName], tag, localizedFilterId)
        dialog.collapseText = collapseText

        handleValuesOnFinish(fragmentManager, dialog, viewModel, filterName, localizedFilterId)

    }
}

fun Chip.connectToCheckBoxList(fragmentManager: FragmentManager, filterName : String, localizedFilterId : Int, arrayList : ArrayList<ListItem>, viewModel: BaseViewModel){


    if(viewModel.filters.containsKey(filterName)){
        val localizedStrings : ArrayList<String> = arrayListOf()

        for (tag in viewModel.filters[filterName]!!){
            for (elements in arrayList){
                if(tag == elements.tag){
                    localizedStrings.add(elements.title)
                }
            }
        }


        setChip(arrayToString(localizedStrings), localizedFilterId, viewModel, filterName)
    }
    else{
        resetSimpleChip(context.getString(localizedFilterId))
    }

    setOnClickListener {


        val dialog = ListStaticBsdFragment(arrayList, viewModel.filters[filterName], localizedFilterId)


        handleValuesOnFinish(fragmentManager, dialog, viewModel, filterName, localizedFilterId)
    }
}

fun arrayToString(arrayList: ArrayList<String>) : String{
    return Arrays.toString(arrayList.toArray()).replace("[", "").replace("]", "")
}

fun dateDoubleToString(date : Pair<Long, Long>) : String{
    var stringForChip = date.first.formatDate(DateTimeHelper.shortMonthDayFormat)
    if (date.first != date.second) {
        stringForChip += " - ${date.second.formatDate(DateTimeHelper.shortMonthDayFormat)}"
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
