package com.only.restapi.RestApi

import android.content.ContentValues.TAG
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import android.util.Log

import java.io.IOException

import okhttp3.Interceptor.*

import androidx.annotation.NonNull
import com.idi.restful.UnauthorisedInterceptor
import com.only.restapi.Utility.Prefesmanager

import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit


class Restclient {

    companion object {

        private const val TIME:Int = 60 * 15


        //const val BASE_URL = "http://192.168.1.105:8000/"

        //const val BASE_URL = "http://192.168.0.105:8000/"

        const val BASE_URL = "https://mybackofficework.com/survey/" //live url

        private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        var httpClient: OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(TIME.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIME.toLong(), TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(UnauthorisedInterceptor())
            .addNetworkInterceptor(Interceptor { chain ->
                val original: Request = chain.request()
                val requestBuilder: Request.Builder = original.newBuilder()
                if (Prefesmanager.containKey(Prefesmanager.Token)!!) {
                    requestBuilder.addHeader("Authorization","Bearer "+ Prefesmanager.readStringPrefVal(Prefesmanager.Token).toString())
                }
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            })
            .build()

        private var apiInterface: Apiinterface? = null
        private fun retrofit() {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            apiInterface=retrofit.create(Apiinterface::class.java)
        }

        private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(httpClient)
            .build()

        fun getApiInterface():Apiinterface?{
            return retrofit.create(Apiinterface::class.java)
        }
    }
}