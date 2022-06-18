package com.example.littlebeachblog.domain.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.littlebeachblog.app.App
import com.example.littlebeachblog.data.api.HomeAPIs
import com.example.littlebeachblog.data.bean.MoYv
import java.lang.Exception

/**
 * Created by AK on 2022/2/7 16:56.
 * God bless my code!
 */
class MoYvPagingSource(private val homeApi: HomeAPIs) : PagingSource<Int, MoYv>() {

    override fun getRefreshKey(state: PagingState<Int, MoYv>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoYv> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val moYvResponse = homeApi.getMoYvs(App.getApp().mMap["token"], page, pageSize)
            val moYvs = moYvResponse.getData()
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (moYvs.isNotEmpty()) page + 1 else null
            val page1 = LoadResult.Page(moYvs, prevKey, nextKey)
            page1
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}