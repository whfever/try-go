package main.java.org.example.learn;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import java.io.File;

public class LearnTomcat {
    public static void main(String[] args) {
        try {
            // 创建Tomcat实例
            Tomcat tomcat = new Tomcat();
            
            // 设置端口
            tomcat.setPort(8080);
            
            // 设置基础目录
            String baseDir = System.getProperty("java.io.tmpdir");
            tomcat.setBaseDir(baseDir);
            
            // 添加默认的webapp
            String contextPath = "";
            String docBase = new File(".").getAbsolutePath();
            Context context = tomcat.addWebapp(contextPath, docBase);
            
            // 启动服务器
            tomcat.start();
            
            // 等待请求
            tomcat.getServer().await();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
