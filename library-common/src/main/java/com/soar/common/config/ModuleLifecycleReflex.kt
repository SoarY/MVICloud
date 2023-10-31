package com.soar.common.config

/**
 * NAME：YONG_
 * Created at: 2023/8/24 17
 * Describe:
 */
object ModuleLifecycleReflex {

     private val CommonInit = "com.soar.common.CommonModuleInit"

     //Main模块
     private val MainInit = "com.soar.main.MainModuleInit"
     //登录验证模块
     private val SignInit = "com.soar.sign.SignModuleInit"
     //美食模块
     private val MusicInit = "com.soar.delicacy.MusicModuleInit"

     var initModuleNames = arrayOf(CommonInit,MainInit, SignInit,MusicInit)
}