package com.test.weather.domain.data.db;

public class WeatherDb {
    public static final String TABLE_NAME = "Weather";

    static final String COLUMN_ID = "id";
    public static final String COLUMN_WEATHER_STRING = "weather_string";
    public static final String COLUMN_TIMESTAMP = "timestamp";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_WEATHER_STRING + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

}
