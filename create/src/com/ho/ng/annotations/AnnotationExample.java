package com.ho.ng.annotations;

import java.lang.annotation.*;
import java.lang.reflect.*;

// Custom annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface LogExecutionTime {
}

// Service class with annotated methods
class BusinessService {
    @LogExecutionTime
    public void processOrder() {
        System.out.println("Processing order...");
        try {
            Thread.sleep(1000); // Simulate processing time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @LogExecutionTime
    public void sendNotification() {
        System.out.println("Sending notification...");
        try {
            Thread.sleep(500); // Simulate processing time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Annotation processor
class AnnotationProcessor {
    public static void processAnnotations(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(LogExecutionTime.class)) {
                long start = System.currentTimeMillis();
                method.invoke(obj);
                long end = System.currentTimeMillis();
                System.out.println("Execution time for " + method.getName() + ": " + (end - start) + "ms");
            }
        }
    }
}

public class AnnotationExample {
    public static void main(String[] args) throws Exception {
        BusinessService service = new BusinessService();
        AnnotationProcessor.processAnnotations(service);
    }
}