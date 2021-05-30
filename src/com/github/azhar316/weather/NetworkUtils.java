package com.github.azhar316.weather;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    /* API url with API Key to fetch weather forecast details */
    private static final String API_URL =
            "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";

    public static String getWeatherJsonData() {
        try {
            URL url = buildUrlFromString(API_URL);
            String jsonResponse = getResponseFromHttpUrl(url);
            return jsonResponse;
        } catch (IOException e) {
            System.out.println("An error occurred while establishing a connection to the API");
            System.out.println("Check your internet connection and try again.");
            return null;
        }
    }


    private static URL buildUrlFromString(String  urlString) {
        try {
            URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private  static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        InputStream in = urlConnection.getInputStream();

        // InputStream can be read in many ways but one of the best ways i
        // to use scanner.
        Scanner scanner = new Scanner(in);

        // using \A delimiter forces scanner to read the entire the input in
        // a single token
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
