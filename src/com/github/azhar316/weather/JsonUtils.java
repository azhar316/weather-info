package com.github.azhar316.weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;

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

    public static final int TEMPERATURE_INDEX = 0;
    public static final int PRESSURE_INDEX = 1;
    public static final int WIND_SPEED_INDEX = 2;

    public static double[] getWeatherValuesFromJson(String jsonString) {
        JSONObject weatherJson = new JSONObject(jsonString);

        // check for error in response
        if (weatherJson.has(API_MESSAGE_CODE)) {
            int code = weatherJson.getInt(API_MESSAGE_CODE);
            if (code != HttpURLConnection.HTTP_OK) return null;
        }

        JSONArray jsonWeatherArray = weatherJson.getJSONArray(API_LIST);
        // For now get the first jsonObject
        JSONObject specifiedDayJson = jsonWeatherArray.getJSONObject(0);

        // temperature and pressure are child objects of object called 'main'
        JSONObject mainObject = specifiedDayJson.getJSONObject(API_MAIN);
        double temperature = mainObject.getDouble(API_TEMP);
        double pressure = mainObject.getDouble(API_PRESSURE);

        // wind speed is child object of object called 'wind'
        JSONObject windObject = specifiedDayJson.getJSONObject(API_WIND);
        double windSpeed = windObject.getDouble(API_WIND_SPEED);

        double[] weatherValues = new double[3];
        weatherValues[TEMPERATURE_INDEX] = temperature;
        weatherValues[PRESSURE_INDEX] = pressure;
        weatherValues[WIND_SPEED_INDEX] = windSpeed;

        return weatherValues;
    }
}
