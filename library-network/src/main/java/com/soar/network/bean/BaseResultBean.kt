package com.soar.network.bean


/**
 * YONG_
 * 返回实体基类
 */
class BaseResultBean<T> {
    var errorCode = 0
    var errorMsg: String? = null
    var data: T? = null
}