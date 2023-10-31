package com.soar.music.activity

import android.os.Bundle
import com.soar.music.R
import com.soar.music.databinding.ActivityMusicBinding
import com.soar.music.fragment.MusicFragment
import com.soar.mvi.base.BaseActivity


/**
 * NAME：YONG_
 * Created at: 2023/8/24 17
 * Describe:
 */
class MusicActivity : BaseActivity<ActivityMusicBinding>(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        supportFragmentManager //
            .beginTransaction()
            .add(
                R.id.fl_music,
                MusicFragment()
            ) // 此处的R.id.fragment_container是要盛放fragment的父容器
            .commit()
    }

}