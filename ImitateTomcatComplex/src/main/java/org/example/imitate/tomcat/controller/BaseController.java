package org.example.imitate.tomcat.controller;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class BaseController {
    
    protected FullHttpResponse createResponse(String content, String contentType) {
        byte[] bytes = content.getBytes(CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1,
            HttpResponseStatus.OK,
            Unpooled.wrappedBuffer(bytes)
        );
        response.headers().set("Content-Type", contentType);
        response.headers().set("Content-Length", bytes.length);
        return response;
    }
} 