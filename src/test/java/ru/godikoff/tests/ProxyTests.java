package ru.godikoff.tests;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import ru.godikoff.utils.BrowserProxy;
import ru.godikoff.utils.Request;
import ru.godikoff.utils.Response;
import ru.yandex.qatools.allure.annotations.Title;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProxyTests {
    private AndroidDriver driver;
    private BrowserMobProxy proxy;
    private YabroObjects yabroObjects;
    private YabroSteps yabroSteps;
    private ProxySteps proxySteps;
    private List<Request> requestList;
    private CopyOnWriteArrayList<Response> responseList;


    @Before
    public void before() throws Exception {
        BrowserProxy browserProxy = new BrowserProxy().withIceboarding();
        proxy = browserProxy.getProxy();
        driver = browserProxy.getDriver();
        requestList = browserProxy.getRequestList();
        responseList = browserProxy.getResponseList();
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
        yabroSteps.inputText(yabroObjects.omniboxTextField, "wikiped");
        yabroSteps.click(yabroObjects.omniNavigationLink);
        yabroSteps.shouldBeInLog("Ya:ReportManager", "url opened");
        proxySteps.containInHeader("wikipedia", "Referer", "android");
    }

    @Title("Проверка цвета More Button")
    @Test
    public void checkMoreButtonColor() throws Exception {
        //Color iceMoreButtonColor = new Color(0, 0, 255);
        yabroSteps.openZen();
        yabroSteps.scrollDownTo(yabroObjects.zenView, yabroObjects.iceMoreButton);
        for (Response x : responseList) {
            if (x.httpMessageInfo.getUrl().contains("api/v2/android/export_ob")) {
                JsonParser parser = new JsonParser();
                JsonObject mainObject = parser.parse(x.httpMessageContents.getTextContents()).getAsJsonObject();
                JsonArray moreButton = mainObject.getAsJsonArray("more_button");
            }
        }
        //yabroSteps.shouldContainColor(yabroObjects.iceMoreButton, iceMoreButtonColor);
    }
}