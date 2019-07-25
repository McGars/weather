package com.test.weather.core.location;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.test.weather.App;

import static com.test.weather.core.data.CommonConstants.ACTION_LOCATION_UPDATED;
import static com.test.weather.core.data.CommonConstants.EXTRA_LOCATION;

public class UserLocationProvider {
    private static final String LOG_TAG = "UserLocationProvider";

    private LocationTrackingService locationService;
    private boolean shouldUnbind;
    private boolean isServiceReady = false;

    public interface LocationListener {
        void onLocationUpdated(Location location);
    }

    private LocationListener locationListener;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.wtf(LOG_TAG, "onServiceConnected: location service connected");
            locationService = ((LocationTrackingService.LocalBinder) service).getService();
            isServiceReady = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.wtf(LOG_TAG, "onServiceDisconnected: ");
            locationService = null;
            isServiceReady = false;
        }
    };

    public UserLocationProvider() {
        doBindService();
        LocalBroadcastManager.getInstance(App.getInstance()).registerReceiver(
                locationReceiver, new IntentFilter(ACTION_LOCATION_UPDATED));
    }

    private BroadcastReceiver locationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.wtf(LOG_TAG, " BROADCAST RECEIVED: ");

            Location lastLocation = intent.getParcelableExtra(EXTRA_LOCATION);
            locationListener.onLocationUpdated(lastLocation);
        }
    };

    private void doBindService() {
        Log.wtf(LOG_TAG, "doBindService: ");

        if (App.getInstance().bindService(new Intent(App.getInstance(), LocationTrackingService.class),
                serviceConnection, Context.BIND_AUTO_CREATE)) {
            shouldUnbind = true;
        } else {
            Log.e(LOG_TAG, "Error: The requested service doesn't " +
                    "exist, or this client isn't allowed access to it");
        }
    }

    private void doUnbindService() {
        if (shouldUnbind) {
            // Release information about the service's state.
            App.getInstance().unbindService(serviceConnection);
            shouldUnbind = false;
        }
    }

    public void onStop() {
        LocalBroadcastManager.getInstance(App.getInstance())
                             .unregisterReceiver(locationReceiver);
        doUnbindService();
    }

    @Nullable
    public Location getLocationFromService() {
        Log.wtf(LOG_TAG, "getLocationFromService: ... ... ");
        if (locationService != null) {
            return locationService.getLastLocation();
        }

        return null;
    }

    public boolean isServiceReady() {
        Log.wtf(LOG_TAG, "IS SERVICE READY? " + isServiceReady);
        return isServiceReady;
    }

    public void setLocationListener(LocationListener listener) {
        this.locationListener = listener;
    }
}
