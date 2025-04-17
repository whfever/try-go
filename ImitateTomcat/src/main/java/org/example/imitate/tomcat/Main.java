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
        } catch (Exception e) {
            logger.error("服务器启动失败", e);
        }
    }
} 