package com.sathish.weathervoicereco.view

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sathish.weathervoicereco.R
import com.sathish.weathervoicereco.model.Weather
import com.sathish.weathervoicereco.utils.permissionhelper.PermissionUtils
import com.sathish.weathervoicereco.viewmodel.SpeechRecognizerViewModel
import com.sathish.weathervoicereco.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_weatherinfo.*
import java.util.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList



/*
 *
 * Project Name : WeatherVoiceRecognizer
 * Created by : SATHISH KUMAR R
 * Created on :15-10-2019 00:01
 * Updated on :
 * File Name : VoiceActivity.kt
 * ClassName : VoiceActivity
 * Module Name : app
 * Desc : Voice Activity Listen to user input  and show  weather report
 */


class VoiceActivity : AppCompatActivity() {

    internal val REQUEST_PERMISSION_KEY = 1
    private lateinit var mSpeechRecognizerViewModel: SpeechRecognizerViewModel
    private lateinit var mWeatherViewModel: WeatherViewModel
    var getActivityContext: VoiceActivity? = null
    val PERMISSIONS = arrayOf(PermissionUtils.Mainfest_RECORD_AUDIO)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getActivityContext = this@VoiceActivity
        initView()
        setUpSpeechViewModel()
        setUpWeatherViewModel()


    }

    // setUpWeatherViewModel() initialize  view model of weather
    private fun setUpWeatherViewModel() {
        mWeatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        mWeatherViewModel.mWeatherStatusViewModel.observe(this, Observer {

            val response = mWeatherViewModel.mWeatherStatusViewModel.value
            if( response != null) {

                val mCity = response.name + "," + " " + response.sys.country
                val mCloudStatus = response.weather as ArrayList<Weather>
                val mTemp = response.main.temp
                val mMintemp = "Min Temp: " + response.main.tempMin
                val mMaxtemp = "Max Temp: " + response.main.tempMax
                val mSunriseTime = response.sys.sunrise.toLong()
                val mSunsetTime = response.sys.sunset.toLong()
                val mWindTemp = response.wind.speed.toString()
                val mPressureTemp = response.main.pressure.toString()
                val mHumidity = response.main.humidity.toString()

                txtView_City.text = mCity
                txtView_Date.text = getCurrentDate
                if (mCloudStatus.size > 0) {

                    txtView_ClearStatus.setText(mCloudStatus[0].description.capitalize())

                }
                txtView_temp.text = mTemp.toString() + "°C"
                txtView_temp_min.text = mMintemp + "°C"
                txtView_temp_max.text = mMaxtemp + "°C"
                txtView_sunrisetime.text =
                    SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(mSunriseTime * 1000))
                txtView_sunsetime.text =
                    SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(mSunsetTime * 1000))
                txtView_windTemp.text = mWindTemp
                txtView_pressure_temp.text = mPressureTemp
                txtView_humidity_temp.text = mHumidity

                layoutCons_weather.visibility = View.VISIBLE
                TxtView_speak.visibility = View.GONE

                }

        })
    }

//Current Date and Time
    val getCurrentDate: String
        get() {
            val currentTime = Calendar.getInstance().getTime()
            val sdf = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault())
            val currentDateandTime = sdf.format(Date())
            return currentDateandTime
        }

    // setUpSpeechViewModel() initialize  view model of Voice speecher
    private fun setUpSpeechViewModel() {

        mSpeechRecognizerViewModel =
            ViewModelProviders.of(this).get(SpeechRecognizerViewModel::class.java)
        mSpeechRecognizerViewModel.getViewState()
            .observe(this, Observer<SpeechRecognizerViewModel.ViewState> { viewState ->
                render(viewState)
                if (txtView_tittle.text != null) {
                        mWeatherViewModel.CallWeatherService(getActivityContext!!,txtView_tittle.text.toString())

                }

            })

    }

    private fun render(uiOutput: SpeechRecognizerViewModel.ViewState?) {
        if (uiOutput == null) return
        txtView_tittle!!.text = uiOutput.spokenText

    }

    // view casting
    private fun initView() {

        btn_Mic.setOnClickListener(micClickListener)

    }

    // onClickListenter
    private val micClickListener = View.OnClickListener {
        if (!mSpeechRecognizerViewModel.permissionToRecordAudio) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSION_KEY)
            return@OnClickListener
        }

        if (mSpeechRecognizerViewModel.isListening) {
            mSpeechRecognizerViewModel.stopListening()
        } else {
            mSpeechRecognizerViewModel.startListening()
        }
    }

    // Request Permission
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_KEY) {
            mSpeechRecognizerViewModel.permissionToRecordAudio =
                grantResults[0] == PackageManager.PERMISSION_GRANTED

        }

        if (mSpeechRecognizerViewModel.permissionToRecordAudio) {
            btn_Mic!!.performClick()
        }
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
