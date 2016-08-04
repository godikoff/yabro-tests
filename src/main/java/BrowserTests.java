import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class BrowserTests {
    private AppiumDriver driver;


    @Before
    public void Before() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName","aphone");
        capabilities.setCapability("appPackage", "com.yandex.browser");
        capabilities.setCapability("appActivity", ".YandexBrowserMainActivity");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    @Test
    public void BrowserStart() throws Exception{
        try {
            driver.findElement(By.id(("bro_sentry_bar_fake_text")));
        }
        catch (Exception e)
        {
            WebElement tutorialCloseButton = driver.findElement(By.id("activity_tutorial_close_button"));
            tutorialCloseButton.click();
        }
        finally {
            driver.findElement(By.id(("bro_sentry_bar_fake_text")));
        }
    }


    @Test
    public void SearchFromSuggest() throws Exception {
        try {
            driver.findElement(By.id(("bro_sentry_bar_fake_text")));
        }
        catch (Exception e)
        {
            WebElement tutorialCloseButton = driver.findElement(By.id("activity_tutorial_close_button"));
            tutorialCloseButton.click();
        }

        WebElement omnibox = driver.findElement(By.id(("bro_sentry_bar_fake_text")));
        omnibox.click();

        WebElement omniboxTextField = driver.findElement(By.id("bro_sentry_bar_input_edittext"));
        omniboxTextField.sendKeys("qwe");

        List<WebElement> SuggestList = driver.findElements(By.id("bro_common_omnibox_text_layout"));
        SuggestList.get(SuggestList.size()-3).click();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.findElement(By.id("com.yandex.browser:id/bro_omnibox_titlebar_button_reload"));
    }


    @After
    public void After() throws Exception {
        driver.quit();
    }
}
