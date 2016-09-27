import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.yandex.qatools.allure.annotations.Title;

import java.awt.*;
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
        yabroSteps.browserStart();
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


    @Title("Тап по 3 элементу саджеста и ожидание загрузки страницы")
    @Test
    public void SearchFromSuggestWithSteps() throws Exception {
        yabroSteps.click(yabroObjects.omniboxInNewTab);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "qwe");
        yabroSteps.clickOnSuggest(3);
        yabroSteps.shouldBeInLog("Ya:ReportManager", "url opened");
    }

    @Title("Фейлящийся тап по 3 элементу саджеста и ожидание загрузки страницы")
    @Test
    public void FailSearchFromSuggestWithSteps() throws Exception {
        yabroSteps.click(yabroObjects.omniboxInNewTab);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "qwe");
        yabroSteps.clickOnSuggest(3);
        yabroSteps.shouldBeInLog("Ya:ReportManager", "urlopened");
    }

    @Title("Проверка, что в саджесте больше 1 элемента")
    @Test
    public void CheckCountOfSuggestElements() {
        yabroSteps.click(yabroObjects.omniboxInNewTab);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "qwe");
        yabroSteps.hasMoreElementsThan(yabroObjects.reversedSuggestList, 1);
    }

    @Title("Проверка цвета текста в историческом саджесте")
    @Test
    public void CheckColorOfHistorySuggest() throws Exception {
        Color historySuggestColor1 = new Color(148, 148, 148);
        Color historySuggestColor2 = new Color(86, 28, 140);
        yabroSteps.click(yabroObjects.omniboxInNewTab);
        yabroSteps.inputAndSendText(yabroObjects.omniboxTextField, "cat");
        yabroSteps.click(yabroObjects.omniboxInCurrentTab);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "cat");
        yabroSteps.shouldContainColors(yabroObjects.historySearchSuggest, historySuggestColor1, historySuggestColor2);
    }
}
