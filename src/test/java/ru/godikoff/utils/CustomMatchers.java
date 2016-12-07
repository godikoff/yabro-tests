package ru.godikoff.utils;

import io.appium.java_client.android.AndroidElement;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;

public class CustomMatchers extends AndroidElement {
    public static Matcher<BufferedImage> hasColor(final Color expectedColor) {
        return new FeatureMatcher<BufferedImage, Boolean>(is(true), "Color " + expectedColor, "Color " + expectedColor) {
            @Override
            protected Boolean featureValueOf(BufferedImage elementScreenshot) {
                return findColor(elementScreenshot, expectedColor);
            }
        };
    }

    static boolean findColor(BufferedImage elementScreenshot, Color expectedColor) {
        for (int x = 0; x < elementScreenshot.getWidth(); x++) {
            for (int y = 0; y < elementScreenshot.getHeight(); y++) {
                int rgb = elementScreenshot.getRGB(x, y);
                if (rgb == expectedColor.getRGB()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Matcher<List<Request>> hasInHeaders(
            final String url, final String header, final String expectedValue) {
        return new FeatureMatcher<List<Request>, Boolean>(
                is(true), expectedValue + " in " + header, expectedValue + " in " + header) {
            @Override
            protected Boolean featureValueOf(List<Request> requestList) {
                return findHeader(requestList, url, header, expectedValue);
            }
        };
    }

    static boolean findHeader(List<Request> requestList, String url, String header, String expectedValue) {
        for (Request singleRequest : requestList) {
            if (singleRequest.messageInfo.getUrl().contains(url)) {
                List<Map.Entry<String, String>> headerList = singleRequest.request.headers().entries();
                for (Map.Entry<String, String> singleHeader : headerList) {
                    if (singleHeader.getKey().contains(header) && singleHeader.getValue().contains(expectedValue)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
