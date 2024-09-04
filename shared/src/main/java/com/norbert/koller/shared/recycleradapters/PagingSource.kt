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
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.ErrorData
import com.norbert.koller.shared.helpers.ApiHelper
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.managers.formatDate
import com.norbert.koller.shared.viewmodels.DetailsViewModel
import com.norbert.koller.shared.viewmodels.ListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Date
import kotlin.random.Random


abstract class PagingSource(val context: Context, val viewModel: ListViewModel) : PagingSource<Int, Any>()  {

    private var lastFirstChar : String? = null
    private var lastListSize : Int = ListViewModel.pageSize + 1

    open fun getTimeLimit() : Int{
        return DateTimeHelper.TIME_NOT_IMPORTANT
    }

    abstract fun getDataType() : Class<*>
    abstract fun getDataTag() : String

    @SuppressLint("WeekBasedYear")
    fun getFilters() : String{

        var finalString = ""

        for ((key, value) in (viewModel.filters)) {

            for (argument in value) {

                finalString += "${key}:${argument},"
            }
        }

        for ((key, value) in (viewModel.dateFilters)) {
            finalString += "${key}[gte]:${value.first.formatDate(DateTimeHelper.apiFormat) + "T00:00:00.000Z"},${key}[lte]:${SimpleDateFormat(DateTimeHelper.apiFormat).format(value.second) + "T00:00:00.000Z"},"
        }
        finalString.dropLast(1)

        return finalString
    }

    fun getSort() : String{
        return viewModel.sortOptions[viewModel.selectedSort]
    }

    abstract suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int) : Response<List<BaseData>>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {

        Log.d("LISTGET", viewModel.sortOptions[viewModel.selectedSort]+"<----",)

        var offset = params.key ?: 0
        if(viewModel.isRequestModeRefresh) {
            offset = 0

            viewModel.state = ApiHelper.STATE_LOADING
            var pagingSource : LoadResult<Int, Any>? = null

            delay(Random.nextLong((APIInterface.loadingDelayFrom * 1000).toLong(), (APIInterface.loadingDelayTo * 1000 + 1).toLong()))

            RetrofitInstance.communicate({getApiResponse(RetrofitInstance.api, params.loadSize, offset)}, {response ->
                pagingSource = onSuccess(response, offset, params)
                viewModel.onRefreshSuccess()
            }) { error, errorBody ->
                pagingSource = onError(error, errorBody)
                viewModel.onRefreshError()
            }

            return pagingSource!!
        }
        else{
            if (viewModel.beingEmptied || lastListSize < ListViewModel.pageSize) {
                viewModel.beingEmptied = false
                offset = 0
                return LoadResult.Page(emptyList(), null, null)
            }

            if(viewModel.shouldBeEmpty){
                offset = 0
                viewModel.shouldBeEmpty = false
            }

            if (offset <= 0 && areParametersDefault()) {

                if (CacheManager.savedListsOfValues.containsKey(getDataTag()) && (CacheManager.savedListsOfValues[getDataTag()]!![0].isValid(
                        context,
                        getTimeLimit()
                    ))
                ) {
                    val savedValues = CacheManager.savedListsOfValues[getDataTag()]!!
                    viewModel.onAppendSuccess()
                    return formatRecievedValues(savedValues, 0, savedValues.size)
                }

                viewModel.state = ApiHelper.STATE_LOADING

                val baseDataList = DataStoreManager.readList(context, getDataTag(), getDataType())
                    ?.toCollection(java.util.ArrayList())

                if (baseDataList != null && baseDataList[0].isValid(context, getTimeLimit())) {
                    CacheManager.savedListsOfValues[getDataTag()] = baseDataList
                    viewModel.onAppendSuccess()
                    return formatRecievedValues(baseDataList, 0, baseDataList.size)
                }
            }

            viewModel.onAppendLoading()
            viewModel.state = ApiHelper.STATE_LOADING
            var pagingSource : LoadResult<Int, Any>? = null

            delay(Random.nextLong((APIInterface.loadingDelayFrom * 1000).toLong(), (APIInterface.loadingDelayTo * 1000 + 1).toLong()))

            RetrofitInstance.communicate({getApiResponse(RetrofitInstance.api, params.loadSize, offset)}, {response ->
                pagingSource = onSuccess(response, offset, params)
                viewModel.onAppendSuccess()
            }) { error, errorBody ->
                pagingSource = onError(error, errorBody)
                viewModel.onAppendError()
            }

            return pagingSource!!
        }
    }

    fun areParametersDefault() : Boolean {
        return ((viewModel.filters).isEmpty() &&
                (viewModel.dateFilters).isEmpty() &&
                viewModel.selectedSort == 0)
    }

    fun onSuccess(response : Any, offset: Int, params: LoadParams<Int>) : LoadResult<Int, Any>{
        response as List<BaseData>

        if(viewModel.selectedSort == 0 && response.isNotEmpty()) {
            response[0].saveReceivedTime()
            if (!CacheManager.savedListsOfValues.containsKey(getDataTag())) {
                CacheManager.savedListsOfValues[getDataTag()] = ArrayList()
            }
            CacheManager.savedListsOfValues[getDataTag()]!!.addAll(response)
        }

        val pagingSource = formatRecievedValues(response, offset, params.loadSize)
        viewModel.isRequestModeRefresh = false

        return pagingSource
    }

    fun onError(error: String?, errorBody: ErrorData?) : LoadResult<Int, Any>{
        viewModel.state = ApiHelper.STATE_ERROR
        val pagingSource : LoadResult<Int, Any> = LoadResult.Error(Throwable("Error: $error. Error body: $errorBody"))
        return pagingSource
    }

    fun formatRecievedValues(responseAs : List<BaseData>, offset : Int, limit: Int): LoadResult<Int, Any> {
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
            prevKey = if (offset == 0) null else offset - limit,
            nextKey = if (items.isEmpty()) null else offset + limit
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
        return state.anchorPosition
    }
}