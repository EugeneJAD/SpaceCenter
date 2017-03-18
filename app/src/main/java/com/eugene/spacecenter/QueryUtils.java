package com.eugene.spacecenter;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 24.10.2016.
 */
public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }


    public static ApodBox fetchAPODData(String requestUrl) {

        URL url = createURL(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHTTPConnection(url);
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return extractAPODDataFromJSON(jsonResponse);
    }

    private static ApodBox extractAPODDataFromJSON(String jsonResponse) {

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        Log.v(LOG_TAG, "JSON results "+jsonResponse);

        ApodBox apodBox=null;

        try {
            JSONObject root = new JSONObject(jsonResponse);
            apodBox = new ApodBox(root.getString("date"),root.getString("explanation"),
                    root.getString("title"),root.getString("url"),root.getString("hdurl"));

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the APOD JSON results", e);
        }

        return apodBox;
    }

    private static String makeHTTPConnection(URL url) throws IOException {

        String jsonResponse="";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(10000);
            urlConnection.connect();

            //if(urlConnection.getResponseCode()==200)

            inputStream = urlConnection.getInputStream();
            jsonResponse=fetchJSONdata(inputStream);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error response code ",e);
        }
        finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null)
                inputStream.close();
        }
        return jsonResponse;

    }

    private static String fetchJSONdata(InputStream inputStream) throws IOException{

        StringBuilder response = new StringBuilder();
        InputStreamReader inputReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReder = new BufferedReader(inputReader);
        String line = bufferedReder.readLine();

        while (line!=null)
        {
            response.append(line);
            line=bufferedReder.readLine();
        }

        return response.toString();

    }

    private static URL createURL(String requestUrl) {

        URL url=null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        return url;

    }


    public static List<SoundBox> fetchSoundsData(String url) {

        URL jsonUrl = createURL(url);

        String jsonSoundResponse = null;

        try {
            jsonSoundResponse = makeHTTPConnection(jsonUrl);
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<SoundBox> soundBoxes = extractSoundsDataFromJSON(jsonSoundResponse);

        return soundBoxes;


    }

    private static List<SoundBox> extractSoundsDataFromJSON(String jsonSoundResponse) {

        if (TextUtils.isEmpty(jsonSoundResponse)) {
            return null;
        }

        List<SoundBox> soundBoxes = new ArrayList<>();

        try {

            JSONObject root  = new JSONObject(jsonSoundResponse);
            JSONArray results = root.getJSONArray("results");

            for (int i =0; i<results.length(); i++)
            {
                JSONObject jsonObject = results.getJSONObject(i);
                soundBoxes.add(new SoundBox(jsonObject.getString("title"),jsonObject.getString("stream_url"),
                        jsonObject.getLong("duration"),jsonObject.getString("download_url")));

            }

        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }

        return soundBoxes;
    }


}
