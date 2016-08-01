import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;


public class BrowserStart {

    private AppiumDriver driver;

    @Before
    public void BeforeTest() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName","aphone");
        capabilities.setCapability("appPackage", "com.yandex.browser");
        capabilities.setCapability("appActivity", ".YandexBrowserActivity");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
    }

    @Test

    public void Test() throws Exception{
        driver.findElement(By.id("bro_sentry_bar_fake"));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

}
