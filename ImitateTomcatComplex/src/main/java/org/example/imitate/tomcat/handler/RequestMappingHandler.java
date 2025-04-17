package org.example.imitate.tomcat.handler;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import org.example.imitate.tomcat.annotation.Controller;
import org.example.imitate.tomcat.annotation.RequestMapping;
import org.example.imitate.tomcat.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestMappingHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestMappingHandler.class);
    private final Map<String, Method> handlerMethods = new HashMap<>();
    private final Map<String, BaseController> controllers = new HashMap<>();

    public void registerController(BaseController controller) {
        Class<?> clazz = controller.getClass();
        if (!clazz.isAnnotationPresent(Controller.class)) {
            return;
        }

        String basePath = "";
        if (clazz.isAnnotationPresent(RequestMapping.class)) {
            basePath = clazz.getAnnotation(RequestMapping.class).value();
        }

        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                String path = basePath + mapping.value();
                handlerMethods.put(path, method);
                controllers.put(path, controller);
                logger.info("注册处理器: {} -> {}.{}", path, clazz.getSimpleName(), method.getName());
            }
        }
    }

    public FullHttpResponse handleRequest(FullHttpRequest request) {
        String path = request.uri();
        Method method = handlerMethods.get(path);
        BaseController controller = controllers.get(path);

        if (method != null && controller != null) {
            try {
                return (FullHttpResponse) method.invoke(controller, request);
            } catch (Exception e) {
                logger.error("处理请求时发生错误", e);
                return createErrorResponse("Internal Server Error");
            }
        }

        return createErrorResponse("404 Not Found");
    }

    private FullHttpResponse createErrorResponse(String message) {
        FullHttpResponse response = new DefaultFullHttpResponse(
            HttpVersion.HTTP_1_1,
            HttpResponseStatus.NOT_FOUND,
            Unpooled.wrappedBuffer(message.getBytes(StandardCharsets.UTF_8))
        );
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=utf-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }
} 