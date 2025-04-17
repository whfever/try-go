package org.example.imitate.tomcat.router;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import java.nio.charset.StandardCharsets;

public class SimpleRoute implements Route {
    private final String path;
    private final String response;
    private final String contentType;

    public SimpleRoute(String path, String response, String contentType) {
        this.path = path;
        this.response = response;
        this.contentType = contentType;
    }

    @Override
    public boolean matches(String path) {
        return this.path.equals(path);
    }

    @Override
    public FullHttpResponse handle(FullHttpRequest request) {
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1,
            HttpResponseStatus.OK,
            Unpooled.wrappedBuffer(response.getBytes(StandardCharsets.UTF_8))
        );
        
        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType + "; charset=utf-8");
        httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, httpResponse.content().readableBytes());
        
        return httpResponse;
    }
} 