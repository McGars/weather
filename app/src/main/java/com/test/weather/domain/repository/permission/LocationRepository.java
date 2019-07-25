package com.test.weather.domain.repository.permission;

import android.content.Context;
import android.location.LocationManager;

import static android.content.Context.LOCATION_SERVICE;

public class LocationRepository {

    Context context;

    public LocationRepository(Context context) {
        this.context = context;
    }

    public boolean isGpsEnable() {
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}
