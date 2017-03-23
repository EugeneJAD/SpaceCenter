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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public static List<Asteroid> fetchAsteroidsData (String url, String startDate, String endDate) {

        URL jsonURL = createURL(url);

        String jsonAsteroidResponse = null;

        try {
            jsonAsteroidResponse = makeHTTPConnection(jsonURL);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return extractAsteroidDataFromJSON(jsonAsteroidResponse, startDate, endDate);

    }

    private static List<Asteroid> extractAsteroidDataFromJSON(String jsonAsteroidResponse,String startDate, String endDate) {

        if (TextUtils.isEmpty(jsonAsteroidResponse)) {
            return null;
        }

        List<Asteroid> asteroids = new ArrayList<>();

        try {

            JSONObject root  = new JSONObject(jsonAsteroidResponse);
            JSONObject objects = root.getJSONObject("near_earth_objects");
            JSONArray dataEndArray = objects.getJSONArray(endDate);

            for (int i =0; i<dataEndArray.length(); i++)
            {
                JSONObject jsonObject = dataEndArray.getJSONObject(i);
                JSONObject diameterObject = jsonObject.getJSONObject("estimated_diameter");
                JSONObject diameterMetreObject = diameterObject.getJSONObject("meters");

                JSONArray approach = jsonObject.getJSONArray("close_approach_data");
                JSONObject approachObject = approach.getJSONObject(0);
                JSONObject velocityObject = approachObject.getJSONObject("relative_velocity");
                JSONObject missDistance = approachObject.getJSONObject("miss_distance");

                asteroids.add(new Asteroid(jsonObject.getString("name"),
                        formattedDouble(diameterMetreObject.getDouble("estimated_diameter_max")),
                        approachObject.getString("close_approach_date"),
                        formattedDouble(velocityObject.getDouble("kilometers_per_hour")),
                        missDistance.getString("kilometers"),
                        jsonObject.getBoolean("is_potentially_hazardous_asteroid"),
                        getAsteroidImage()));
            }
            JSONArray dataStartArray = objects.getJSONArray(startDate);

            for (int i =0; i<dataStartArray.length(); i++)
            {
                JSONObject jsonObject = dataStartArray.getJSONObject(i);
                JSONObject diameterObject = jsonObject.getJSONObject("estimated_diameter");
                JSONObject diameterMetreObject = diameterObject.getJSONObject("meters");

                JSONArray approach = jsonObject.getJSONArray("close_approach_data");
                JSONObject approachObject = approach.getJSONObject(0);
                JSONObject velocityObject = approachObject.getJSONObject("relative_velocity");
                JSONObject missDistance = approachObject.getJSONObject("miss_distance");



                asteroids.add(new Asteroid(jsonObject.getString("name"),
                        formattedDouble(diameterMetreObject.getDouble("estimated_diameter_max")),
                        approachObject.getString("close_approach_date"),
                        formattedDouble(velocityObject.getDouble("kilometers_per_hour")),
                        missDistance.getString("kilometers"),
                        jsonObject.getBoolean("is_potentially_hazardous_asteroid"),
                        getAsteroidImage()));
            }


        }
        catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }

        return asteroids;

    }

    private static String formattedDouble(Double d){

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(d);
    }

    private static int getAsteroidImage() {

        Random random = new Random();

        switch (random.nextInt(6)) {
            case 0:
                return R.drawable.ast1;
            case 1:
                return R.drawable.ast2;
            case 2:
                return R.drawable.ast3;
            case 3:
                return R.drawable.ast4;
            case 4:
                return R.drawable.ast5;
            case 5:
                return R.drawable.ast6;
            case 6:
                return R.drawable.ast7;
            default:
                return R.drawable.ast3;

        }
    }

}
