package com.test.weather.domain.repository.location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

public class LocationPermissionRepository {

    private static final String REQUIRED_LOCATION_PERMISSION
            = Manifest.permission.ACCESS_FINE_LOCATION;

    private Context context;

    public LocationPermissionRepository(Context context) {
        this.context = context;
    }

    public boolean isLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(context, REQUIRED_LOCATION_PERMISSION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public boolean onRequestPermissionsResult(@NonNull int[] grantResults) {
        return grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

}
