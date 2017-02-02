package ru.godikoff.utils;

import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

public class Response {
    public HttpResponse httpResponse;
    public HttpMessageContents httpMessageContents;
    public HttpMessageInfo httpMessageInfo;

    public Response(HttpResponse httpResponse, HttpMessageContents httpMessageContents, HttpMessageInfo httpMessageInfo){
        this.httpResponse = httpResponse;
        this.httpMessageContents = httpMessageContents;
        this.httpMessageInfo = httpMessageInfo;
    }
}
