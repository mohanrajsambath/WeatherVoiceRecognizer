package com.sathish.weathervoicereco.viewmodel

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sathish.weathervoicereco.view.utils.permissionhelper.PermissionUtils
import java.util.*

/*
 * Project Name : WeatherVoiceRecognizer
 * Created by : SATHISH KUMAR R
 * Created on :15-10-2019 14:51
 * Updated on : 
 * File Name : SpeechRecognizerViewModel.kt
 * ClassName : SpeechRecognizerViewModel.kt
 * Module Name : app
 * Desc : 
 */

class SpeechRecognizerViewModel(application: Application) : AndroidViewModel(application), RecognitionListener {

    data class ViewState(val spokenText: String,val isListening: Boolean,val error: String?,val rmsDbChanged: Boolean = false)

      var viewState: MutableLiveData<ViewState>? = null
    private var previousRmsdB = 0f

    val mSpeechRecognizer: SpeechRecognizer =
        SpeechRecognizer.createSpeechRecognizer(application.applicationContext).apply {
            setRecognitionListener(this@SpeechRecognizerViewModel)
        }

     var permissionToRecordAudio = checkAudioRecordingPermission(context = application)

    private val recognizerIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, application.packageName)
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
    }


    var isListening = false
        get() = viewState?.value?.isListening ?: false

    fun startListening() {
        mSpeechRecognizer.startListening(recognizerIntent)
        notifyListening(isRecording = true)
    }

    fun stopListening() {
        mSpeechRecognizer.stopListening()
        notifyListening(isRecording = false)
    }

    private fun notifyListening(isRecording: Boolean) {
        viewState?.value = viewState?.value?.copy(isListening = isRecording, rmsDbChanged = false)
    }

    private fun updateResults(speechBundle: Bundle?) {
        val userSaid = speechBundle?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        viewState!!.value =
            viewState!!.value!!.copy(spokenText = userSaid?.get(0) ?: "", rmsDbChanged = false)
    }

    fun checkAudioRecordingPermission(context: Application) =

        ContextCompat.checkSelfPermission(context,
            PermissionUtils.Mainfest_RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED


    override fun onRmsChanged(rmsdB: Float) {
        if (rmsdB > 4 && diffRms(newRms = rmsdB, previousRms = previousRmsdB) > 1) {
            previousRmsdB = rmsdB
            viewState?.value = viewState?.value?.copy(rmsDbChanged = true)
        }
    }

    fun getViewState(): LiveData<ViewState> {
        if (viewState == null) {
            viewState = MutableLiveData()
            viewState?.value = initViewState()
        }

        return viewState as MutableLiveData<ViewState>
    }

    private fun initViewState() = ViewState(spokenText = "", isListening = false, error = null)

    private fun diffRms(newRms: Float, previousRms: Float): Int =
        Math.abs(previousRms - newRms).toInt()

    override fun onBufferReceived(buffer: ByteArray?) {}

    override fun onPartialResults(partialResults: Bundle?) = updateResults(speechBundle = partialResults)

    override fun onEvent(eventType: Int, params: Bundle?) {}

    override fun onBeginningOfSpeech() {}
    override fun onReadyForSpeech(params: Bundle?) {}

    override fun onEndOfSpeech() = notifyListening(isRecording = false)

    override fun onError(errorCode: Int) {
        viewState?.value = viewState?.value?.copy(
            error = when (errorCode) {
                SpeechRecognizer.ERROR_AUDIO -> "error_audio_error"
                SpeechRecognizer.ERROR_CLIENT -> "error_client"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "error_permission"
                SpeechRecognizer.ERROR_NETWORK -> "error_network"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "error_timeout"
                SpeechRecognizer.ERROR_NO_MATCH -> "error_no_match"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "error_busy"
                SpeechRecognizer.ERROR_SERVER -> "error_server"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "error_timeout"
                else -> "error_unknown"
            }, rmsDbChanged = false
        )
    }

    override fun onResults(results: Bundle?)  = updateResults(speechBundle = results)

}