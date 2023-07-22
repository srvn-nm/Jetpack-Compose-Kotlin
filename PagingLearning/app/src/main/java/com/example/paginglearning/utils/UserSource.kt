package com.example.paginglearning.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paginglearning.model.UserData
import com.example.paginglearning.network.ApiClient
import okio.IOException
import retrofit2.HttpException

class UserSource : PagingSource<Int, UserData>() {
    override fun getRefreshKey(state: PagingState<Int, UserData>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserData> {
        return try {
            val nextPage = params.key ?: 1
            val userList = ApiClient.apiService.getUserList(nextPage)
            LoadResult.Page(
                data = userList.data,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (userList.data.isEmpty()) null else userList.page + 1
            )
        }catch (exception: IOException){
return LoadResult.Error(exception)
        }catch (exception: HttpException){
            return LoadResult.Error(exception)
        }
    }

}