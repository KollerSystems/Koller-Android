package com.norbert.koller.shared.recycleradapters

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.android.material.chip.Chip
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.formatDate
import com.norbert.koller.shared.viewmodels.BaseViewModel
import kotlinx.coroutines.delay
import retrofit2.Response
import kotlin.random.Random


abstract class PagingSource(val context: Context, val viewModel: BaseViewModel) : PagingSource<Int, Any>()  {

    private var lastFirstChar : String? = null
    private var lastListSize : Int = BaseViewModel.pageSize + 1
    lateinit var recyclerAdapter: ApiRecyclerAdapter

    companion object{

    }

    @SuppressLint("WeekBasedYear")
    fun getFilters() : String{

        var finalString = ""

        for ((key, value) in viewModel.filters) {

            for (argument in value) {

                finalString += "${key}:${argument},"
            }
        }

        for ((key, value) in viewModel.dateFilters) {
            finalString += "${key}[gte]:${value.first.formatDate(DateTimeHelper.apiFormat) + "T00:00:00.000Z"},${key}[lte]:${SimpleDateFormat(DateTimeHelper.apiFormat).format(value.second) + "T00:00:00.000Z"},"
        }
        finalString.dropLast(1)

        return finalString
    }

    fun getSort() : String{
        if(recyclerAdapter.chipsSort == null) return ""
        val chip : Chip = recyclerAdapter.chipsSort!!.findViewById(recyclerAdapter.chipsSort!!.checkedChipId)
        return chip.tag.toString()
    }


    abstract suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int) : Response<List<BaseData>>

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {

        Log.d("INFO", "HELLO LOADI")

        val dataTag = recyclerAdapter.getDataTag()

        if(recyclerAdapter.beingEmptied || lastListSize < BaseViewModel.pageSize) {
            Log.d("INFO", "TO EMPTY")
            return LoadResult.Page(emptyList(), null, null)
        }

        var offset = params.key ?: 0

        if(recyclerAdapter.snapshot().size == 0 || recyclerAdapter.setOffsetToZero){
            offset = 0
            recyclerAdapter.setOffsetToZero = false
        }

        if(recyclerAdapter.withLoadingAnim) {
            recyclerAdapter.state = ApiRecyclerAdapter.STATE_LOADING
        }
        else{
            if(areParametersDefault()) {
                CacheManager.savedListsOfValues.remove(dataTag)
            }
            recyclerAdapter.withLoadingAnim = true
        }


        if(offset <= 0 && areParametersDefault() && CacheManager.savedListsOfValues.containsKey(dataTag) && (CacheManager.savedListsOfValues[dataTag]!![0].isValid(context, DateTimeHelper.TIME_NOT_IMPORTANT))) {



            CacheManager.savedListsOfValues[dataTag]!!.forEachIndexed { index, element ->
                if(CacheManager.savedValues.containsKey(Pair(dataTag, element.getMainID()))){
                    CacheManager.savedListsOfValues[dataTag]!![index] = CacheManager.savedValues[Pair(dataTag, element.getMainID())]!!
                }
            }

            val savedValues = CacheManager.savedListsOfValues[dataTag]!!


            val result = formatRecievedValues(savedValues,0, savedValues.size)
            return result
        }



        Handler(Looper.getMainLooper()).post {
            recyclerAdapter.notifyItemInserted(recyclerAdapter.itemCount)
        }

        var pagingSource : LoadResult<Int, Any>? = null


        delay(Random.nextLong((APIInterface.loadingDelayFrom * 1000).toLong(), (APIInterface.loadingDelayTo * 1000 + 1).toLong()))

        RetrofitInstance.communicate({getApiResponse(RetrofitInstance.api, params.loadSize, offset)}, {response ->

            response as List<BaseData>

            if(areParametersDefault() && response.isNotEmpty()) {
                response[0].saveReceivedTime()
                if (!CacheManager.savedListsOfValues.containsKey(dataTag)) {
                    CacheManager.savedListsOfValues[dataTag] = ArrayList()
                }
                CacheManager.savedListsOfValues[dataTag]!!.addAll(response)
            }

            pagingSource = formatRecievedValues(response, offset, params.loadSize)

        }) { error, errorBody ->

            recyclerAdapter.state = ApiRecyclerAdapter.STATE_ERROR
            recyclerAdapter.notifyItemChanged(recyclerAdapter.itemCount - 1)
            Log.e("ERROR", error.toString())
            pagingSource = LoadResult.Error(Throwable("Error: $error. Error body: $errorBody"))

        }

        return pagingSource!!
    }

    fun areParametersDefault() : Boolean{
        return (viewModel.filters.isEmpty() && viewModel.dateFilters.isEmpty() && (recyclerAdapter.chipsSort?.checkedChipId ?: R.id.chip_first) == R.id.chip_first)
    }

    fun formatRecievedValues(responseAs : List<BaseData>, offset : Int, limit: Int): LoadResult<Int, Any> {

        if(offset <= 0){
            recyclerAdapter.recyclerView.scrollToPosition(0)
        }

        recyclerAdapter.notifyItemRemoved(recyclerAdapter.itemCount - 1)
        recyclerAdapter.notifyItemChanged(recyclerAdapter.itemCount - 1 - 1, Object())
        Log.d("INFUPUPUPUPUO", recyclerAdapter.itemCount.toString())

        recyclerAdapter.state = ApiRecyclerAdapter.STATE_NONE

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