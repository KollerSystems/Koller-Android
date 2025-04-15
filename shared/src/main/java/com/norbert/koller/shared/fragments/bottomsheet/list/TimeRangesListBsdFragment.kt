package com.norbert.koller.shared.fragments.bottomsheet.list

import com.google.android.material.datepicker.MaterialDatePicker
import com.norbert.koller.shared.helpers.ChipHelper
import com.norbert.koller.shared.helpers.dateDoubleToString

class TimeRangesListBsdFragment  {

    /*ListCardStaticBsdfFragment().setup(((context as ContextWrapper).baseContext as AppCompatActivity), arrayListOf(
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
    ), context.getString(R.string.date)).show(fragmentManager, ListBsdfFragment.TAG)*/

}