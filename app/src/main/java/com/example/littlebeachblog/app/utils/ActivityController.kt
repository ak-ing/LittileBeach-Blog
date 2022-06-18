package com.example.littlebeachblog.app.utils

import android.app.Activity

/**
 * Created by AK on 2022/2/27 21:00.
 * God bless my code!
 */
object ActivityController {

    private val activities = ArrayList<Activity>()

    /**
     * 添加activity到集合控制
     */
    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    /**
     * 从集合中删除activity
     */
    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    /**
     * finish指定的Activity
     */
    fun <T : Activity> finishOne(clazz: Class<T>) {
        for (activity in activities) {
            if (activity::class.java.name == clazz.name) {
                if (!activity.isFinishing) {
                    activity.finish()
                }
            }
        }
    }

    /**
     * 关闭全部activity
     */
    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }
}