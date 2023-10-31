package com.soar.network.constant


import com.soar.network.bean.BaseResultBean
import com.soar.network.bean.response.AndroidPlayBannerBean
import com.soar.network.bean.response.DataBean
import com.soar.network.bean.response.NaviBean
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * YONG_
 */
interface API {

    /**
     * 玩安卓轮播图
     */
    @GET("banner/json")
    suspend fun getBannerData(): Response<BaseResultBean<List<AndroidPlayBannerBean>>>

    /**
     * 玩安卓，文章列表、知识体系下的文章
     *
     * @param page 页码，从0开始
     * @param cid  体系id
     */
    @GET("article/list/{page}/json")
    suspend fun getHomeList(
        @Path("page") page: Int,
        @Query("cid") cid: Int?
    ): Response<BaseResultBean<DataBean>>

    /**
     * 导航数据
     */
    @GET("navi/json")
    suspend fun getNaviJson(): Response<BaseResultBean<List<NaviBean>>>
}