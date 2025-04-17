package org.example.imitate.tomcat.router;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Router {
    private static final Logger logger = LoggerFactory.getLogger(Router.class);
    private final List<Route> routes = new ArrayList<>();

    public void addRoute(Route route) {
        routes.add(route);
    }

    public FullHttpResponse route(FullHttpRequest request) {
        String path = request.uri();
        logger.info("路由请求: {}", path);
        
        for (Route route : routes) {
            if (route.matches(path)) {
                return route.handle(request);
            }
        }
        
        // 默认路由
        return new SimpleRoute("/", "404 Not Found", "text/plain").handle(request);
    }
} 