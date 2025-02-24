package com.norbert.koller.shared.recycleradapters

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.android.material.chip.Chip
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.ApiDataObject
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.ErrorData
import com.norbert.koller.shared.data.ExpiringListData
import com.norbert.koller.shared.helpers.ApiHelper
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.managers.formatDate
import com.norbert.koller.shared.viewmodels.DetailsViewModel
import com.norbert.koller.shared.viewmodels.ListApiComplexViewModel
import com.norbert.koller.shared.viewmodels.ListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Date
import kotlin.random.Random


class PagingSourceWithSeparator(context: Context, viewModel: ListApiComplexViewModel) : com.norbert.koller.shared.recycleradapters.PagingSource(context, viewModel)  {

    fun getListApiComplexViewModel() : ListApiComplexViewModel{
        return viewModel as ListApiComplexViewModel
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