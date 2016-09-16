import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Title;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class YabroTests {
    private AppiumDriver driver;
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
                saveScreenshot(scFile);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        @Attachment
        public byte[] saveScreenshot(File scFile) throws IOException {
            Path path = Paths.get(String.valueOf(scFile));
            byte[] data = Files.readAllBytes(path);
            return data;
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
        yabroSteps.clickOnSuggest(yabroObjects.reversedSuggestList, 3);
        yabroSteps.shouldBeInLog(driver, "Ya:ReportManager", "url opened");
    }

    @Title("Фейлящийся тап по 3 элементу саджеста и ожидание загрузки страницы")
    @Test
    public void FailingSearchFromSuggestWithWithSteps() throws Exception {
        BrowserStart();
        yabroSteps.click(yabroObjects.omnibox);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "qwe");
        yabroSteps.clickOnSuggest(yabroObjects.reversedSuggestList, 3);
        yabroSteps.shouldBeInLog(driver, "Ya:ReportManager", "urlopened");
    }
}
