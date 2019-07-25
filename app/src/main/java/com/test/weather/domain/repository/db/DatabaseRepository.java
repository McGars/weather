package com.test.weather.domain.repository.db;

import com.test.weather.domain.data.db.WeatherDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseRepository extends SQLiteOpenHelper {
    private static final String LOG_TAG = "####### DATABASE ######";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "weather_db";

    public DatabaseRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WeatherDb.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WeatherDb.TABLE_NAME);
        onCreate(db);
    }

    public long weather(String weather) {
        Log.wtf(LOG_TAG, "---- INSERT ----: " + weather);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WeatherDb.COLUMN_WEATHER_STRING, weather);
        long id = db.insert(WeatherDb.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public String getLatestWeather() {
        String selectQuery = "SELECT  * FROM " + WeatherDb.TABLE_NAME + " ORDER BY " +
                WeatherDb.COLUMN_TIMESTAMP + " DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String weather = "";
        if (cursor.moveToFirst()) {
            weather = cursor.getString(cursor.getColumnIndex(WeatherDb.COLUMN_WEATHER_STRING));
        }
        cursor.close();
        Log.wtf(LOG_TAG, "---- GET ----: " + weather);
        return weather;
    }

}
