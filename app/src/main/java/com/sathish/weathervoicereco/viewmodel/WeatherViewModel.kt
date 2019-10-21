package com.sathish.weathervoicereco.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sathish.weathervoicereco.BuildConfig
import com.sathish.weathervoicereco.model.WeatheInfoModel
import com.sathish.weathervoicereco.remote.network.RetrofitClient
import com.sathish.weathervoicereco.remote.repository.RetrofitInterface
import com.sathish.weathervoicereco.utils.AppLog
import com.sathish.weathervoicereco.utils.Constants
import com.sathish.weathervoicereco.view.VoiceActivity
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

    var mWeatherStatusViewModel: MutableLiveData<WeatheInfoModel> = MutableLiveData()


    fun test(x: Int): Int {
        return 10 + x
    }



    fun CallWeatherService(mActivity: VoiceActivity, mCityName: String) {
        val apiservice: RetrofitInterface =RetrofitClient.getClient.create(RetrofitInterface::class.java)
        val result = apiservice.getWeatherInfo(mCityName, Constants.UNIT, BuildConfig.API_KEY)

        result.enqueue(object : Callback, retrofit2.Callback<WeatheInfoModel> {

            override fun onFailure(call: Call<WeatheInfoModel>, t: Throwable) {
             getFailedStatus(mActivity, t)
               /* AppLog.e("Error ", t.toString())
                Toast.makeText(mActivity, t.toString(), Toast.LENGTH_SHORT).show()*/
            }

            override fun onResponse(call: Call<WeatheInfoModel>,response: Response<WeatheInfoModel>) {
               getSuccessStatus(response)
               /* if (response.isSuccessful) {
                    mWeatherStatusViewModel.value = response.body()
                }*/
            }

        })

    }


    fun getSuccessStatus(response: Response<WeatheInfoModel>) {
        if (response.isSuccessful) {
            mWeatherStatusViewModel.value = response.body()
        }
    }

    fun getFailedStatus(mActivity: VoiceActivity, t: Throwable) {
        AppLog.e("Error ", t.toString())
        Toast.makeText(mActivity, t.toString(), Toast.LENGTH_SHORT).show()
    }

}