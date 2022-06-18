package com.example.littlebeachblog.data.config

/**
 * Created by AK on 2022/2/1 14:19.
 * God bless my code!
 */
interface IApiResponse<T> {
    fun getOrNull(): ApiResponse<T>?

    fun isNullOrEmpty(): Boolean

    fun getCode(): Int

    fun getMessage(): String

    fun isSuccess(): Boolean

    fun getToken(): String

    fun getData(): T

}