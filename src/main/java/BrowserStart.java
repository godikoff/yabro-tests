import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeoutException;


public class BrowserStart {
    private AppiumDriver driver;


    @Before
    public void BeforeTest() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName","aphone");
        capabilities.setCapability("appPackage", "com.yandex.browser");
        capabilities.setCapability("appActivity", ".YandexBrowserMainActivity");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
    }


    @Test
    public void Test() throws Exception{
        WebDriverWait wait = new WebDriverWait(driver, 5);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bro_sentry_bar_fake")));
        }
        catch (Exception s){
            wait.until(ExpectedConditions.elementToBeClickable(By.id("activity_tutorial_close_button"))).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bro_sentry_bar_fake")));
    }

    
    @After
    public void After() throws Exception {
        driver.quit();
    }

}

