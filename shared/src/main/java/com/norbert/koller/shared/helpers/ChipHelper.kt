package com.norbert.koller.shared.helpers

import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.util.Pair
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewModelScope
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObject
import com.norbert.koller.shared.data.ListCardItem
import com.norbert.koller.shared.data.ListItem
import com.norbert.koller.shared.data.ListToggleItem
import com.norbert.koller.shared.managers.checkByPass
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListCardStaticBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListToggleApiBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListToggleStaticBsdfFragment
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.managers.formatDate
import com.norbert.koller.shared.managers.restoreDropDown
import com.norbert.koller.shared.viewmodels.ListApiComplexViewModel
import kotlinx.coroutines.launch
import java.util.Arrays
import java.util.Calendar

class ChipHelper {

    companion object{
        fun getDateFilterValue(viewModel: ListApiComplexViewModel) : MutableMap<String, Pair<Long, Long>>{
            return viewModel.dateFilters
        }

        fun getFilterValue(viewModel: ListApiComplexViewModel) : MutableMap<String, MutableSet<Int>>{
            return viewModel.filters
        }

        fun changeFilterValue(viewModel: ListApiComplexViewModel, filterName : String, selection : MutableSet<Int>){
            getFilterValue(viewModel)[filterName] = selection
            viewModel.onChipsChanged?.invoke()
        }

        fun changeDateFilterValue(viewModel: ListApiComplexViewModel, filterName : String, selection : Pair<Long, Long>){
            getDateFilterValue(viewModel)[filterName] = selection
            viewModel.onChipsChanged?.invoke()
        }

        fun removeFilterValue(viewModel: ListApiComplexViewModel, filterName : String){
            getFilterValue(viewModel).remove(filterName)
            viewModel.onChipsChanged?.invoke()
        }

        fun removeDateFilterValue(viewModel: ListApiComplexViewModel, filterName : String = "Date"){
            getDateFilterValue(viewModel).remove(filterName)
            viewModel.onChipsChanged?.invoke()
        }
    }
    
    

}

fun Chip.connectToDateRangePicker(fragmentManager : FragmentManager, filterName : String = "Date", viewModel: ListApiComplexViewModel, localizedId : Int){



    fun setChip(dateString : String){
        checkByPass(true)
        text = dateString
        this.addCloseOption {
            ChipHelper.removeDateFilterValue(viewModel, filterName)
            resetSimpleChip(context.getString(localizedId))
        }

    }

    if(ChipHelper.getDateFilterValue(viewModel).containsKey(filterName)){
        setChip(dateDoubleToString(ChipHelper.getDateFilterValue(viewModel)[filterName]!!))
    }
    else{
        resetSimpleChip(context.getString(localizedId))
    }

    setOnClickListener {

        val dprdb = MaterialDatePicker.Builder.dateRangePicker()

        if (ChipHelper.getDateFilterValue(viewModel).containsKey(filterName)) {
            dprdb.setSelection(ChipHelper.getDateFilterValue(viewModel)[filterName])
        }

        val drpd = dprdb.build()

        drpd.addOnPositiveButtonClickListener { selection ->

            val dateString = dateDoubleToString(selection)
            if(text.toString() != dateString) {
                ChipHelper.changeDateFilterValue(viewModel, filterName, selection)
                setChip(dateDoubleToString(selection))
            }
        }

        drpd.show(fragmentManager, "MATERIAL_DATE_RANGE_PICKER")

    }
}

fun Chip.connectToDateRangePickerWithTemplates(fragmentManager : FragmentManager, filterName : String = "Date", viewModel: ListApiComplexViewModel){


    fun setChip(dateString : String){
        checkByPass(true)
        text = dateString
        this.addCloseOption {
            ChipHelper.getDateFilterValue(viewModel).remove(filterName)
            resetSimpleChip(context.getString(R.string.date))
        }

    }

    if(ChipHelper.getDateFilterValue(viewModel).containsKey(filterName)){
        setChip(dateDoubleToString(ChipHelper.getDateFilterValue(viewModel)[filterName]!!))
    }
    else{
        resetSimpleChip(context.getString(R.string.date))
    }

    fun getEndOfTodayAndStartOfSpecificDayAgo(specificDay : Int): Pair<Long, Long> {
        val calendar = Calendar.getInstance()

        // A mai nap vége (23:59:59.999)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfTodayInMillis = calendar.timeInMillis

        // Visszalépés 7 nappal (a 7. nap eleje, 00:00)
        calendar.add(Calendar.DAY_OF_YEAR, -specificDay)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfSeventhDayAgoInMillis = calendar.timeInMillis

        return Pair(startOfSeventhDayAgoInMillis, endOfTodayInMillis)
    }

    fun setViewModelAndChip(selection : Pair<Long, Long>){

        ChipHelper.changeDateFilterValue(viewModel, filterName, selection)
        setChip(dateDoubleToString(selection))
    }

    setOnClickListener {

        ListCardStaticBsdfFragment().setup(((context as ContextWrapper).baseContext as AppCompatActivity), arrayListOf(
            ListCardItem(context.getString(R.string.this_day), null, AppCompatResources.getDrawable(context, R.drawable.today), {
                setViewModelAndChip(getEndOfTodayAndStartOfSpecificDayAgo(0))
            }),
            ListCardItem(context.getString(R.string.one_week), null, AppCompatResources.getDrawable(context, R.drawable.week), {
                setViewModelAndChip(getEndOfTodayAndStartOfSpecificDayAgo(7))
            }),
            ListCardItem(context.getString(R.string.four_weeks), null, AppCompatResources.getDrawable(context, R.drawable.month), {
                setViewModelAndChip(getEndOfTodayAndStartOfSpecificDayAgo(7*4))
            }),
            ListCardItem(context.getString(R.string.first_quarter), null, AppCompatResources.getDrawable(context, R.drawable.quarter), {

            }),
            ListCardItem(context.getString(R.string.half_year), null, AppCompatResources.getDrawable(context, R.drawable.half), {

            }),
            ListCardItem(context.getString(R.string.third_quarter), null, AppCompatResources.getDrawable(context, R.drawable.three_quarter), {

            }),
            ListCardItem(context.getString(R.string.full_year), null, AppCompatResources.getDrawable(context, R.drawable.full), {

            }),
            ListCardItem(context.getString(R.string.custom_range), null, AppCompatResources.getDrawable(context, R.drawable.date_range), {
                val dprdb = MaterialDatePicker.Builder.dateRangePicker()

                if (ChipHelper.getDateFilterValue(viewModel).containsKey(filterName)) {
                    dprdb.setSelection(ChipHelper.getDateFilterValue(viewModel)[filterName])
                }

                val drpd = dprdb.build()

                drpd.addOnPositiveButtonClickListener { selection ->

                    val dateString = dateDoubleToString(selection)
                    if(text.toString() != dateString) {
                        setViewModelAndChip(selection)
                    }
                }

                drpd.show(fragmentManager, "MATERIAL_DATE_RANGE_PICKER")
            })
        ), context.getString(R.string.date)).show(fragmentManager, ListBsdfFragment.TAG)
    }
}

