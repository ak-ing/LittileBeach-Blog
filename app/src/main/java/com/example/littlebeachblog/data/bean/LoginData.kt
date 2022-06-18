package com.example.littlebeachblog.data.bean

/**
 * Created by AK on 2022/2/2 20:56.
 * God bless my code!
 */
class LoginData(
    private val userName: String,
    private val passWord: String,
    private val token: String? = null
) {
    fun getUserName() = userName
    fun getPassWord() = passWord
    fun getToken() = token
}