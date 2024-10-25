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
import com.norbert.koller.shared.viewmodels.ListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Date
import kotlin.random.Random


class PagingSourceWithSeparator(val apiDataObject: ApiDataObject, val context: Context, val viewModel: ListViewModel) : PagingSource<Int, Any>()  {

    private var lastFirstChar : String? = null
    private var lastListSize : Int = ListViewModel.pageSize + 1

    open fun getTimeLimit() : Int{
        return DateTimeHelper.TIME_NOT_IMPORTANT
    }

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

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {



        var offset = params.key ?: 0
        if(viewModel.isRequestModeRefresh) {
            offset = 0

            viewModel.state = ApiHelper.STATE_LOADING
            var pagingSource : LoadResult<Int, Any>? = null

            delay(Random.nextLong((APIInterface.loadingDelayFrom * 1000).toLong(), (APIInterface.loadingDelayTo * 1000 + 1).toLong()))

            RetrofitInstance.communicate({apiDataObject.getApiResponse(params.loadSize, offset, getSort(), getFilters())}, {response ->
                pagingSource = onSuccess(response, offset)
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

                if (CacheManager.listDataMap.containsKey(apiDataObject.getDataType().simpleName)) {
                    if((CacheManager.listDataMap[apiDataObject.getDataType().simpleName]!!.isValid(context, getTimeLimit()))){
                        viewModel.onAppendSuccess()
                        return formatRecievedValues(CacheManager.getListDataMapWithValues(context, apiDataObject.getDataType()), 0)
                    }
                }

                viewModel.state = ApiHelper.STATE_LOADING

                val baseDataList = DataStoreManager.readList(context, apiDataObject.getDataType())

                if (baseDataList != null) {

                    if(baseDataList.isValid(context, getTimeLimit())){
                        CacheManager.listDataMap[apiDataObject.getDataType().simpleName] = baseDataList
                        val response = CacheManager.getListDataMapWithValues(context, apiDataObject.getDataType())
                        viewModel.onAppendSuccess()
                        return formatRecievedValues(response, 0)
                    }

                }
            }

            viewModel.onAppendLoading()
            viewModel.state = ApiHelper.STATE_LOADING
            var pagingSource : LoadResult<Int, Any>? = null

            delay(Random.nextLong((APIInterface.loadingDelayFrom * 1000).toLong(), (APIInterface.loadingDelayTo * 1000 + 1).toLong()))

            RetrofitInstance.communicate({apiDataObject.getApiResponse(params.loadSize, offset, getSort(), getFilters())}, {response ->
                pagingSource = onSuccess(response, offset)
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

    fun onSuccess(response : Any, offset: Int) : LoadResult<Int, Any>{
        response as List<BaseData>

        if(response.isNotEmpty()) {

            if ((!CacheManager.listDataMap.containsKey(apiDataObject.getDataType().simpleName) || offset == 0) && areParametersDefault()) {
                CacheManager.listDataMap[apiDataObject.getDataType().simpleName] = ExpiringListData()
                CacheManager.listDataMap[apiDataObject.getDataType().simpleName]!!.saveReceivedTime()
            }

            for(baseData in response){
                if(CacheManager.detailsDataMap.containsKey(Pair(apiDataObject.getDataType().simpleName, baseData.getMainID()))){
                    CacheManager.detailsDataMap[Pair(apiDataObject.getDataType().simpleName, baseData.getMainID())]!!.updateValues(baseData)
                }
                else{
                    CacheManager.detailsDataMap[Pair(apiDataObject.getDataType().simpleName, baseData.getMainID())] = baseData
                }

                if(areParametersDefault()){
                    CacheManager.listDataMap[apiDataObject.getDataType().simpleName]!!.list.add(baseData.getMainID())
                }

            }
        }

        val pagingSource = formatRecievedValues(response, offset)
        viewModel.isRequestModeRefresh = false

        return pagingSource
    }

    fun onError(error: String?, errorBody: ErrorData?) : LoadResult<Int, Any>{
        viewModel.state = ApiHelper.STATE_ERROR
        val pagingSource : LoadResult<Int, Any> = LoadResult.Error(Throwable("Error: $error. Error body: $errorBody"))
        return pagingSource
    }

    fun formatRecievedValues(responseAs : List<BaseData>, offset : Int): LoadResult<Int, Any> {
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

    override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
        return state.anchorPosition
    }
}