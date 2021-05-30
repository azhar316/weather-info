package com.github.azhar316.weather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    /* input codes */
    private static final int EXIT_CODE = 0;
    private static final int TEMP_CODE = 1;
    private static final int WIND_SPEED_CODE = 2;
    private static final int PRESSURE_CODE = 3;

    private static final String INPUT_DATE_AND_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    private static void printInstructions() {
        System.out.println("Input 1 for Temperature");
        System.out.println("Input 2 for Wind Speed");
        System.out.println("Input 3 for Pressure");
        System.out.println("Input 0 for terminating the program");
    }

    public static void main(String[] args) {
        // API response is cached and will only be requested once for the entire
        // duration of the program
        String weatherJson = NetworkUtils.getWeatherJsonData();
        if (weatherJson == null) return;

        printInstructions();

        // input date and time format
        SimpleDateFormat dateFormat = new SimpleDateFormat(INPUT_DATE_AND_TIME_FORMAT);

        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\n");
        int inputCode = -1;

        while (true) {

            System.out.println("Enter input option");
            if (scanner.hasNext()) {
                inputCode= scanner.nextInt();
            }

            if (inputCode == EXIT_CODE) break;

            System.out.println("Enter date and time as " + INPUT_DATE_AND_TIME_FORMAT);
            String dateString = null;

            if (scanner.hasNext()) {
                dateString = scanner.next();
            }
            Date dateAndTime = null;
            try {
                dateAndTime = dateFormat.parse(dateString);
            } catch (ParseException e) {
                System.out.println("Invalid date format.");
                continue;
            }

            double[] weatherValues = JsonUtils.getWeatherValuesFromJson(weatherJson, dateAndTime);
            if (weatherValues == null) {
                System.out.println("input date is out of range");
                continue;
            }

            switch (inputCode) {
                case TEMP_CODE:
                    System.out.println(weatherValues[JsonUtils.TEMPERATURE_INDEX]);
                    break;
                case WIND_SPEED_CODE:
                    System.out.println(weatherValues[JsonUtils.WIND_SPEED_INDEX]);
                    break;
                case PRESSURE_CODE:
                    System.out.println(weatherValues[JsonUtils.PRESSURE_INDEX]);
                    break;
                default:
                    System.out.println("Invalid option was entered. Try again.");
                    break;
            }
        }
    }
}
