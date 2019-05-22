package com.loopmoth.loopweather

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main2.*
import android.view.inputmethod.InputMethodManager
import java.security.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import android.widget.Toast
import kotlin.concurrent.schedule
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopmoth.loopweather.R.id.imgWeatherIcon1
import com.loopmoth.loopweather.R.id.inputCityName
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_main2.view.*
import java.lang.Math.round

class Main2Activity : AppCompatActivity() {

    var cityName:String  = "Gliwice"

    private var mTemperatures = arrayListOf<String>()
    private var mDates = arrayListOf<String>()
    private var mWeathers = arrayListOf<String>()
    private var mHumidities = arrayListOf<String>()
    private var mCloudinesses = arrayListOf<String>()
    private var mWinds = arrayListOf<String>()
    private var mPreasures = arrayListOf<String>()
    private var mImages = arrayListOf<String>()

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        linearLayout.setOnClickListener{
            inputCityName.isFocusable=false
            inputCityName.isFocusableInTouchMode=true

            val view = this.currentFocus
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }

        buttonSearch.setOnClickListener {
            cityName = inputCityName.text.toString()

            GetCurrentWeather(this@Main2Activity).execute(cityName)
            GetForecast(this@Main2Activity).execute(cityName)

            val view = this.currentFocus
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }

        updateWeather(cityName)
    }

    override fun onResume() {
        super.onResume()

        val sv = findViewById(R.id.viewScroll) as ScrollView
        Timer("SettingUp", false).schedule(2500) {
            sv.smoothScrollTo(0, 340)
        }
    }

    fun getWeather(json: String){
        val gson = GsonBuilder().create()
        val currWeather = gson.fromJson(json, CurrentWeather::class.java)

        this.tvCurrTemp.setText(round(currWeather.main.temp).toString()+"°C")
        this.tvCurrCity.setText(currWeather.name)
        //this.tvCurrCity.setText(getDateTime(currWeather.dt))
        this.tvWeather.setText(currWeather.weather[0].description.capitalize())
        this.tvCloudiness.setText("Zachmurzenie: "+currWeather.clouds.all.toString() +"%")
        this.tvHumidity.setText("Wilgotność: "+currWeather.main.humidity.toString()+"%")
        this.tvPressure.setText("Ciśnienie: "+round(currWeather.main.pressure).toString()+" hPa")
        this.tvWind.setText("Wiatr: "+round(currWeather.wind.speed).toString()+" m/s")
        this.tvDate1.setText(getDateTime(currWeather.dt))
        Picasso.get().load("http://openweathermap.org/img/w/"+currWeather.weather[0].icon+".png").resize(50,50).into(imgWeatherIcon1)
    }

    fun getForecast(json: String){
        val gson = GsonBuilder().create()
        val forecast = gson.fromJson(json, WeatherForecast::class.java)

        mTemperatures.clear()
        mCloudinesses.clear()
        mDates.clear()
        mHumidities.clear()
        mPreasures.clear()
        mWinds.clear()
        mWeathers.clear()
        mImages.clear()

        forecast.list.forEach{
            mTemperatures.add(round(it.main.temp).toString()+"°C")
            mCloudinesses.add("Zachmurzenie: "+it.clouds.all.toString()+"%")
            mDates.add(getDateTime(it.dt))
            mHumidities.add("Wilgotność: "+it.main.humidity.toString()+"%")
            mPreasures.add("Ciśnienie: "+round(it.main.pressure).toString()+" hPa")
            mWinds.add("Wiatr: "+round(it.wind.speed).toString()+" m/s")
            mWeathers.add(it.weather[0].description.capitalize())
            mImages.add("http://openweathermap.org/img/w/"+it.weather[0].icon+".png")
        }

        val sv = findViewById(R.id.viewScroll) as ScrollView
        sv.smoothScrollTo(0, 340)

    }

    private fun getDateTime(s: Int): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = s.toLong()*1000L
        val date = cal.time
        val format1 = SimpleDateFormat("dd-MM-yyyy HH:mm")
        var inActiveDate: String = ""
        try {
            inActiveDate = format1.format(date)
            //println(inActiveDate)
            return inActiveDate
        } catch (e1: ParseException) {
            // TODO Auto-generated catch block
            e1.printStackTrace()
            return ""
        }
    }

    fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(layoutManager)

        val adapter = RecyclerViewAdapter(this, mTemperatures, mDates, mWeathers, mHumidities, mCloudinesses, mWinds, mPreasures, mImages)
        recyclerView.setAdapter(adapter)
    }

    fun updateWeather(city: String){
        GetCurrentWeather(this@Main2Activity).execute(cityName)
        GetForecast(this@Main2Activity).execute(cityName)
    }
}
