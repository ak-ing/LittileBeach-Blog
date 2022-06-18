package com.example.littlebeachblog.app.utils

/**
 * Created by AK on 2022/2/1 13:48.
 * God bless my code!
 */
const val BASE_URL = "http://moyv.akcoming.top:30000"
const val MUSIC_URL = "http://music.akcoming.top"
const val SOB_URL = "https://api.sunofbeaches.com"

/**
 * token认证参数
 */
const val TOKEN = "authorization"

/**
 *  room数据库路径 -> /data/data/${packageName}/files/room_db
 */
const val roomDir = "/room_db"

/**
 * blurView模糊率
 */
const val radius = 20f

/**
 * 分页条数
 */
const val PAGE_SIZE = 6

/**
 * 正则表达式：验证邮箱
 */
const val REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"

/**
 * 正则表达式：验证密码
 */
const val REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$"
