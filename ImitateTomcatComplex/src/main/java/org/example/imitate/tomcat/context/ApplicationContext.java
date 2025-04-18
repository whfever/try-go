package org.example.imitate.tomcat.context;

import org.example.imitate.tomcat.annotation.Controller;
import org.example.imitate.tomcat.controller.BaseController;
import org.example.imitate.tomcat.handler.RequestMappingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApplicationContext {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationContext.class);
    private final RequestMappingHandler requestMappingHandler;
    private final String basePackage;

    public ApplicationContext(String basePackage) {
        this.basePackage = basePackage;
        this.requestMappingHandler = new RequestMappingHandler();
        scanControllers();
    }

    private void scanControllers() {
        try {
            String packagePath = basePackage.replace('.', '/');
            URL resource = Thread.currentThread().getContextClassLoader().getResource(packagePath);
            if (resource == null) {
                logger.error("找不到包路径: {}", packagePath);
                return;
            }

            File packageDir = new File(resource.getFile());
            if (!packageDir.exists()) {
                logger.error("包目录不存在: {}", packageDir.getAbsolutePath());
                return;
            }

            List<Class<?>> controllerClasses = findControllerClasses(packageDir, basePackage);
            for (Class<?> clazz : controllerClasses) {
                try {
                    BaseController controller = (BaseController) clazz.getDeclaredConstructor().newInstance();
                    requestMappingHandler.registerController(controller);
                    logger.info("注册控制器: {}", clazz.getName());
                } catch (Exception e) {
                    logger.error("实例化控制器失败: {}", clazz.getName(), e);
                }
            }
        } catch (Exception e) {
            logger.error("扫描控制器失败", e);
        }
    }

    private List<Class<?>> findControllerClasses(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return classes;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findControllerClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                try {
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(Controller.class)) {
                        classes.add(clazz);
                    }
                } catch (ClassNotFoundException e) {
                    logger.error("加载类失败: {}", className, e);
                }
            }
        }
        return classes;
    }

    public RequestMappingHandler getRequestMappingHandler() {
        return requestMappingHandler;
    }
} 