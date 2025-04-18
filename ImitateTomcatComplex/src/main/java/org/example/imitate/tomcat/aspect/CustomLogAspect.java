package org.example.imitate.tomcat.aspect;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.example.imitate.tomcat.annotation.CustomLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

public class CustomLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(CustomLogAspect.class);

    public static FullHttpResponse around(Method method, FullHttpRequest request, RequestHandler handler) throws Exception {
        // 检查方法是否有CustomLog注解
        if (method.isAnnotationPresent(CustomLog.class)) {
            CustomLog customLog = method.getAnnotation(CustomLog.class);
            String message = customLog.value().isEmpty() ? "自定义请求" : customLog.value();
            logger.info("{}: {} {}", message, request.method().name(), request.uri());
        }
        
        // 执行实际的处理方法
        return handler.handle();
    }

    @FunctionalInterface
    public interface RequestHandler {
        FullHttpResponse handle() throws Exception;
    }
} 