package com.soar.music.action

/**
 * NAME：YONG_
 * Created at: 2023/10/26 11
 * Describe:
 */
sealed class AndroidPlayViewAction {
    object LoadData: AndroidPlayViewAction()
    object OnRefresh: AndroidPlayViewAction()
    object OnLoadMore: AndroidPlayViewAction()
}

sealed class AndroidPlayViewEvent {
    object StopRefresh : AndroidPlayViewEvent()
    data class ShowToast(val message: String) : AndroidPlayViewEvent()
}
