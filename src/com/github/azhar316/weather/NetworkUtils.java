package com.github.azhar316.weather;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String API_URL =
            "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";

    public static String getWeatherJsonData() {
        try {
            URL url = buildUrlFromString(API_URL);
            String jsonResponse = getResponseFromHttpUrl(url);

            return jsonResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static URL buildUrlFromString(String  urlString) {
        try {
            URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        InputStream in = urlConnection.getInputStream();

        Scanner scanner = new Scanner(in);
        scanner.useDelimiter("\\A");

        String response = null;
        if (scanner.hasNext()) {
            response = scanner.next();
        }

        scanner.close();
        urlConnection.disconnect();

        return response;
    }
}
