package com.example.littlebeachblog.data.config

import com.example.littlebeachblog.app.utils.BASE_URL
import com.example.littlebeachblog.app.utils.MUSIC_URL
import com.example.littlebeachblog.app.utils.SOB_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

/**
 * Created by AK on 2022/2/2 20:09.
 * God bless my code!
 */
object ServiceCreator {

    private val retrofitSOB: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SOB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private val retrofitMus: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(MUSIC_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor {
            Timber.d("===> result：${it}")
        }.also {
            it.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun <T> create(clazz: Class<T>) = retrofit.create(clazz)

    fun <T> createSOB(clazz: Class<T>) = retrofitSOB.create(clazz)

    fun <T> createMus(clazz: Class<T>) = retrofitMus.create(clazz)

}