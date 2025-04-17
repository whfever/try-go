package com.ho.ng.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// Define an interface
interface Service {
    void performTask();
}

// Implement the interface
class RealService implements Service {
    @Override
    public void performTask() {
        System.out.println("Performing the real task.");
    }
}

// Create a dynamic proxy handler
class DynamicProxyHandler implements InvocationHandler {
    private final Object target;

    public DynamicProxyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before method: " + method.getName());
        Object result = method.invoke(target, args);
        System.out.println("After method: " + method.getName());
        return result;
    }
}

public class DynamicProxyExample {
    public static void main(String[] args) {
        // Create the real service
        Service realService = new RealService();

        // Create the proxy instance
        Service proxyInstance = (Service) Proxy.newProxyInstance(
            realService.getClass().getClassLoader(),
            realService.getClass().getInterfaces(),
            new DynamicProxyHandler(realService)
        );

        // Use the proxy instance
        proxyInstance.performTask();
    }
}