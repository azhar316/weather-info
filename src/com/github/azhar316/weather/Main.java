package com.github.azhar316.weather;

public class Main {
    public static void main(String[] args) {
        String weatherJson = NetworkUtils.getWeatherJsonData();
        double[] weatherValues = JsonUtils.getWeatherValuesFromJson(weatherJson);

        if (weatherValues == null) {
            System.out.println("An error occurred while fetching weather details");
            return;
        }

        System.out.println(weatherValues[JsonUtils.TEMPERATURE_INDEX]);
        System.out.println(weatherValues[JsonUtils.PRESSURE_INDEX]);
        System.out.println(weatherValues[JsonUtils.WIND_SPEED_INDEX]);
    }
}
