package ru.godikoff.utils;

import io.appium.java_client.android.AndroidDriver;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class AndroidDriverInitializer extends TestWatcher{
    private AndroidDriver driver;

    public AndroidDriverInitializer() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName","aphone");
        capabilities.setCapability("appPackage", "com.yandex.browser");
        capabilities.setCapability("appActivity", ".YandexBrowserMainActivity");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
    }

    public AndroidDriver getDriver() { return driver; }
}
