package org.example.ssm;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {
    public static void main(String[] args) throws Exception {
        // 创建Tomcat实例
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(9090);

        // 设置基础目录
        String userDir = System.getProperty("user.dir");
        String tomcatBaseDir = userDir + File.separator + "tomcat";
        createDirectoryIfNotExists(tomcatBaseDir);
        tomcat.setBaseDir(tomcatBaseDir);

        // 获取项目路径
        String webappDirLocation = userDir + File.separator + "src/main/webapp";
        createDirectoryIfNotExists(webappDirLocation);
        System.out.println("配置应用上下文: " + webappDirLocation);

        // 配置Context
        Context ctx = tomcat.addWebapp("", webappDirLocation);

        // 确保classes目录存在
        String classesPath = userDir + File.separator + "target/classes";
        createDirectoryIfNotExists(classesPath);
        System.out.println("类文件路径: " + classesPath);

        // 设置编译后的class文件位置
        File additionWebInfClasses = new File(classesPath);
        WebResourceRoot resources = new StandardRoot((StandardContext) ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ((StandardContext) ctx).setResources(resources);

        // 配置Spring上下文参数
        ctx.addParameter("contextConfigLocation", "classpath:applicationContext.xml");
        ctx.addApplicationListener(ContextLoaderListener.class.getName());

        // 配置Spring MVC
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setContextConfigLocation("classpath:spring-mvc.xml");
        Tomcat.addServlet(ctx, "dispatcher", dispatcherServlet);
        ctx.addServletMappingDecoded("/*", "dispatcher");

        // 添加一个默认的Connector
        tomcat.getConnector();

        // 启动Tomcat
        tomcat.start();
        System.out.println("应用已启动: http://localhost:9090");
        tomcat.getServer().await();
    }

    private static void createDirectoryIfNotExists(String dir) throws IOException {
        Path path = Paths.get(dir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
            System.out.println("创建目录: " + dir);
        }
    }
}
