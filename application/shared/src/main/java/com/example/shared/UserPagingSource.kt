package com.example.shared

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.shared.api.APIInterface
import com.example.shared.api.RetrofitHelper
import com.example.shared.data.UserData
import com.example.shared.recycleradapter.UserRecycleAdapter
import kotlinx.coroutines.delay
import retrofit2.Response
import kotlin.random.Random


class UserPagingSource(val recyclerAdapter: UserRecycleAdapter) : PagingSource<Int, Any>()  {

    var lastFirstChar : Char? = null
    var lastListSize : Int = UserViewModel.pageSize + 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {
        if(lastListSize < UserViewModel.pageSize) return LoadResult.Page(emptyList(), null,null)

        val offset = params.key ?: 0
        recyclerAdapter.state = UserRecycleAdapter.STATE_LOADING
        Handler(Looper.getMainLooper()).post {
            recyclerAdapter.notifyItemInserted(recyclerAdapter.itemCount)
        }

        val usersResponse = RetrofitHelper.buildService(APIInterface::class.java)
        try {
            val response: Response<List<UserData>> = usersResponse.getUsers(params.loadSize, offset, APIInterface.getHeaderMap())
            delay(Random.nextLong((APIInterface.loadingDelayFrom * 1000).toLong(), (APIInterface.loadingDelayTo * 1000 + 1).toLong()))

            if(response.isSuccessful) {

                recyclerAdapter.notifyItemRemoved(recyclerAdapter.itemCount - 1)
                recyclerAdapter.notifyItemChanged(recyclerAdapter.itemCount - 1 - 1, Object())
                recyclerAdapter.state = UserRecycleAdapter.STATE_NONE


                val users = response.body() as List<UserData>
                lastListSize = users.size
                val items = mutableListOf<Any>()



                for (user in users) {
                    val firstChar = user.Name!![0]
                    if (firstChar != lastFirstChar) {
                        items.add(firstChar.toString())
                        lastFirstChar = firstChar
                    }
                    items.add(user)
                }

                return LoadResult.Page(
                    data = items,
                    prevKey = if (offset == 0) null else offset - params.loadSize,
                    nextKey = if (items.isEmpty()) null else offset + params.loadSize
                )
            }
            else{
                throw Exception("API error: ${response.code()}")
            }

        }catch(e: Exception){
            recyclerAdapter.state = UserRecycleAdapter.STATE_ERROR
            recyclerAdapter.notifyItemChanged(recyclerAdapter.itemCount-1)
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
        return state.anchorPosition
    }
}