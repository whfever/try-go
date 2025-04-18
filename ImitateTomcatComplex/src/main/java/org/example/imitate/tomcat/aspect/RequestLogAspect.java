package org.example.imitate.tomcat.aspect;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.example.imitate.tomcat.annotation.LogRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;

public class RequestLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);

    public static FullHttpResponse around(Method method, FullHttpRequest request, RequestHandler handler) throws Exception {
        Instant start = Instant.now();
        String path = request.uri();
        String methodName = request.method().name();
        
        try {
            // 记录请求开始日志
            logger.info("开始处理请求: {} {} | 处理器: {}.{}", 
                methodName, path, 
                method.getDeclaringClass().getSimpleName(), 
                method.getName());
            
            // 执行实际的处理方法
            FullHttpResponse response = handler.handle();
            
            // 计算处理时间
            Duration duration = Duration.between(start, Instant.now());
            
            // 记录请求完成日志
            logger.info("请求处理完成: {} {} | 耗时: {}ms | 状态码: {}", 
                methodName, path,
                duration.toMillis(),
                response.status().code());
            
            return response;
        } catch (Exception e) {
            // 记录错误日志
            logger.error("请求处理失败: {} {} | 错误: {}", 
                methodName, path, 
                e.getMessage(), e);
            throw e;
        }
    }

    @FunctionalInterface
    public interface RequestHandler {
        FullHttpResponse handle() throws Exception;
    }
} 