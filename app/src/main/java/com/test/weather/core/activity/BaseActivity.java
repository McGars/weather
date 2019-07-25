package com.test.weather.core.activity;

import com.test.weather.R;
import com.test.weather.core.navigation.Navigator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements Navigator {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
    }

    @Override
    public void loadScreen(final Fragment screen, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        if(addToBackStack) {
            transaction.addToBackStack(screen.getClass().getSimpleName());
        }

        transaction.replace(R.id.container, screen)
                .commit();
    }


}
