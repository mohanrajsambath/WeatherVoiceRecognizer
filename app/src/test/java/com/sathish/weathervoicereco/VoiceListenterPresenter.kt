package com.sathish.weathervoicereco

import android.view.View
import com.sathish.weathervoicereco.viewmodel.WeatherViewModel
import org.junit.Assert
import org.junit.Test

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

    @Test fun correctVOiceSpeecherListerner(){
           val result =  WeatherViewModel().test(10)

        assert(result==20)
        Assert.assertNotEquals(result,10)
    }

}