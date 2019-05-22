package com.loopmoth.loopweather

import android.R.attr
import com.loopmoth.loopweather.*
import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.layout_listitem.*


class RecyclerViewAdapter(private val mContext: Context, temperatures: ArrayList<String>, date: ArrayList<String>, weathers: ArrayList<String>, humidities: ArrayList<String>, cloudiness: ArrayList<String>, winds: ArrayList<String>, preasures: ArrayList<String>, images: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    //vars
    private var mTemperatures = arrayListOf<String>()
    private var mDates = arrayListOf<String>()
    private var mWeathers = arrayListOf<String>()
    private var mHumidities = arrayListOf<String>()
    private var mCloudinesses = arrayListOf<String>()
    private var mWinds = arrayListOf<String>()
    private var mPreasures = arrayListOf<String>()
    private var mImages = arrayListOf<String>()

    init {
        mTemperatures = temperatures
        mDates = date
        mWeathers = weathers
        mHumidities = humidities
        mCloudinesses = cloudiness
        mWinds = winds
        mPreasures = preasures
        mImages = images
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_listitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTemp.text = mTemperatures.get(position)
        holder.mCloudiness.text = mCloudinesses.get(position)
        holder.mWind.text = mWinds.get(position)
        holder.mPreasure.text = mPreasures.get(position)
        holder.mHumidity.text = mHumidities.get(position)
        holder.mWeather.text = mWeathers.get(position)
        holder.mDate.text = mDates.get(position)
        Picasso.get().load(mImages.get(position)).resize(50,50).into(holder.mImage)
    }

    override fun getItemCount(): Int {
        //return mTemperatures.count()
        return mTemperatures.size
        //return mImageUrls.count()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var mTemp: TextView
        internal var mWeather: TextView
        internal var mPreasure: TextView
        internal var mHumidity: TextView
        internal var mWind: TextView
        internal var mCloudiness: TextView
        internal var mDate: TextView
        internal var mImage: ImageView

        init {
            mTemp = itemView.findViewById(R.id.tvTemp)
            mWeather = itemView.findViewById(R.id.tvWeather)
            mPreasure = itemView.findViewById(R.id.tvPressure)
            mHumidity = itemView.findViewById(R.id.tvHumidity)
            mWind = itemView.findViewById(R.id.tvWind)
            mCloudiness = itemView.findViewById(R.id.tvCloudiness)
            mDate = itemView.findViewById(R.id.tvDate)
            mImage = itemView.findViewById(R.id.imgWeatherIcon)
        }
    }

    companion object {

        private val TAG = "RecyclerViewAdapter"
    }
}