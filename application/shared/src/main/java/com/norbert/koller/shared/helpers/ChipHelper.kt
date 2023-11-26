package com.norbert.koller.shared.helpers

import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentManager
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.MaterialDatePicker
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.checkByPass
import com.norbert.koller.shared.data.FilterDateData
import com.norbert.koller.shared.data.FiltersData
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragment
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.shared.restoreDropDown

class ChipHelper {


}

fun Chip.connectToDateRangePicker(fragmentManager : FragmentManager, filterName : String = "Date"){

    setOnClickListener {

        val dprdb = MaterialDatePicker.Builder.dateRangePicker()

        if (tag is FilterDateData) {
            dprdb.setSelection((tag as FilterDateData).filterFrom)
        }

        val drpd = dprdb.build()

        drpd.addOnPositiveButtonClickListener { selection ->

            var stringForChip = java.text.SimpleDateFormat(DateTimeHelper.shortMonthDayFormat).format(selection.first)
            if (selection.first != selection.second) {
                stringForChip += " - ${java.text.SimpleDateFormat(DateTimeHelper.shortMonthDayFormat).format(selection.second)}"
            }
            tag = FilterDateData(filterName, selection)
            if(text.toString() != stringForChip) {
                text = stringForChip
            }

            checkByPass(true)

            this.addCloseOption(R.string.date)
        }

        drpd.show(fragmentManager, "MATERIAL_DATE_RANGE_PICKER")

    }
}

fun Chip.connectToCheckBoxList(fragmentManager: FragmentManager, filterName : String, localizedFilterId : Int, arrayList : ArrayList<ListItem>){


    setOnClickListener {

        var filterTo : ArrayList<String>? = null
        if(tag is FiltersData){
            filterTo = (tag as FiltersData).filterTo
        }

        val dialog = ItemListDialogFragment(arrayList, filterTo)


        dialog.getValuesOnFinish = {values, locNames ->


            val hasValues = values.size != 0
            checkByPass(hasValues)
            if(hasValues) {
                tag = FiltersData(filterName, values)
                if(text.toString() != locNames){
                    text = locNames
                    this.addCloseOption(localizedFilterId)
                }


            }
            else{
                tag = null
                val string = context.getString(localizedFilterId)
                if(text.toString() != string) {
                    text = string
                    restoreDropDown()
                }
            }

        }

        dialog.show(fragmentManager, ItemListDialogFragment.TAG)


    }

}

fun Chip.addCloseOption(localizedFilterId : Int){
    closeIcon = AppCompatResources.getDrawable(context, R.drawable.close_thick)
    setOnCloseIconClickListener {
        restoreDropDown()
        tag = null
        text = context.getString(localizedFilterId)
        checkByPass(false)
    }
}