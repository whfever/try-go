package org.example.imitate.tomcat.router;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface Route {
    boolean matches(String path);
    FullHttpResponse handle(FullHttpRequest request);
} 