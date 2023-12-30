package com.norbert.koller.shared.recycleradapters

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
import com.norbert.koller.shared.api.RetrofitHelper
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.viewmodels.BaseViewModel
import kotlinx.coroutines.delay
import retrofit2.Response
import kotlin.random.Random


abstract class BasePagingSource(val context: Context, val viewModel: BaseViewModel) : PagingSource<Int, Any>()  {

    private var lastFirstChar : String? = null
    private var lastListSize : Int = BaseViewModel.pageSize + 1
    lateinit var recyclerAdapter: BaseRecycleAdapter

    companion object{
        var temporarySavedValues : MutableMap<String, ArrayList<BaseData>> = mutableMapOf()
    }

    fun getFilters() : String{

        var finalString = ""

        for ((key, value) in viewModel.filters) {

            for (argument in value) {

                finalString += "${key}:${argument},"
            }
        }

        for ((key, value) in viewModel.dateFilters) {
            finalString += "${key}[gte]:${SimpleDateFormat(DateTimeHelper.apiFormat).format(value.first) + "T00:00:00.000Z"},${key}[lte]:${SimpleDateFormat(DateTimeHelper.apiFormat).format(value.second) + "T00:00:00.000Z"},"
        }
        finalString.dropLast(1)

        return finalString
    }

    fun getSort() : String{
        if(recyclerAdapter.chipGroupSort == null) return ""
        val chip : Chip = recyclerAdapter.chipGroupSort!!.findViewById(recyclerAdapter.chipGroupSort!!.checkedChipId)
        return chip.tag.toString()
    }


    open suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int) : Response<List<BaseData>> {
        throw Exception("Nem lett felülírva a getApiResponse függvény 💀")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {

        Log.d("INFO", "HELLO LOADI")

        val className = recyclerAdapter.javaClass.simpleName

        if(recyclerAdapter.beingEmptied || lastListSize < BaseViewModel.pageSize) {
            Log.d("INFO", "TO EMPTY")
            return LoadResult.Page(emptyList(), null, null)
        }

        var offset = params.key ?: 0

        if(recyclerAdapter.snapshot().size == 0){
            offset = 0
        }

        if(recyclerAdapter.withLoadingAnim) {
            recyclerAdapter.state = BaseRecycleAdapter.STATE_LOADING
        }
        else{
            if(areParametersDefault()) {
                temporarySavedValues.remove(className)
            }
            recyclerAdapter.withLoadingAnim = true
        }

        Log.d("INFOOOO", recyclerAdapter.withLoadingAnim.toString())
        Log.d("INFOOOO2", recyclerAdapter.state.toString())

        if(offset <= 0 && areParametersDefault() && temporarySavedValues.containsKey(className)) {

            val savedValues = temporarySavedValues[className]
            val result = formatRecievedValues(savedValues!!,0, savedValues.size)
            Log.d("INFO", "BEJÖTT")
            return result
        }



        Handler(Looper.getMainLooper()).post {
            recyclerAdapter.notifyItemInserted(recyclerAdapter.itemCount)
        }

        val apiResponse = RetrofitHelper.buildService(APIInterface::class.java)
        try {
            val response: Response<List<BaseData>> = getApiResponse(apiResponse, params.loadSize, offset)
            var responseAs: List<BaseData> = listOf()
            if(response.isSuccessful) {
                if(!response.body().isNullOrEmpty()) {
                    responseAs = response.body() as List<BaseData>
                }
            }
            else{
                throw Exception("API error: ${response.code()}")
            }
            delay(Random.nextLong((APIInterface.loadingDelayFrom * 1000).toLong(), (APIInterface.loadingDelayTo * 1000 + 1).toLong()))

            if(areParametersDefault()) {
                if (!temporarySavedValues.containsKey(className)) {
                    temporarySavedValues[className] = ArrayList()
                }
                temporarySavedValues[className]!!.addAll(responseAs)
            }
            return formatRecievedValues(responseAs, offset, params.loadSize)



        }catch(e: Exception){
            recyclerAdapter.state = BaseRecycleAdapter.STATE_ERROR
            recyclerAdapter.notifyItemChanged(recyclerAdapter.itemCount-1)
            Log.e("ERROR", e.toString())
            return LoadResult.Error(e)
        }
    }

    fun areParametersDefault() : Boolean{
        return (viewModel.filters.isEmpty() && viewModel.dateFilters.isEmpty() && (recyclerAdapter.chipGroupSort?.checkedChipId ?: R.id.first) == R.id.first)
    }

    fun formatRecievedValues(responseAs : List<BaseData>, offset : Int, limit: Int): LoadResult<Int, Any> {

        if(offset <= 0){
            recyclerAdapter.RecyclerView.scrollToPosition(0)
        }

        recyclerAdapter.notifyItemRemoved(recyclerAdapter.itemCount - 1)
        recyclerAdapter.notifyItemChanged(recyclerAdapter.itemCount - 1 - 1, Object())
        Log.d("INFUPUPUPUPUO", recyclerAdapter.itemCount.toString())

        recyclerAdapter.state = BaseRecycleAdapter.STATE_NONE

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