package org.example.imitate.tomcat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Server server = new Server(8080);
            logger.info("正在启动服务器...");
            server.start();
            
            // 添加关闭钩子
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    logger.info("正在关闭服务器...");
                    server.stop();
                } catch (LifecycleException e) {
                    logger.error("关闭服务器失败", e);
                }
            }));
            
            // 等待服务器关闭
            Thread.currentThread().join();
        } catch (Exception e) {
            logger.error("服务器启动失败", e);
        }
    }
} 