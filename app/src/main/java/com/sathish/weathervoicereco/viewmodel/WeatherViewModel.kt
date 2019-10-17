package com.sathish.weathervoicereco.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sathish.weathervoicereco.BuildConfig
import com.sathish.weathervoicereco.service.model.WeatheInfoModel
import com.sathish.weathervoicereco.service.model.Weather
import com.sathish.weathervoicereco.service.rest.RetrofitClient
import com.sathish.weathervoicereco.service.rest.RetrofitInterface
import com.sathish.weathervoicereco.view.ui.VoiceActivity
import com.sathish.weathervoicereco.view.utils.AppLog
import com.sathish.weathervoicereco.view.utils.Constants
import retrofit2.*
import javax.security.auth.callback.Callback

/*
 * Project Name : WeatherVoiceRecognizer
 * Created by : SATHISH KUMAR R
 * Created on :16-10-2019 17:07
 * Updated on : 
 * File Name : WeatherViewModel.kt
 * ClassName : WeatherViewModel
 * Module Name : app
 * Desc : 
 */

class WeatherViewModel : ViewModel() {

     var mWeatherStatusViewModel : MutableLiveData<WeatheInfoModel> = MutableLiveData()


    fun CallWeatherService(mActivity : VoiceActivity){

        val apiservice : RetrofitInterface = RetrofitClient.getClient.create(RetrofitInterface::class.java)
        val result = apiservice.getWeatherInfo(Constants.CITY,Constants.UNIT,BuildConfig.API_KEY)

        result.enqueue(object : Callback,retrofit2.Callback<WeatheInfoModel>{
            override fun onFailure(call: Call<WeatheInfoModel>, t: Throwable) {
                AppLog.e("Error ", t.toString())
                Toast.makeText(mActivity,t.toString(),Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<WeatheInfoModel>,response: Response<WeatheInfoModel>) {

                if (response.isSuccessful){
                    mWeatherStatusViewModel.value = response.body()

                }
            }

        })

    }

}