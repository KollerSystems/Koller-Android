package com.example.shared

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.shared.api.APIInterface
import com.example.shared.api.RetrofitHelper
import com.example.shared.data.UserData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class UserPagingSource() : PagingSource<Int, Any>()  {

    var lastFirstChar : Char? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {
        val offset = params.key ?: 0
        val usersResponse = RetrofitHelper.buildService(APIInterface::class.java)
        val response: Response<List<UserData>> = usersResponse.getUsers(params.loadSize, offset, APIInterface.getHeaderMap())
        val users = response.body() as List<UserData>
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

    override fun getRefreshKey(state: PagingState<Int, Any>): Int? {
        return state.anchorPosition
    }
}