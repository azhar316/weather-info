package com.github.azhar316.weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonUtils {

    /* API message code */
    private static final String API_MESSAGE_CODE = "cod";

    /* Weather information. Each hour's forecast info is an element of the list array */
    private static final String API_LIST = "list";

    /* Temperature and Pressure are child object of main */
    private static final String API_MAIN = "main";

    /* Temperature for the hour */
    private static final String API_TEMP = "temp";

    /* Pressure for the hour */
    private static final String API_PRESSURE = "pressure";

    /* wind speed if child object of wind */
    private static final String API_WIND = "wind";
    private static final String API_WIND_SPEED = "speed";

    private static final String API_DATE = "dt_txt";

    private static final String API_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final int TEMPERATURE_INDEX = 0;
    public static final int PRESSURE_INDEX = 1;
    public static final int WIND_SPEED_INDEX = 2;

    public static double[] getWeatherValuesFromJson(String jsonString, Date dateAndTime) {
        JSONObject weatherJson = new JSONObject(jsonString);

        // check for error in response
        if (weatherJson.has(API_MESSAGE_CODE)) {
            int code = weatherJson.getInt(API_MESSAGE_CODE);
            if (code != HttpURLConnection.HTTP_OK) return null;
        }

        JSONArray jsonWeatherArray = weatherJson.getJSONArray(API_LIST);
        SimpleDateFormat dateFormat = new SimpleDateFormat(API_DATE_FORMAT);

        double[] weatherValues = null;

        // iterate over every object
        for (int i = 0; i < jsonWeatherArray.length() && weatherValues == null; ++i) {

            JSONObject jsonObject = jsonWeatherArray.getJSONObject(i);
            String currentDateAndTimeString = jsonObject.getString(API_DATE);
            Date currentDateAndTime = null;
            try {
                currentDateAndTime = dateFormat.parse(currentDateAndTimeString);
            } catch (ParseException e) {
                // invalid entry. check other objects
                continue;
            }

            if (!currentDateAndTime.equals(dateAndTime)) continue;

            // temperature and pressure are child objects of object called 'main'
            JSONObject mainObject = jsonObject.getJSONObject(API_MAIN);
            double temperature = mainObject.getDouble(API_TEMP);
            double pressure = mainObject.getDouble(API_PRESSURE);

            // wind speed is child object of object called 'wind'
            JSONObject windObject = jsonObject.getJSONObject(API_WIND);
            double windSpeed = windObject.getDouble(API_WIND_SPEED);

            weatherValues = new double[3];
            weatherValues[TEMPERATURE_INDEX] = temperature;
            weatherValues[PRESSURE_INDEX] = pressure;
            weatherValues[WIND_SPEED_INDEX] = windSpeed;
        }

        return weatherValues;
    }
}
