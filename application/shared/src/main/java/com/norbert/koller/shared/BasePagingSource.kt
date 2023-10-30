package com.norbert.koller.shared

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.norbert.koller.shared.api.APIInterface
import com.norbert.koller.shared.api.RetrofitHelper
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.recycleradapter.BaseRecycleAdapter
import kotlinx.coroutines.delay
import retrofit2.Response
import kotlin.random.Random


open class BasePagingSource(val context: Context, private val recyclerAdapter: BaseRecycleAdapter, val sort: String, val filter: String? = null) : PagingSource<Int, Any>()  {

    private var lastFirstChar : String? = null
    private var lastListSize : Int = BaseViewModel.pageSize + 1



    open suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int) : Response<List<BaseData>> {
        throw Exception("Nem lett felülírva a getApiResponse függvény 💀")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {


        if(recyclerAdapter.beingEmptied || lastListSize < BaseViewModel.pageSize) return LoadResult.Page(emptyList(), null,null)

        val offset = params.key ?: 0
        recyclerAdapter.state = BaseRecycleAdapter.STATE_LOADING
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

            recyclerAdapter.notifyItemRemoved(recyclerAdapter.itemCount - 1)
            recyclerAdapter.notifyItemChanged(recyclerAdapter.itemCount - 1 - 1, Object())
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
                prevKey = if (offset == 0) null else offset - params.loadSize,
                nextKey = if (items.isEmpty()) null else offset + params.loadSize
            )


        }catch(e: Exception){
            recyclerAdapter.state = BaseRecycleAdapter.STATE_ERROR
            recyclerAdapter.notifyItemChanged(recyclerAdapter.itemCount-1)
            Log.e("ERROR", e.toString())
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
        return state.anchorPosition
    }


}