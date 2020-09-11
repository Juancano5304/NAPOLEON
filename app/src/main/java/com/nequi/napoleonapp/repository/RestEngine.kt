package com.nequi.napoleonapp.repository

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestEngine {

    companion object {
        fun getRestEngine(): Retrofit {
            val gson = Gson()
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel((HttpLoggingInterceptor.Level.BODY))
            val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
            val retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory((GsonConverterFactory.create(gson))).client(okHttpClient)
                .build()
            return retrofit
        }
    }
}