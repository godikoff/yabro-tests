import com.google.common.collect.Lists;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.junit.After;
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


public class BrowserTests {
    private AppiumDriver driver;
    private LogReader logReader;
    private YabroObjects yabroObjects;
    private static Date dateTime = new Date();

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
        // Поиск омнибокса
        try {
            yabroObjects.omnibox.isDisplayed();
        }
        catch (Exception e)
        {
            // Если онибокс не найден, поиск кнопки закрытия туториала
            try {
                yabroObjects.tutorialCloseButton.isDisplayed();
            }
            // Если кнопка закрытия туториала не найдена, прохождение велком скрина
            catch (Exception e1) {
                yabroObjects.welcomeScreenCheckbox.click();
                yabroObjects.welcomeScreenNextButton.click();
            }
            // Тап на кнопку закрытия туториала
            finally {
                yabroObjects.tutorialCloseButton.click();
            }
        }
        // Поиск омнибокса
        finally {
            yabroObjects.omnibox.isDisplayed();
        }
    }


    @Test /* Тап по 3 элементу саджеста и ожидание загрузки */
    public void SearchFromSuggest() throws Exception {
        // Старт браузера
        BrowserStart();
        // Тап в омнибокс
        WebElement omnibox = driver.findElement(By.id(("bro_sentry_bar_fake_text")));
        omnibox.click();
        // Ввод в онибокс строки
        WebElement omniboxTextField = driver.findElement(By.id("bro_sentry_bar_input_edittext"));
        omniboxTextField.sendKeys("qwe");
        // Получение списка элементов саджеста и тап по 3 строке
        List<WebElement> suggestList = driver.findElements(By.id("bro_common_omnibox_text_layout"));
        Lists.reverse(suggestList).get(2).click();
        // Вызов парсера логов (с передачей в него триггера, после которого будут получены логи)
        WebElement webView = driver.findElement(By.className("android.webkit.WebView"));
        logReader.FindString(driver, "Ya:ReportManager", "url opened", webView);
    }


    @Test /* Тап по 3 элементу саджеста и ожидание загрузки c PageObject */
    public void SearchFromSuggestWithPageObject() throws Exception {
        // Старт браузера
        BrowserStart();
        // Тап в омнибокс
        yabroObjects.omnibox.click();
        // Ввод в онибокс строки
        yabroObjects.omniboxTextField.sendKeys("qwe");
        // Получение списка элементов саджеста и тап по 3 строке
        yabroObjects.suggestList().get(2).click();
        // Вызов парсера логов (с передачей в него триггера, после которого будут получены логи)
        logReader.FindString(driver, "Ya:ReportManager", "url opened", yabroObjects.webView);
    }


    @Test /* Фейл тапа по 3 элементу саджеста и ожидания загрузки c PageObject */
    public void FailingSearchFromSuggestWithPageObject() throws Exception {
        // Старт браузера
        BrowserStart();
        // Тап в омнибокс
        yabroObjects.omnibox.click();
        // Ввод в онибокс строки
        yabroObjects.omniboxTextField.sendKeys("qwe34vsd");
        // Получение списка элементов саджеста и тап по 3 строке
        yabroObjects.suggestList().get(2).click();
        // Вызов парсера логов (с передачей в него триггера, после которого будут получены логи)
        logReader.FindString(driver, "Ya:ReportManager", "url opened", yabroObjects.webView);
    }

}
