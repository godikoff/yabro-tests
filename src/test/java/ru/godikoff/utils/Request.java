package ru.godikoff.utils;

import io.netty.handler.codec.http.HttpRequest;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

public class Request {
    HttpRequest request;
    HttpMessageContents contents;
    HttpMessageInfo messageInfo;

    public Request(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo){
        this.request = request;
        this.contents = contents;
        this.messageInfo = messageInfo;
    }
}
