Project Name: WeatherApp
Date:18/Oct/2019

Design pattern used : MVVM
Networking Library used : OkHttp, Retrofit
Xml Design: ConstraintLayout
Weather Api used : OpenWeather
Programming Language : Kotlin
Speech Listenter : native Speeche Listenter
Unit Test: Junit
User input to App : Berlin, Frankfurt,Munich,Hamburg
App working flow : Tap on mic to speak "Berlin or Frankfurt or Munich or Hamburg". It automatically show the weather data of this API. 

Project Status: 
1)  VoiceActivity - This UI activity responsible for getting voice input and displaying Weather data.
2)  SpeechRecognizerViewModel- This View modelcontain getting voice input from  voice activity and it translate the Speech to Text(STT).
3)  WeatherViewModel - This View model contain api Response connectivity. It handle both case of Response success and Response failed.
4)  RetrofitCLient - This class Handling the Retrofit and OkHttpClient Connectivity
5)  RetrofitInterface - This Interface class contain Weather api url 
6)  WeatherIndoModel - This model contain model data of weather data.

