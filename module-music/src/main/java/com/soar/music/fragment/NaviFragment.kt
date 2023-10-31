package com.soar.music.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.soar.common.util.ToastUtils
import com.soar.commonui.view.LoadingView
import com.soar.music.action.AndroidPlayViewAction
import com.soar.music.action.AndroidPlayViewEvent
import com.soar.music.action.NaviViewAction
import com.soar.music.adapter.NaviAdapter
import com.soar.music.adapter.NaviContentAdapter
import com.soar.music.databinding.FragmentAndroidPlayBinding
import com.soar.music.databinding.FragmentNaviBinding
import com.soar.music.databinding.HeaderAndroidPalyBinding
import com.soar.music.view.GlideImageLoader
import com.soar.music.vm.AndroidPlayViewModel
import com.soar.music.vm.NaviViewModel
import com.soar.mvi.base.BaseLazyFragment
import com.soar.mvi.core.FetchStatus
import com.soar.mvi.core.ViewState
import com.soar.network.bean.BaseResultBean
import com.soar.network.bean.response.AndroidPlayBannerBean
import com.soar.network.bean.response.DataBean
import com.soar.network.bean.response.NaviBean
import com.youth.banner.BannerConfig
import java.util.stream.Collectors

/**
 * NAME：YONG_
 * Created at: 2023/3/25 14
 * Describe:
 */
class NaviFragment : BaseLazyFragment<FragmentNaviBinding>(){

    private lateinit var adapter:NaviAdapter
    private lateinit var naviContentAdapter:NaviContentAdapter

    private val mViewModel: NaviViewModel by viewModels()

    companion object{
        fun newInstance():NaviFragment{
            return NaviFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        initView()
        observeViewModel()
        return view
    }

    private fun initView() {
        adapter = NaviAdapter()
        naviContentAdapter = NaviContentAdapter()
        binding.xrvNavi.adapter=adapter
        binding.xrvNaviDetail.adapter=naviContentAdapter

        adapter.setOnSelectListener{
            (binding.xrvNaviDetail.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(it, 0)
        }
        binding.loadingView.setOnRetryListener{lazyData()}
    }

    override fun lazyData() {
        mViewModel.dispatch(NaviViewAction.LoadData)
    }

    private fun observeViewModel() {
        mViewModel.naviViewStates.observe(this, ::naviViewStatesResult)
    }

    private fun naviViewStatesResult(status: ViewState<BaseResultBean<List<NaviBean>>>) {
        when (status.fetchStatus) {
            is FetchStatus.Fetching -> viewState(1, LoadingView.State.ing)
            is FetchStatus.Fetched -> status.data?.data?.let {
                viewState(0,LoadingView.State.done)

                adapter.setDataList(it)
                naviContentAdapter.setDataList(it)
            }
            is FetchStatus.Error -> {
                viewState(1,LoadingView.State.error)
                (status.fetchStatus as FetchStatus.Error).errorCode.let {
                    Log.i(TAG, "handleLoginResult: $status.errorCode")
                }
            }
            is FetchStatus.NotFetched -> {
                viewState(1,LoadingView.State.empty)
            }
        }
    }

    private fun viewState(i: Int, state: LoadingView.State?) {
        binding.viewSwitcher.displayedChild=i
        binding.loadingView.notifyDataChanged(state)
    }
}