package com.test.weather.core.navigation;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class NavigationUtil {

    public static void openScreen(Activity activity, Fragment fragment) {
        if (activity instanceof Navigator) {
            ((Navigator) activity).loadScreen(fragment, true);
        }
    }

    public static void openScreen(Activity activity, Fragment fragment, boolean addToBackStack) {
        if (activity instanceof Navigator) {
            ((Navigator) activity).loadScreen(fragment, addToBackStack);
        }
    }

    public static void openLocationSettings(Activity activity) {
        if (activity != null) {
            Intent callGPSSettingIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivity(callGPSSettingIntent);
        }
    }

}
