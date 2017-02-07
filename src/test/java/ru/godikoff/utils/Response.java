package ru.godikoff.utils;

import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

public class Response {
    public HttpResponse httpResponse;
    public HttpMessageContents httpMessageContents;
    public HttpMessageInfo httpMessageInfo;
    private String content;

    public Response(HttpResponse httpResponse, HttpMessageContents httpMessageContents, HttpMessageInfo httpMessageInfo){
        this.httpResponse = httpResponse;
        this.httpMessageContents = httpMessageContents;
        this.httpMessageInfo = httpMessageInfo;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }
}
