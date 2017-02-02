package ru.godikoff.utils;

import io.appium.java_client.android.AndroidDriver;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BrowserProxy {
    private List<Request> requestList;
    private CopyOnWriteArrayList<Response> responseList;
    private BrowserMobProxy proxy;
    private AndroidDriver driver;

    public BrowserProxy() throws Exception {
        AndroidDriverInitializer androidDriverInitializer = new AndroidDriverInitializer();
        driver = androidDriverInitializer.getDriver();
        requestList = new LinkedList<>();
        responseList = new CopyOnWriteArrayList<>();
        proxy = new BrowserMobProxyServer();

        proxy.addRequestFilter(new RequestFilter() {
            @Override
            public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                synchronized (requestList) {
                    Request x = new Request(request, contents, messageInfo);
                    requestList.add(x);
                }
                return null;
            }
        });

        proxy.start(8888, InetAddress.getLocalHost());

        String ip = InetAddress.getLocalHost().getHostAddress();
        String commandLine = "yandex --proxy-server=" + ip + ":" + proxy.getPort();
        byte[] commandLineBytes = commandLine.getBytes();
        driver.pushFile("/data/local/tmp/yandex-browser-command-line", commandLineBytes);
    }

    public BrowserProxy withIceboarding() throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get("zenanswers/iceboarding.json"));
        final String iceboarding = new String(encoded, "UTF-8");
        proxy.addResponseFilter(new ResponseFilter() {
            @Override
            public void filterResponse(HttpResponse httpResponse, HttpMessageContents httpMessageContents, HttpMessageInfo httpMessageInfo) {
                //synchronized (responseList) {
                    Response x = new Response(httpResponse, httpMessageContents, httpMessageInfo);
                    responseList.add(x);
                    if (x.httpMessageInfo.getUrl().contains("api/v2/android/export_ob")) {
                        x.httpMessageContents.setTextContents(iceboarding);
                    }
                //}
            }
        });
        return this;
    }

    public List<Request> getRequestList() {
        return requestList;
    }
    public BrowserMobProxy getProxy() {
        return proxy;
    }
    public CopyOnWriteArrayList<Response> getResponseList() {
        return responseList;
    }
    public AndroidDriver getDriver() {
        return driver;
    }
}
