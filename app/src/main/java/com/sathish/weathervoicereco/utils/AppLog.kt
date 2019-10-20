package com.sathish.weathervoicereco.utils

import android.util.Log
import com.sathish.weathervoicereco.BuildConfig

/*
 *  
 * Project Name : WeatherVoiceRecognizer
 * Created by : SATHISH KUMAR R
 * Created on :14-10-2019 23:43
 * Updated on : 
 * File Name : AppLog
 * ClassName : AppLog
 * Module Name : app.
 * 
 */


object AppLog {

    /*
        * Log state will print based on BuildConfig.LOG (This value declared in build.gradle - buildConfigField "boolean", "LOG", "true")
        * true - it prints (App running in Developer Mode (buildConfigField "boolean", "LOG", "true")
        * false - it wont print log (App Runing in Release mode (buildConfigField "boolean", "LOG", "false")
        */

    val EXCEPTION = "VideoCapture_Exception"
    val PREVIEW = "VideoCapture_Preview"
    val RECORDER = "VideoCapture_VideoRecorder"
    val CAMERA = "VideoCapture_CameraWrapper"

    /**
     * Location code location can be used only inside class
     */
    private val stackTraceMsg: String
        get() {
            var fileInfo = ""
            var stackTraceElements: Array<StackTraceElement>? = Thread.currentThread().stackTrace
            if (stackTraceElements != null && stackTraceElements.size > 4) {
                var stackTrace: StackTraceElement? = stackTraceElements[4]
                fileInfo = stackTrace!!.fileName + "(" + stackTrace.lineNumber + ") " + stackTrace.methodName
                stackTrace = null
            }
            stackTraceElements = null
            return fileInfo
        }


    fun i(tag: String?, inputString: String?) {
        var tag = tag
        var inputString = inputString
        if (BuildConfig.LOG)
            Log.i(tag, "$stackTraceMsg: $inputString")
        tag = null
        inputString = null
    }

    fun e(tag: String?, inputString: String?) {
        var tag = tag
        var inputString = inputString
        if (BuildConfig.LOG)
            Log.e(tag, "$stackTraceMsg: $inputString")
        tag = null
        inputString = null
    }

    fun d(tag: String?, inputString: String?) {
        var tag = tag
        var inputString = inputString
        if (BuildConfig.LOG)
            Log.d(tag, "$stackTraceMsg: $inputString")
        tag = null
        inputString = null
    }

    fun v(tag: String?, inputString: String?) {
        var tag = tag
        var inputString = inputString
        if (BuildConfig.LOG)
            Log.v(tag, "$stackTraceMsg: $inputString")
        tag = null
        inputString = null
    }

    fun w(tag: String?, inputString: String?) {
        var tag = tag
        var inputString = inputString
        if (BuildConfig.LOG)
            Log.w(tag, "$stackTraceMsg: $inputString")
        tag = null
        inputString = null
    }

}