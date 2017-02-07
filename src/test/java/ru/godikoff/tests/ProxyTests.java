package ru.godikoff.tests;

import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import ru.godikoff.objects.YabroObjects;
import ru.godikoff.steps.ProxySteps;
import ru.godikoff.steps.YabroSteps;
import ru.godikoff.utils.AndroidDriverInitializer;
import ru.yandex.qatools.allure.annotations.Title;

public class ProxyTests {
    private AndroidDriver driver;
    private YabroObjects yabroObjects;
    private YabroSteps yabroSteps;
    private ProxySteps proxySteps;


    @Before
    public void before() throws Exception {
        driver = new AndroidDriverInitializer().getDriver();
        yabroObjects = new YabroObjects(driver);
        yabroSteps = new YabroSteps(driver);
        proxySteps = new ProxySteps(driver);
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


    @Title("Проверка текста в хедере")
    @Test
    public void checkReferrerHeader() throws Exception {
        yabroSteps.click(yabroObjects.omniboxInNewTab);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "wikiped");
        yabroSteps.click(yabroObjects.omniNavigationLink);
        yabroSteps.shouldBeInLog("Ya:ReportManager", "url opened");
        proxySteps.containInHeader("wikipedia", "Referer", "android");
    }

    @Title("Проверка цвета More Button")
    @Test
    public void checkMoreButtonColor() throws Exception {
        yabroSteps.openZen();
        yabroSteps.scrollDownTo(yabroObjects.zenView, yabroObjects.iceMoreButton);
        yabroSteps.shouldContainColor(yabroObjects.iceMoreButton,
                proxySteps.getResponseIceMoreButtonColor());
    }
}