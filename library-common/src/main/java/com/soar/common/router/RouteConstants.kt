package com.soar.common.router

/**
 * NAME：YONG_
 * Created at: 2023/3/23 18
 * Describe:
 */
open class RouteConstants {
    /**
     * Main组件
     */
    open class Main {
        companion object {
            const val MAIN = "/main"
            //Main界面
            const val MAIN_ACTIVITY = "$MAIN/mainactivity"
        }
    }

    /**
     * 商品组件
     */
    open class Delicacy {
        companion object {
            const val DELICACY = "/delicacy"
            //食谱列表
            const val DELICACY_RECIPES = "$DELICACY/recipes"
            //食谱详情
            const val DELICACY_DETAILS = "$DELICACY/details"
        }
    }
}