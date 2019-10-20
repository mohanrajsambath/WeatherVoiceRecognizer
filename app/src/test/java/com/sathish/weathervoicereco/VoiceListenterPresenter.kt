package com.sathish.weathervoicereco

import com.sathish.weathervoicereco.remote.repository.RetrofitInterface
import com.sathish.weathervoicereco.view.VoiceActivity
import com.sathish.weathervoicereco.viewmodel.WeatherViewModel
import org.junit.Assert
import org.junit.Test
import retrofit2.Response
import org.mockito.Mockito
import retrofit2.Call
import android.system.Os.shutdown
import junit.framework.Assert.assertTrue
import retrofit2.Retrofit




/*
 * Project Name : WeatherVoiceRecognizer
 * Created by : SATHISH KUMAR R
 * Created on :18-10-2019 00:18
 * Updated on : 
 * File Name : VoiceListenterPresenter.kt
 * ClassName : VoiceListenterPresenter
 * Module Name : app
 * Desc : 
 */
class VoiceListenterPresenterTest {
    private val apiManager: RetrofitInterface? = null

    @Test fun correctVOiceSpeecherListerner(){
       val result = WeatherViewModel().CallWeatherService(mActivity = VoiceActivity(),mCityName = String())

    }
    @Test fun SuccessStatus(){

       /* val result = WeatherViewModel().getSuccessStatus(Response.success())
        Assert.assertEquals("Success",result)*/

    }

    @Test fun FailedStatus(){
        val result = WeatherViewModel().getFailedStatus(mActivity = VoiceActivity(),t = Throwable())
        Assert.assertEquals(Unit,result)

    }



}