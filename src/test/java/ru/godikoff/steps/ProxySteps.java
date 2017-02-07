package ru.godikoff.steps;

import com.google.gson.JsonParser;
import io.appium.java_client.android.AndroidDriver;
import net.lightbody.bmp.BrowserMobProxy;
import ru.godikoff.utils.BrowserProxy;
import ru.godikoff.utils.CustomMatchers;
import ru.godikoff.utils.Request;
import ru.godikoff.utils.Response;
import ru.yandex.qatools.allure.annotations.Step;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.MatcherAssert.assertThat;

public class ProxySteps {
    private AndroidDriver driver;
    private BrowserMobProxy proxy;
    private List<Request> requestList;
    private CopyOnWriteArrayList<Response> responseList;
    private String content;


    public ProxySteps(AndroidDriver driver) throws Exception {
        this.driver = driver;
        BrowserProxy browserProxy = new BrowserProxy(driver).withIceboarding();
        proxy = browserProxy.getProxy();
        responseList = browserProxy.getResponseList();
        requestList = browserProxy.getRequestList();
    }

    @Step("Request to: {0} should contain text: {2} in Header: {1}")
    public void containInHeader(String url, String header, String expectedValue) throws Exception {
        assertThat(expectedValue + " not found in Header " + header, requestList,
                CustomMatchers.hasInHeaders(url, header, expectedValue));
    }

    @Step("Get color of iceboarding More Button from Response")
    public Color getResponseIceMoreButtonColor(){
        for (Response x : responseList) {
            if (x.httpMessageInfo.getUrl().contains("/api/v2/android/export_ob/")) {
                content = x.getContent();
            }
        }

        JsonParser parser = new JsonParser();
        return new Color(Color.decode(parser
                .parse(content)
                .getAsJsonObject()
                .getAsJsonArray("iceboarding_items")
                .get(0)
                .getAsJsonObject()
                .getAsJsonObject("more_button")
                .get("background_color")
                .getAsString())
                .getRGB());
    }
}
