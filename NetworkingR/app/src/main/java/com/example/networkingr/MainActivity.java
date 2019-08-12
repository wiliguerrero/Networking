package com.example.networkingr;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import java.io.IOException;
import  java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.img);

        new DownloadImageTask().execute("http://www.oslafo.com/uploads/galerias/CsuzFzRDEhOnKaBrmbgS2JY8okhEAyxNPyvQR97UX6IPctBPTxWR3iCet57yRN0AFCt3Vy8mxdByD9.jpg");

    }

    private InputStream OpenHttpConnection(String urlString) throws IOException{
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if(!(conn instanceof  HttpURLConnection))
            throw new IOException("Not an HTTP conection");
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK){
                in = httpConn.getInputStream();
            }
        }catch (Exception e){
            Log.d("Networking", e.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }

    private Bitmap DownloadImage(String URL){
        Bitmap bitmap = null;
        InputStream in = null;

        try{
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e){
            Log.d("NetworkingActivity", e.getLocalizedMessage());
        }
        return bitmap;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{
        protected Bitmap doInBackground(String... urls){
            return DownloadImage(urls[0]);


        }
        protected void onPostExecute(Bitmap result){
            img.setImageBitmap(result);
        }
    }




}