fun Chip.resetChip(localizedNameSting : String, viewModel: ListApiComplexViewModel, filterName : String){
    ChipHelper.removeFilterValue(viewModel, filterName)
    resetSimpleChip(localizedNameSting)
}

fun Chip.setChip(localizedNameSting : String, localizedFilterId : Int, viewModel: ListApiComplexViewModel, filterName : String){
    checkByPass(true)
    text = localizedNameSting
    this.addCloseOption {
        resetChip(context.getString(localizedFilterId), viewModel, filterName)
    }
}

fun Chip.createAndSetChipText(viewModel: ListApiComplexViewModel, filterName: String, classOfT: Class<*>, localizedFilterId : Int){
    val strings : ArrayList<String> = arrayListOf()

    viewModel.viewModelScope.launch {
        for (id in ChipHelper.getFilterValue(viewModel)[filterName]!!){
            for (elements in CacheManager.getListDataMapWithValues(context, classOfT)){
                if(id == elements.getMainID()){
                    strings.add(elements.getTitle())
                }
            }
        }
        setChip(arrayToString(strings), localizedFilterId, viewModel, filterName)
    }
}

fun Chip.handleValuesOnFinish(fragmentManager : FragmentManager, dialog : ListBsdfFragment, viewModel: ListApiComplexViewModel, filterName: String, localizedFilterId: Int){

    dialog.show(fragmentManager, ListBsdfFragment.TAG)

    if(dialog.getValuesOnFinish == null){
        dialog.getValuesOnFinish = { values, locNames ->


            if (values.size != 0) {
                val locNamesString = arrayToString(locNames)
                if (text.toString() != locNamesString) {
                    ChipHelper.changeFilterValue(viewModel, filterName, values)
                    setChip(locNamesString, localizedFilterId, viewModel, filterName)
                }
            } else {

                val string = context.getString(localizedFilterId)
                if (text.toString() != string) {
                    ChipHelper.removeFilterValue(viewModel, filterName)
                    resetChip(string, viewModel, filterName)
                }
            }
        }
    }
}

fun Chip.connectToCheckBoxList(fragmentManager: FragmentManager, filterName : String, localizedFilterId : Int, apiDataObject: ApiDataObject, viewModel: ListApiComplexViewModel, collapseText : Boolean){

    if(ChipHelper.getFilterValue(viewModel).containsKey(filterName)){

         viewModel.viewModelScope.launch {
            if(CacheManager.listDataMap.containsKey(apiDataObject.getDataType().simpleName)){
                createAndSetChipText(viewModel, filterName, apiDataObject.getDataType(), localizedFilterId)
            }
            else{
                setChip(context.getString(R.string.loading), localizedFilterId, viewModel, filterName)

                val baseDataList = DataStoreManager.readList(context, apiDataObject.getDataType())

                CacheManager.listDataMap[apiDataObject.getDataType().simpleName] = baseDataList!!
                createAndSetChipText(viewModel, filterName, apiDataObject.getDataType(), localizedFilterId)

            }
        }
    }
    else{
        resetSimpleChip(context.getString(localizedFilterId))
    }

    setOnClickListener {


        val dialog = ListToggleApiBsdfFragment().setup(((context as ContextWrapper).baseContext as AppCompatActivity), apiDataObject, ChipHelper.getFilterValue(viewModel)[filterName], context.getString(localizedFilterId), collapseText)


        handleValuesOnFinish(fragmentManager, dialog, viewModel, filterName, localizedFilterId)

    }
}

fun Chip.connectToCheckBoxList(fragmentManager: FragmentManager, filterName : String, localizedFilterId : Int, arrayList : ArrayList<ListItem>, viewModel: ListApiComplexViewModel){


    if(ChipHelper.getFilterValue(viewModel).containsKey(filterName)){
        val localizedStrings : ArrayList<String> = arrayListOf()

        for (tag in ChipHelper.getFilterValue(viewModel)[filterName]!!){
            for (elements in arrayList){
                elements as ListToggleItem
                if(tag == elements.id){
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


        val dialog = ListToggleStaticBsdfFragment().setup(((context as ContextWrapper).baseContext as AppCompatActivity), arrayList, ChipHelper.getFilterValue(viewModel)[filterName], context.getString(localizedFilterId))


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
