package com.sathish.weathervoicereco

import com.sathish.weathervoicereco.model.WeatheInfoModel
import com.sathish.weathervoicereco.remote.repository.RetrofitInterface
import com.sathish.weathervoicereco.viewmodel.WeatherViewModel
import org.junit.Test
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.security.auth.callback.Callback


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
class WeatherApiPresenterTest {
    var mockServer: MockWebServer = MockWebServer()
    lateinit var apiService: RetrofitInterface
    var mWeather: WeatherViewModel = WeatherViewModel()

    val outputResult: String = "{\n" +
            "    \"coord\": {\n" +
            "        \"lon\": 13.39,\n" +
            "        \"lat\": 52.52\n" +
            "    },\n" +
            "    \"weather\": [\n" +
            "        {\n" +
            "            \"id\": 800,\n" +
            "            \"main\": \"Clear\",\n" +
            "            \"description\": \"clear sky\",\n" +
            "            \"icon\": \"01n\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"base\": \"stations\",\n" +
            "    \"main\": {\n" +
            "        \"temp\": 13.76,\n" +
            "        \"pressure\": 1012,\n" +
            "        \"humidity\": 87,\n" +
            "        \"temp_min\": 11.11,\n" +
            "        \"temp_max\": 16.11\n" +
            "    },\n" +
            "    \"visibility\": 10000,\n" +
            "    \"wind\": {\n" +
            "        \"speed\": 2.6,\n" +
            "        \"deg\": 70\n" +
            "    },\n" +
            "    \"clouds\": {\n" +
            "        \"all\": 0\n" +
            "    },\n" +
            "    \"dt\": 1571606628,\n" +
            "    \"sys\": {\n" +
            "        \"type\": 1,\n" +
            "        \"id\": 1275,\n" +
            "        \"message\": 0.009,\n" +
            "        \"country\": \"DE\",\n" +
            "        \"sunrise\": 1571549991,\n" +
            "        \"sunset\": 1571587346\n" +
            "    },\n" +
            "    \"timezone\": 7200,\n" +
            "    \"id\": 2950159,\n" +
            "    \"name\": \"Berlin\",\n" +
            "    \"cod\": 200\n" +
            "}"

    @Before
    open fun setUp() {
        this.configureMockServer()
    }

    @After
    fun tearDown() {
        this.stopMockServer()
    }


    open fun configureMockServer() {
        mockServer.start()
        apiService = Retrofit.Builder()
            .baseUrl(mockServer.url("/")).addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient()).build().create(RetrofitInterface::class.java)
    }


    open fun stopMockServer() {
        mockServer.shutdown()
    }

    @Test
    open fun testApi() {

        val response = MockResponse().setResponseCode(200).setBody(outputResult)
        mockServer.enqueue(response)
        val Result = apiService.getWeatherInfo("Munich", "metric", BuildConfig.API_KEY)
        Result.enqueue(object : Callback, retrofit2.Callback<WeatheInfoModel> {
            override fun onFailure(call: Call<WeatheInfoModel>, t: Throwable) {
                Assert.assertEquals("False", t.equals("False"))
            }

            override fun onResponse(
                call: Call<WeatheInfoModel>,
                response: Response<WeatheInfoModel>
            ) {
                Assert.assertEquals(200, response.isSuccessful)
            }

        })

    }


}