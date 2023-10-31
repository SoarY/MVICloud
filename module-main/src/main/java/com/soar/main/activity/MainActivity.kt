package com.soar.main.activity

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soar.common.router.RouteConstants
import com.soar.common.util.ToastUtils
import com.soar.main.R
import com.soar.main.adapter.BottomPagerAdapter
import com.soar.main.databinding.ActivityMainBinding
import com.soar.main.databinding.LayoutNavigationHeaderBinding
import com.soar.music.fragment.MusicFragment
import com.soar.mvi.base.BaseActivity

/**
 * NAME：YONG_
 * Created at: 2023/8/24 17
 * Describe:
 */
@Route(path = RouteConstants.Main.MAIN_ACTIVITY)
class MainActivity : BaseActivity<ActivityMainBinding>(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.layToolbarMain.flTitleMenu.setOnClickListener {binding.drawerLayout.openDrawer(GravityCompat.START)}
        binding.layToolbarMain.ivTitleSearch.setOnClickListener { ToastUtils.showToast("Search") }

        val headerView: View = binding.navigationView.getHeaderView(0)
        val headBind: LayoutNavigationHeaderBinding = LayoutNavigationHeaderBinding.bind(headerView)

        headBind.llNavHomepage.setOnClickListener(listener)
        headBind.llNavScanDownload.setOnClickListener(listener)
        headBind.llNavDeedback.setOnClickListener(listener)
        headBind.llNavAbout.setOnClickListener(listener)
        headBind.llNavLogin.setOnClickListener(listener)
        headBind.llNavCollect.setOnClickListener(listener)
        headBind.llNavExit.setOnClickListener(listener)

        val bottomPagerAdapter = BottomPagerAdapter(supportFragmentManager)
        bottomPagerAdapter.addFragment(MusicFragment.newInstance())
        bottomPagerAdapter.addFragment(MusicFragment.newInstance())
        bottomPagerAdapter.addFragment(MusicFragment.newInstance())
        binding.viewPager.setOffscreenPageLimit(3)
        binding.viewPager.setAdapter(bottomPagerAdapter)

        binding.viewPager.addOnPageChangeListener(onPageChangeListener)
        binding.layToolbarMain.navigationTool.setOnNavigationItemSelectedListener (onNavigationItemSelectedListener)
    }

    private val listener = View.OnClickListener { v: View ->
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        when (v.id) {
            R.id.ll_nav_homepage -> ToastUtils.showToast("主页")
            R.id.ll_nav_scan_download -> ToastUtils.showToast("扫码下载")
            R.id.ll_nav_deedback -> ToastUtils.showToast("问题反馈")
            R.id.ll_nav_about -> ToastUtils.showToast("关于云阅")
            R.id.ll_nav_collect -> ToastUtils.showToast("玩安卓收藏")
            R.id.ll_nav_login -> ToastUtils.showToast("玩安卓登录")
            R.id.ll_nav_exit -> ToastUtils.showToast("退出应用")
        }
    }

    private val onPageChangeListener= object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            binding.layToolbarMain.navigationTool.menu.getItem(position).isChecked = true
        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.getItemId()) {
            R.id.item_music -> {
                binding.viewPager.setCurrentItem(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.item_discover -> {
                binding.viewPager.setCurrentItem(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.item_friends -> {
                binding.viewPager.setCurrentItem(2)
                return@OnNavigationItemSelectedListener true
            }
            else -> {
                return@OnNavigationItemSelectedListener false
            }
        }
    }
}