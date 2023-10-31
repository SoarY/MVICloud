package com.soar.music.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soar.music.R
import com.soar.music.adapter.MePagerAdapter
import com.soar.music.databinding.FragmentMusicBinding
import com.soar.mvi.base.BaseLazyFragment

/**
 * NAME：YONG_
 * Created at: 2023/3/25 11
 * Describe:
 */
class MusicFragment : BaseLazyFragment<FragmentMusicBinding>(){

    companion object{
        fun newInstance():MusicFragment{
            return MusicFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        initView()
        return view
    }

    private fun initView() {
        val pagerAdapter = MePagerAdapter(childFragmentManager)
        pagerAdapter.addFragment(getString(R.string.android),AndroidPlayFragment.newInstance())
        pagerAdapter.addFragment(getString(R.string.navi),NaviFragment.newInstance())

        binding.viewPager.offscreenPageLimit = 2
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    override fun lazyData() {
    }
}