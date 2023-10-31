package com.soar.music.repository

import com.soar.common.base.BaseApplication
import com.soar.mvi.core.FetchStatus
import com.soar.mvi.core.ViewState
import com.soar.network.bean.BaseResultBean
import com.soar.network.bean.response.AndroidPlayBannerBean
import com.soar.network.bean.response.DataBean
import com.soar.network.bean.response.NaviBean
import com.soar.network.constant.APIMain
import com.soar.network.constant.NetworkConstant
import com.soar.network.retrofit.RetrofitClient
import com.soar.network.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * NAME：YONG_
 * Created at: 2023/9/6 10
 * Describe:
 */
object NaviRepository {

    fun getNaviJson(): Flow<ViewState<BaseResultBean<List<NaviBean>>>> {
        return flow {
            emit(exGetNaviJson())
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun exGetNaviJson(): ViewState<BaseResultBean<List<NaviBean>>> {
        if (!NetworkUtils.isNetworkConnected(BaseApplication.context))
            return ViewState(FetchStatus.Error(NetworkConstant.NETWORK_ERROR))

        val fetchRecipes = RetrofitClient.getApi(APIMain.API_PLAY_ANDROID)!!.getNaviJson()
        if (fetchRecipes.isSuccessful){
            val baseResultBean = fetchRecipes.body() as BaseResultBean<List<NaviBean>>
            if (baseResultBean.errorCode==0)
                return ViewState(FetchStatus.Fetched,baseResultBean)
            else
                return ViewState(FetchStatus.Error(baseResultBean.errorCode))
        } else{
            return ViewState(FetchStatus.Error(fetchRecipes.code()))
        }
    }
}