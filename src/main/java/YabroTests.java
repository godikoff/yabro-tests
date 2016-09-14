import com.google.common.collect.Lists;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class YabroTests {
    private AppiumDriver driver;
    private LogReader logReader;
    private YabroObjects yabroObjects;
    private static Date dateTime = new Date();
    private YabroSteps yabroSteps;

    @Before
    public void Before() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName","aphone");
        capabilities.setCapability("appPackage", "com.yandex.browser");
        capabilities.setCapability("appActivity", ".YandexBrowserMainActivity");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        logReader = new LogReader();
        yabroObjects = new YabroObjects(driver);
        yabroSteps = new YabroSteps();
    }


    @Rule
    public TestWatcher testWatcher = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            String dateTimeFormated = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(dateTime);
            String screenshotName = description.getMethodName() + ".png";
            try {
                File scFile = driver.getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scFile, new File("screenshots/" + dateTimeFormated + "/" + screenshotName));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
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


    @Test /* Тап по 3 элементу саджеста и ожидание загрузки */
    public void SearchFromSuggest() throws Exception {
        BrowserStart();
        WebElement omnibox = driver.findElement(By.id(("bro_sentry_bar_fake_text")));
        omnibox.click();
        WebElement omniboxTextField = driver.findElement(By.id("bro_sentry_bar_input_edittext"));
        omniboxTextField.sendKeys("qwe");
        List<WebElement> suggestList = driver.findElements(By.id("bro_common_omnibox_text_layout"));
        Lists.reverse(suggestList).get(2).click();
        logReader.FindString(driver, "Ya:ReportManager", "url opened");
    }


    @Test /* Тап по 3 элементу саджеста и ожидание загрузки c PageObject */
    public void SearchFromSuggestWithPageObject() throws Exception {
        BrowserStart();
        yabroObjects.omnibox.click();
        yabroObjects.omniboxTextField.sendKeys("qwe");
        yabroObjects.suggestList().get(2).click();
        logReader.FindString(driver, "Ya:ReportManager", "url opened");
    }


    @Test /* Фейл тапа по 3 элементу саджеста и ожидания загрузки c PageObject */
    public void FailingSearchFromSuggestWithPageObject() throws Exception {
        BrowserStart();
        yabroObjects.omnibox.click();
        yabroObjects.omniboxTextField.sendKeys("qwe34vsd");
        yabroObjects.suggestList().get(2).click();
        logReader.FindString(driver, "Ya:ReportManager", "url opened");
    }


    @Test /* Тап по 3 элементу саджеста и ожидание загрузки c использованием Step*/
    public void SearchFromSuggestWithSteps() throws Exception {
        BrowserStart();
        yabroSteps.click(yabroObjects.omnibox);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "qwe");
        yabroSteps.clickOnSuggest(yabroObjects.reversedSuggestList, 3);
        yabroSteps.shouldBeInLog(driver, "Ya:ReportManager", "url opened");
    }
}
