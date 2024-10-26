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


open class PagingSource(val context: Context, val viewModel: ListViewModel) : PagingSource<Int, Any>()  {

    protected var lastListSize : Int = ListViewModel.pageSize + 1

    @SuppressLint("WeekBasedYear")
    open fun getFilters() : String{

        if(viewModel.search != null){
            return "${viewModel.search!!.first}:${viewModel.search!!.second},"
        }
        return ""

    }

    open fun getSort() : String?{
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {

        var offset = params.key ?: 0
        if(viewModel.isRequestModeRefresh) {
            offset = 0

            viewModel.state = ApiHelper.STATE_LOADING
            var pagingSource : LoadResult<Int, Any>? = null

            delay(Random.nextLong((APIInterface.loadingDelayFrom * 1000).toLong(), (APIInterface.loadingDelayTo * 1000 + 1).toLong()))

            RetrofitInstance.communicate({viewModel.apiDataObject.getApiResponse(params.loadSize, offset, getSort(), getFilters())}, {response ->
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

                if (CacheManager.listDataMap.containsKey(viewModel.apiDataObject.getDataType().simpleName)) {
                    if((CacheManager.listDataMap[viewModel.apiDataObject.getDataType().simpleName]!!.isValid(context, viewModel.apiDataObject.getTimeLimit()))){
                        viewModel.onAppendSuccess()
                        return formatRecievedValues(CacheManager.getListDataMapWithValues(context, viewModel.apiDataObject.getDataType()), 0)
                    }
                }

                viewModel.state = ApiHelper.STATE_LOADING

                val baseDataList = DataStoreManager.readList(context, viewModel.apiDataObject.getDataType())

                if (baseDataList != null) {

                    if(baseDataList.isValid(context, viewModel.apiDataObject.getTimeLimit())){
                        CacheManager.listDataMap[viewModel.apiDataObject.getDataType().simpleName] = baseDataList
                        val response = CacheManager.getListDataMapWithValues(context, viewModel.apiDataObject.getDataType())
                        viewModel.onAppendSuccess()
                        return formatRecievedValues(response, 0)
                    }

                }
            }

            viewModel.onAppendLoading()
            viewModel.state = ApiHelper.STATE_LOADING
            var pagingSource : LoadResult<Int, Any>? = null

            delay(Random.nextLong((APIInterface.loadingDelayFrom * 1000).toLong(), (APIInterface.loadingDelayTo * 1000 + 1).toLong()))

            RetrofitInstance.communicate({viewModel.apiDataObject.getApiResponse(params.loadSize, offset, getSort(), getFilters())}, {response ->
                pagingSource = onSuccess(response, offset)
                viewModel.onAppendSuccess()
            }) { error, errorBody ->
                pagingSource = onError(error, errorBody)
                viewModel.onAppendError()
            }

            return pagingSource!!
        }
    }

    open fun areParametersDefault() : Boolean {
        return viewModel.search == null
    }

    fun onSuccess(response : Any, offset: Int) : LoadResult<Int, Any>{
        response as List<BaseData>

        if(response.isNotEmpty()) {

            if ((!CacheManager.listDataMap.containsKey(viewModel.apiDataObject.getDataType().simpleName) || offset == 0) && areParametersDefault()) {
                CacheManager.listDataMap[viewModel.apiDataObject.getDataType().simpleName] = ExpiringListData()
                CacheManager.listDataMap[viewModel.apiDataObject.getDataType().simpleName]!!.saveReceivedTime()
            }

            for(baseData in response){
                if(CacheManager.detailsDataMap.containsKey(Pair(viewModel.apiDataObject.getDataType().simpleName, baseData.getMainID()))){
                    CacheManager.detailsDataMap[Pair(viewModel.apiDataObject.getDataType().simpleName, baseData.getMainID())]!!.updateValues(baseData)
                }
                else{
                    CacheManager.detailsDataMap[Pair(viewModel.apiDataObject.getDataType().simpleName, baseData.getMainID())] = baseData
                }

                if(areParametersDefault()){
                    CacheManager.listDataMap[viewModel.apiDataObject.getDataType().simpleName]!!.list.add(baseData.getMainID())
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

    open fun formatRecievedValues(responseAs : List<BaseData>, offset : Int): LoadResult<Int, Any> {
        viewModel.state = ApiHelper.STATE_NONE

        lastListSize = responseAs.size

        return LoadResult.Page(
            data = responseAs,
            prevKey = if (offset == 0) null else offset - responseAs.size,
            nextKey = if (responseAs.isEmpty()) null else offset + responseAs.size
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
        return state.anchorPosition
    }
}