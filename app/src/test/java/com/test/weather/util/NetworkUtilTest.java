package com.test.weather.util;

import com.test.weather.core.network.NetworkUtil;

import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtilTest {

    @Test
    public void givenCoordinates_UrlGetterReturnsValid() {
        String validUrlStr = "https://api.openweathermap.org/data/2.5/weather?lat=56.327530&lon=44.000717&units=metric&APPID=ed3724d4850e07c530135c5488eba79c";
        try {
            URL validUrl = new URL(validUrlStr);
            Assert.assertEquals(validUrl.toString(), NetworkUtil.getLatLonRequestUrl("56.327530", "44.000717").toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
