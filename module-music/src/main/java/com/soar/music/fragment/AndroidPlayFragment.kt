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
import com.soar.music.databinding.FragmentAndroidPlayBinding
import com.soar.music.databinding.HeaderAndroidPalyBinding
import com.soar.music.view.GlideImageLoader
import com.soar.music.vm.AndroidPlayViewModel
import com.soar.mvi.base.BaseLazyFragment
import com.soar.mvi.core.FetchStatus
import com.soar.mvi.core.ViewState
import com.soar.network.bean.BaseResultBean
import com.soar.network.bean.response.AndroidPlayBannerBean
import com.soar.network.bean.response.DataBean
import com.youth.banner.BannerConfig
import java.util.stream.Collectors

/**
 * NAME：YONG_
 * Created at: 2023/3/25 14
 * Describe:
 */
class AndroidPlayFragment : BaseLazyFragment<FragmentAndroidPlayBinding>(){

    private lateinit var mHeaderBinding:HeaderAndroidPalyBinding

    private val mViewModel: AndroidPlayViewModel by viewModels()

    companion object{
        val DELAY_TIME: Int=3000

        fun newInstance():AndroidPlayFragment{
            return AndroidPlayFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        initView()
        observeViewModel()
        return view
    }

    private fun initView() {
        binding.materialHeaderView.setColorSchemeResources(com.soar.commonres.R.color.colorAccent)

        binding.recyclerView.adapter=mViewModel.headerAdapter
        binding.recyclerView.layoutManager=LinearLayoutManager(context)

        mHeaderBinding = HeaderAndroidPalyBinding.inflate(layoutInflater)

        mHeaderBinding.banner.setImageLoader(GlideImageLoader())
            .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
            .setDelayTime(DELAY_TIME)

        mHeaderBinding.banner.setOnBannerListener { ToastUtils.showToast(it.toString() + "") }
        mHeaderBinding.ivBannerOne.setOnClickListener {ToastUtils.showToast("BannerOne") }
        mHeaderBinding.ivBannerTwo.setOnClickListener {ToastUtils.showToast("BannerTwo") }

        mViewModel.headerAdapter.addHeaderView(mHeaderBinding.root)
        mViewModel.adapter.setItemClickListener { position -> ToastUtils.showToast(position.toString() + "") }

        binding.refreshLayout.setOnRefreshListener{
            mViewModel.dispatch(AndroidPlayViewAction.OnRefresh)
        }
        binding.refreshLayout.setOnLoadMoreListener{
            mViewModel.dispatch(AndroidPlayViewAction.OnLoadMore)
        }
    }

    override fun lazyData() {
        mViewModel.dispatch(AndroidPlayViewAction.LoadData)
    }

    private fun observeViewModel() {
        mViewModel.bannerViewStates.observe(this, ::bannerViewStatesResult)
        mViewModel.homeListViewStates.observe(this, ::homeListViewStatesResult)

        mViewModel.viewEvents.observe(this){ it ->
            it.forEach {
                renderViewEvent(it)
            }
        }
    }

    private fun bannerViewStatesResult(status: ViewState<BaseResultBean<List<AndroidPlayBannerBean>>>) {
        when (status.fetchStatus) {
            is FetchStatus.Fetching -> viewState(1, LoadingView.State.ing)
            is FetchStatus.Fetched -> status.data?.data?.let {
                viewState(0,LoadingView.State.done)

                val titles = it.stream().map(AndroidPlayBannerBean::title).collect(Collectors.toList()) as List<String>
                val urls = it.stream().map(AndroidPlayBannerBean::imagePath).collect(Collectors.toList()) as List<String>
                mHeaderBinding.banner.setBannerTitles(titles)
                mHeaderBinding.banner.setImages(urls).start()


                Glide.with(mHeaderBinding.ivBannerOne.context)
                    .load(urls[0])
                    .into(mHeaderBinding.ivBannerOne)
                Glide.with(mHeaderBinding.ivBannerOne.context)
                    .load(urls[1])
                    .into(mHeaderBinding.ivBannerTwo)
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

    private fun homeListViewStatesResult(status: ViewState<BaseResultBean<DataBean>>) {
        when (status.fetchStatus) {
            is FetchStatus.Fetching -> viewState(1, LoadingView.State.ing)
            is FetchStatus.Fetched -> {}
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

    private fun renderViewEvent(viewEvent: AndroidPlayViewEvent) {
        when (viewEvent) {
            is AndroidPlayViewEvent.StopRefresh -> {
                binding.refreshLayout.finishRefresh()
                binding.refreshLayout.finishLoadMore()
            }
            is AndroidPlayViewEvent.ShowToast -> {
                ToastUtils.showToast(viewEvent.message)
            }
        }
    }
}