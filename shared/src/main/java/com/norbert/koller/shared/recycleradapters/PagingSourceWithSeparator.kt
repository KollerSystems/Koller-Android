package com.norbert.koller.shared.recycleradapters

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.helpers.ApiHelper
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.formatDate
import com.norbert.koller.shared.viewmodels.ListApiViewModel


class PagingSourceWithSeparator(context: Context, viewModel: ListApiViewModel) : com.norbert.koller.shared.recycleradapters.PagingSource(context, viewModel)  {

    fun getListApiComplexViewModel() : ListApiViewModel{
        return viewModel as ListApiViewModel
    }

    private var lastFirstChar : String? = null

    @SuppressLint("WeekBasedYear")
    override fun getFilters() : String{

        var finalString = super.getFilters()

        for ((key, value) in (getListApiComplexViewModel().filters)) {

            for (argument in value) {

                finalString += "${key}:${argument},"
            }
        }

        for ((key, value) in (getListApiComplexViewModel().dateFilters)) {
            finalString += "${key}[gte]:${value.first.formatDate(DateTimeHelper.apiFormat) + "T00:00:00.000Z"},${key}[lte]:${SimpleDateFormat(DateTimeHelper.apiFormat).format(value.second) + "T00:00:00.000Z"},"
        }
        finalString.dropLast(1)

        return finalString
    }

    override fun getSort() : String{
        return getListApiComplexViewModel().sortOptions[getListApiComplexViewModel().selectedSort]
    }


    override fun areParametersDefault() : Boolean {
        return (super.areParametersDefault() &&
                getListApiComplexViewModel().filters.isEmpty() &&
                getListApiComplexViewModel().dateFilters.isEmpty() &&
                getListApiComplexViewModel().selectedSort == 0)
    }

    override fun formatRecievedValues(responseAs : List<BaseData>, offset : Int): LoadResult<Int, Any> {
        viewModel.state = ApiHelper.STATE_NONE

        lastListSize = responseAs.size
        val items = mutableListOf<Any>()

        for (data in responseAs) {
            val firstChar = data.diffrentDecider(context)
            if (firstChar != lastFirstChar) {
                items.add(firstChar)
                lastFirstChar = firstChar
            }
            items.add(data)
        }

        return LoadResult.Page(
            data = items,
            prevKey = if (offset == 0) null else offset - responseAs.size,
            nextKey = if (items.isEmpty()) null else offset + responseAs.size
        )
    }
}