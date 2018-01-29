package com.eugene.spacecenter.utils;

import android.content.Context;

import com.eugene.spacecenter.R;
import com.eugene.spacecenter.data.models.Planet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Eugene on 23.01.2018.
 */

public class JsonUtils {

        public static List<Planet> getAllSolarObjects(Context context) {

            String json="";

            try {
                InputStream inputStream = context.getAssets().open("solar_system.json");
                Scanner scanner = new Scanner(inputStream);
                scanner.useDelimiter("//A");
                if(scanner.hasNext())
                    json=scanner.next();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(json.isEmpty())
                return null;

            Type listType = new TypeToken<List<Planet>>() {}.getType();

            List<Planet>planets = new Gson().fromJson(json,listType);
            for(Planet planet:planets){
                planet.setImageResId(getImageRes(planet.getId()));
            }

            return planets;
        }

    private static int getImageRes(int id) {

            switch (id){
                case 0:
                    return R.drawable.sun;
                case 1:
                    return R.drawable.mercury;
                case 2:
                    return R.drawable.venus;
                case 3:
                    return R.drawable.earth;
                case 4:
                    return R.drawable.mars;
                case 5:
                    return R.drawable.ceres;
                case 6:
                    return R.drawable.jupiter;
                case 7:
                    return R.drawable.saturn;
                case 8:
                    return R.drawable.uranus;
                case 9:
                    return R.drawable.neptune;
                case 10:
                    return R.drawable.pluto;
                case 11:
                    return R.drawable.makemake;
                case 12:
                    return R.drawable.haumea;
                case 13:
                    return R.drawable.eris;
                default:
                    return R.drawable.sun;
            }

    }

}
