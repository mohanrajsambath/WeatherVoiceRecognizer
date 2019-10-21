package com.sathish.weathervoicereco.remote.network

import com.sathish.weathervoicereco.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
 *  
 * Project Name : WeatherVoiceRecognizer
 * Created by : SATHISH KUMAR R
 * Created on :14-10-2019 23:37
 * File Name : RetrofitClient
 * ClassName : RetrofitClient
 * Module Name : app.
 * 
 */

class RetrofitClient  {

    companion object {

        var retrofit: Retrofit? = null

        val getClient: Retrofit
            get() {
                if(retrofit ==null){
                    retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(
                        okhttpClient
                    )
                        .addConverterFactory(GsonConverterFactory.create()).build()
                }
                return retrofit!!

            }

        val okhttpClient: OkHttpClient
            get() {
                var interceptor: HttpLoggingInterceptor?= HttpLoggingInterceptor()
                interceptor!!.level = HttpLoggingInterceptor.Level.BODY
                return if (!BuildConfig.RETROFIT_LOG_INTERCEPTOR){
                    OkHttpClient.Builder().build()
                }else{
                    try {
                        OkHttpClient.Builder().addInterceptor(interceptor).build()
                    } finally {
                    }
                }


            }
    }


}