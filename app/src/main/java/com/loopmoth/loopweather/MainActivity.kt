package com.loopmoth.loopweather

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var data: JSONObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setContentView(R.layout.activity_main)

        //val str: String = GetForecast().execute("Gliwice").toString()

        //Toast.makeText(this, GetForecast().doInBackground("Gliwice") ,Toast.LENGTH_LONG).show()
        GetForecast(this).execute("Gliwice")
        //tvMiasto.setText(str)
    }

    fun changeCity(city: String){
        this.tvMiasto.setText(city)
    }

}
