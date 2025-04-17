package org.example.learn.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;
import org.apache.catalina.valves.AccessLogValve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws LifecycleException {
        // 创建 Tomcat 实例
        Tomcat tomcat = new Tomcat();
        
        // 设置基础目录
        String baseDir = System.getProperty("java.io.tmpdir");
        logger.info("Tomcat 基础目录: {}", baseDir);
        tomcat.setBaseDir(baseDir);
        
        // 配置连接器
        Connector connector = new Connector("HTTP/1.1");
        connector.setPort(8080);
        tomcat.setConnector(connector);
        
        // 创建默认上下文
        Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
        
        // 配置访问日志
        AccessLogValve accessLogValve = new AccessLogValve();
        accessLogValve.setDirectory(baseDir + "/logs");
        accessLogValve.setPattern("%h %l %u %t \"%r\" %s %b");
        accessLogValve.setSuffix(".txt");
        ctx.getPipeline().addValve(accessLogValve);

        // 启动 Tomcat
        logger.info("正在启动 Tomcat 服务器...");
        tomcat.start();
        logger.info("Tomcat 服务器已启动，监听端口: {}", connector.getPort());
        
        // 等待服务器关闭
        tomcat.getServer().await();
    }
} 