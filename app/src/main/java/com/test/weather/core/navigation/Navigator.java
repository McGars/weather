package com.test.weather.core.navigation;

import android.support.v4.app.Fragment;

public interface Navigator {

    void loadScreen(Fragment page, boolean addToBackStack);

}