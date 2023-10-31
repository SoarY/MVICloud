package com.soar.music.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soar.common.base.BaseApplication
import com.soar.music.action.AndroidPlayViewAction
import com.soar.music.action.AndroidPlayViewEvent
import com.soar.music.adapter.AndroidPlayAdapter
import com.soar.music.adapter.HeaderFooterAdapter
import com.soar.music.repository.MusicRepository
import com.soar.mvi.base.BaseViewModel
import com.soar.mvi.core.FetchStatus
import com.soar.mvi.core.SingleLiveEvents
import com.soar.mvi.core.ViewState
import com.soar.network.bean.BaseResultBean
import com.soar.network.bean.response.AndroidPlayBannerBean
import com.soar.network.bean.response.ArticlesBean
import com.soar.network.bean.response.DataBean
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * NAME：YONG_
 * Created at: 2023/8/24 17
 * Describe:
 */
class AndroidPlayViewModel : BaseViewModel(){

    private var page: Int = 0

    private var cid: Int? = null

    private var totalCount = 0

    private var datas: ArrayList<ArticlesBean> = ArrayList()

    var adapter: AndroidPlayAdapter = AndroidPlayAdapter()
    var headerAdapter: HeaderFooterAdapter = HeaderFooterAdapter(adapter)

    private val _bannerViewStates: MutableLiveData<ViewState<BaseResultBean<List<AndroidPlayBannerBean>>>> = MutableLiveData()
    val bannerViewStates get() = _bannerViewStates
    private val _homeListViewStates: MutableLiveData<ViewState<BaseResultBean<DataBean>>> = MutableLiveData()
    val homeListViewStates get() = _homeListViewStates

    private val _viewEvents: SingleLiveEvents<AndroidPlayViewEvent> = SingleLiveEvents() //一次性的事件，与页面状态分开管理
    val viewEvents get()= _viewEvents

    fun setCID(cid: Int?) {
        this.cid = cid
    }

    fun dispatch(viewAction: AndroidPlayViewAction) {
        when (viewAction) {
            is AndroidPlayViewAction.LoadData -> loadData()
            is AndroidPlayViewAction.OnRefresh -> onRefresh()
            is AndroidPlayViewAction.OnLoadMore -> onLoadMore()
        }
    }

    private fun loadData() {
        _homeListViewStates.value = ViewState(FetchStatus.Fetching)
        getBannerData()
        getHomeList(true)
    }

    private fun getBannerData() {
        viewModelScope.launch {
            MusicRepository.getBannerData().collect {
                bannerViewStates.value = it
            }
        }
    }

    private fun getHomeList(isRefreshOrLoad:Boolean) {
        viewModelScope.launch {
            MusicRepository.getHomeList(page, cid).collect { it ->
                if (it.fetchStatus is FetchStatus.Fetched){
                    if (isRefreshOrLoad) {
                        datas.clear()
                        adapter.setDataList(datas)
                    }

                    it.data?.data?.let {
                        totalCount=it.total
                        it.datas?.let {
                            datas.addAll(it)
                        }
                        adapter.setDataList(datas)
                    }
                    _viewEvents.value= listOf(AndroidPlayViewEvent.StopRefresh)
                }else{
                    _homeListViewStates.value = it
                }
            }
        }
    }

    private fun onRefresh() {
        page = 0
        getHomeList(true)
        getBannerData()
    }

    private fun onLoadMore() {
        if (datas.size>= totalCount){
            _viewEvents.value=listOf(AndroidPlayViewEvent.ShowToast(BaseApplication
                .context.getString(com.soar.commonui.R.string.to_loaded)))
            _viewEvents.value= listOf(AndroidPlayViewEvent.StopRefresh)
        }
        page++
        getHomeList(false)
    }
}