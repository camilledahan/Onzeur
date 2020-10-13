package com.tp.onzeur.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class WebService extends Thread {

    private final String URL = " http://ws.audioscrobbler.com/2.0/";
    private final String URL_GETARTIST = "?method=artist.getinfo&artist=riles&api_key=";
    private final String KEY = "d3db509c2630417d41c0239f661718aa";
   // private HttpURLConnection connection;
    private InputStream inputStream;
    private String artist;
    private String summary;

public WebService(String artist){
    this.artist=artist;

}

    public void run() {
        java.net.URL url;
        try {

            url = new URL("https://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist="+this.artist+"&api_key=d3db509c2630417d41c0239f661718aa&format=json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setRequestProperty("Accept","*/*");
            InputStream inputStream = connection.getInputStream();
            getSummaryArtist(inputStream);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void getSummaryArtist(InputStream inputStream) {

        try {
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
             String data= br.readLine();
            inputStream.close();
            JSONObject rootObj = new JSONObject(data);

             summary = rootObj.getJSONObject("artist").getJSONObject("bio").getString("summary");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public String getSummary(){
    return  this.summary;
    }
}