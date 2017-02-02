package ru.godikoff.steps;

import net.lightbody.bmp.BrowserMobProxy;
import ru.godikoff.utils.CustomMatchers;
import ru.godikoff.utils.Request;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class ProxySteps {
    private BrowserMobProxy proxy;
    private List<Request> requestList;

    public ProxySteps(BrowserMobProxy proxy, List<Request> requestList) {
        this.proxy = proxy;
        this.requestList = requestList;
    }

    @Step("Request to: {0} should contain text: {2} in Header: {1}")
    public void containInHeader(String url, String header, String expectedValue) throws Exception {
        assertThat(expectedValue + " not found in Header " + header, requestList,
                CustomMatchers.hasInHeaders(url, header, expectedValue));
    }
}
