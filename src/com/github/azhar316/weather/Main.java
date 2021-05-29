package com.github.azhar316.weather;

public class Main {
    public static void main(String[] args) {
        String jsonResponse = NetworkUtils.getWeatherJsonData();
        System.out.println(jsonResponse);
    }
}
