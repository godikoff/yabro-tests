package ru.godikoff.utils;

import io.appium.java_client.android.AndroidDriver;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import org.junit.rules.TestWatcher;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.InetAddress;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class AndroidDriverInitializer extends TestWatcher{
    private AndroidDriver driver;
    private List<Request> requestList;

    public AndroidDriverInitializer() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName","aphone");
        capabilities.setCapability("appPackage", "com.yandex.browser");
        capabilities.setCapability("appActivity", ".YandexBrowserMainActivity");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
    }

    public BrowserMobProxy getProxy() throws Exception{
        requestList = new LinkedList<>();
        BrowserMobProxyServer proxy = new BrowserMobProxyServer();
        proxy.addRequestFilter(new RequestFilter() {
            @Override
            public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                synchronized (requestList) {
                    requestList.add(new Request(request, contents, messageInfo));
                }
                return null;
            }
        });

        proxy.start(8888, InetAddress.getLocalHost());

        String ip = InetAddress.getLocalHost().getHostAddress();
        String commandLine = "yandex --proxy-server=" + ip + ":" + proxy.getPort();
        byte[] commandLineBytes = commandLine.getBytes();
        driver.pushFile("/data/local/tmp/yandex-browser-command-line", commandLineBytes);
        return proxy;
    }

    public AndroidDriver getDriver() { return driver; }
    public List<Request> getRequestList() {
        return requestList;
    }
}
