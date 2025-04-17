package org.example;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;
import org.apache.catalina.valves.AccessLogValve;
import java.io.File;

public class Main {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        
        // 设置基础目录
        String baseDir = System.getProperty("java.io.tmpdir");
        System.out.println("访问日志将保存在: " + baseDir + "/logs");
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

        tomcat.start();
        tomcat.getServer().await();
    }
}