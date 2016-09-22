import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.yandex.qatools.allure.annotations.Title;

import java.net.URL;
import java.util.concurrent.TimeUnit;


public class YabroTests {
    private AppiumDriver driver;
    private YabroObjects yabroObjects;
    private YabroSteps yabroSteps;

    @Before
    public void Before() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName","aphone");
        capabilities.setCapability("appPackage", "com.yandex.browser");
        capabilities.setCapability("appActivity", ".YandexBrowserMainActivity");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        yabroObjects = new YabroObjects(driver);
        yabroSteps = new YabroSteps(driver);
    }


    @Rule
    public TestWatcher testWatcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            yabroSteps.saveScreenshot();
        }

        @Override
        protected void finished(Description description) {
            driver.quit();
        }
    };


    @Test /* Запуск браузера */
    public void BrowserStart() throws Exception{
        try {
            yabroObjects.omnibox.isDisplayed();
        }
        catch (Exception e)
        {
            try {
                yabroObjects.tutorialCloseButton.isDisplayed();
            }
            catch (Exception e1) {
                yabroObjects.welcomeScreenCheckbox.click();
                yabroObjects.welcomeScreenNextButton.click();
            }
            finally {
                yabroObjects.tutorialCloseButton.click();
            }
        }
        finally {
            yabroObjects.omnibox.isDisplayed();
        }
    }


    @Title("Тап по 3 элементу саджеста и ожидание загрузки страницы")
    @Test
    public void SearchFromSuggestWithSteps() throws Exception {
        BrowserStart();
        yabroSteps.click(yabroObjects.omnibox);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "qwe");
        yabroSteps.clickOnSuggest(3);
        yabroSteps.shouldBeInLog("Ya:ReportManager", "url opened");
    }

    @Title("Фейлящийся тап по 3 элементу саджеста и ожидание загрузки страницы")
    @Test
    public void FailSearchFromSuggestWithSteps() throws Exception {
        BrowserStart();
        yabroSteps.click(yabroObjects.omnibox);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "qwe");
        yabroSteps.clickOnSuggest(3);
        yabroSteps.shouldBeInLog("Ya:ReportManager", "urlopened");
    }
}
