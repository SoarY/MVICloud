package com.soar.music

import android.app.Application
import com.soar.common.imp.IModuleInit

/**
 * NAME：YONG_
 * Created at: 2023/8/24 17
 * Describe:
 */
class MusicModuleInit : IModuleInit {
    override fun onInitAhead(application: Application?): Boolean {
        return false
    }

    override fun onInitLow(application: Application?): Boolean {
        return false
    }
}