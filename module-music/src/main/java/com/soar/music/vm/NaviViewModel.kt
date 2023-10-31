package com.soar.music.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soar.music.action.NaviViewAction
import com.soar.music.repository.NaviRepository
import com.soar.mvi.base.BaseViewModel
import com.soar.mvi.core.FetchStatus
import com.soar.mvi.core.ViewState
import com.soar.network.bean.BaseResultBean
import com.soar.network.bean.response.NaviBean
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * NAME：YONG_
 * Created at: 2023/3/31 11
 * Describe:
 */
class NaviViewModel : BaseViewModel(){

    private val _naviViewStates: MutableLiveData<ViewState<BaseResultBean<List<NaviBean>>>> = MutableLiveData()
    val naviViewStates get() = _naviViewStates

    fun dispatch(viewAction: NaviViewAction) {
        when (viewAction) {
            is NaviViewAction.LoadData -> getNaviJson()
        }
    }

    private fun getNaviJson() {
        _naviViewStates.value = ViewState(FetchStatus.Fetching)
        viewModelScope.launch {
            NaviRepository.getNaviJson().collect {
                _naviViewStates.value = it
            }
        }
    }
}