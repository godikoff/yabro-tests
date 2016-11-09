package ru.godikoff.tests;

import io.appium.java_client.android.AndroidDriver;
import net.lightbody.bmp.BrowserMobProxy;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import ru.godikoff.utils.AndroidDriverRules;
import ru.godikoff.steps.ProxySteps;
import ru.godikoff.objects.YabroObjects;
import ru.godikoff.steps.YabroSteps;
import ru.yandex.qatools.allure.annotations.Title;

public class ProxyTests {
    private AndroidDriver driver;
    private BrowserMobProxy proxy;
    private YabroObjects yabroObjects;
    private YabroSteps yabroSteps;
    private ProxySteps proxySteps;

    @Before
    public void before() throws Exception{
        AndroidDriverRules androidDriverRules = new AndroidDriverRules();
        proxy = androidDriverRules.getProxy();
        driver = androidDriverRules.getDriver();
        yabroObjects = new YabroObjects(driver);
        yabroSteps = new YabroSteps(driver);
        proxySteps = new ProxySteps(proxy);
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
            proxy.stop();
        }
    };


    @Title("Проверка текста в хедере")
    @Test
    public void checkReferrerHeader() throws Exception {
        yabroSteps.click(yabroObjects.omniboxInNewTab);
        yabroSteps.inputText(yabroObjects.omniboxTextField, "wikip");
        yabroSteps.click(yabroObjects.omniNavigationLink);
        yabroSteps.shouldBeInLog("Ya:ReportManager", "url opened");
        proxySteps.shouldContainTextInHeader("wikipedia", "Referer", "android");
    }
}
