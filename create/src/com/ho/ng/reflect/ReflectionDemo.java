package com.ho.ng.reflect;

import java.lang.reflect.Method;

public class ReflectionDemo {
    public static void main(String[] args) {
        try {
            // 动态加载类
            Class<?> clazz = Class.forName("com.ho.ng.ServiceProviderImplC");

            // 创建实例
            Object instance = clazz.getDeclaredConstructor().newInstance();

            // 调用方法
            Method method = clazz.getMethod("execute");
            method.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}