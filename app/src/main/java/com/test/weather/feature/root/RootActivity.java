package com.test.weather.feature.root;

import com.test.weather.core.activity.BaseActivity;
import com.test.weather.feature.splash.presentation.view.SplashFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

public class RootActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            loadScreen(SplashFragment.newInstance(), false);
        }

    }
}
