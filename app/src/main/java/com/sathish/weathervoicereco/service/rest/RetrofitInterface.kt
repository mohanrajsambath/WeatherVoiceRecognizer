package com.sathish.weathervoicereco.service.rest

import com.sathish.weathervoicereco.service.model.WeatheInfoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*
 *  
 * Project Name : WeatherVoiceRecognizer
 * Created by : SATHISH KUMAR R
 * Created on :14-10-2019 23:37
 * File Name : RetrofitInterface
 * ClassName : RetrofitInterface
 * Module Name : app
 */

interface RetrofitInterface {

@GET("data/2.5/weather?")
  fun getWeatherInfo(@Query("q")mcity : String , @Query("units")mUnit : String , @Query("appid")mKey : String):Call<WeatheInfoModel>

}