package ru.godikoff.tests;

import io.appium.java_client.android.AndroidDriver;
import net.lightbody.bmp.BrowserMobProxy;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import ru.godikoff.objects.YabroObjects;
import ru.godikoff.steps.ProxySteps;
import ru.godikoff.steps.YabroSteps;
import ru.godikoff.utils.AndroidDriverInitializer;
import ru.godikoff.utils.Request;
import ru.yandex.qatools.allure.annotations.Title;

import java.util.List;

public class ProxyTests {
    private AndroidDriver driver;
    private BrowserMobProxy proxy;
    private YabroObjects yabroObjects;
    private YabroSteps yabroSteps;
    private ProxySteps proxySteps;
    private List<Request> requestList;

    @Before
    public void before() throws Exception{
        AndroidDriverInitializer androidDriverInitializer = new AndroidDriverInitializer();
        proxy = androidDriverInitializer.getProxy();
        driver = androidDriverInitializer.getDriver();
        requestList = androidDriverInitializer.getRequestList();
        yabroObjects = new YabroObjects(driver);
        yabroSteps = new YabroSteps(driver);
        proxySteps = new ProxySteps(proxy, requestList);
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
            proxy.stop();
            driver.quit();
        }
    };


    @Title("Проверка текста в хедере")
    @Test
    public void checkReferrerHeader() throws Exception {
        yabroSteps.click(yabroObjects.omniboxInNewTab);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "wikipe");
        yabroSteps.click(yabroObjects.omniNavigationLink);
        yabroSteps.shouldBeInLog("Ya:ReportManager", "url opened");
        proxySteps.containInHeader("wikipedia", "Referer", "android");
    }
}
