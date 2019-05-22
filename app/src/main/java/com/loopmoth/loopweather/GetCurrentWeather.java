package com.loopmoth.loopweather;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.Normalizer;

class GetCurrentWeather extends AsyncTask<String,Void,String> {

    private Main2Activity context;

    public GetCurrentWeather(Context context){
        this.context = (Main2Activity) context;
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        String result = "";
        HttpURLConnection conn = null;
        try {
            String city = Normalizer.normalize(URLEncoder.encode(params[0], "UTF-8").replaceAll(" ",""), Normalizer.Form.NFKD);
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+ city +"&lang=pl&&units=metric&APPID=1abcff45ddcf2aefcf4f32bdd919d9fb");
            //URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+URLEncoder.encode(params[0], "UTF-8")+"&APPID=1abcff45ddcf2aefcf4f32bdd919d9fb");
            conn = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(conn.getInputStream());

            //Toast.makeText(context.getApplicationContext(), "test", Toast.LENGTH_LONG).show();

            if (in != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                String line = "";

                while ((line = bufferedReader.readLine()) != null)
                    result += line;
            }
            in.close();
            return result;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
            if(conn!=null)
                conn.disconnect();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        //Toast.makeText(context.getApplicationContext(), result, Toast.LENGTH_LONG).show();
        //TextView txtView = (TextView)findViewById(R.id.tvMiasto);
        //txtView.setText("Hello");
        updateTV(result);
    }

    private void updateTV(final String str1){
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(context.getApplicationContext(), "test", Toast.LENGTH_LONG).show();
                try{
                    context.getWeather(str1);
                }
                catch (Exception ex){
                    //Toast.makeText(context.getApplicationContext(), "Nie znaleziono miasta", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}