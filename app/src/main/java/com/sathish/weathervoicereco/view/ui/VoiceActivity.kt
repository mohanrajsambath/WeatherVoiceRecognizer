package com.sathish.weathervoicereco.view.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sathish.weathervoicereco.R
import com.sathish.weathervoicereco.view.utils.permissionhelper.PermissionUtils
import com.sathish.weathervoicereco.viewmodel.SpeechRecognizerViewModel
import kotlinx.android.synthetic.main.activity_main.*

/*
 *
 * Project Name : WeatherVoiceRecognizer
 * Created by : SATHISH KUMAR R
 * Created on :15-10-2019 00:01
 * Updated on :
 * File Name : VoiceActivity.kt
 * ClassName : VoiceActivity
 * Module Name : app
 * Desc :
 */


class VoiceActivity : AppCompatActivity() {

    internal val REQUEST_PERMISSION_KEY = 1
    private lateinit var mSpeechRecognizerViewModel: SpeechRecognizerViewModel
    var getActivityContext: VoiceActivity? = null
    val PERMISSIONS = arrayOf(PermissionUtils.Mainfest_RECORD_AUDIO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getActivityContext = this@VoiceActivity


        initView()
       setUpSpeechViewModel()

    }

    private fun setUpSpeechViewModel() {

        mSpeechRecognizerViewModel = ViewModelProviders.of(this).get(SpeechRecognizerViewModel::class.java)
        mSpeechRecognizerViewModel.getViewState().observe(this, Observer<SpeechRecognizerViewModel.ViewState> { viewState ->
            render(viewState)
        })

    }

    private fun render(uiOutput: SpeechRecognizerViewModel.ViewState?) {
        if (uiOutput == null) return
        txtView_tittle!!.text = uiOutput.spokenText

    }

    private fun initView() {

        btn_Mic.setOnClickListener(micClickListener)

    }

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


}
