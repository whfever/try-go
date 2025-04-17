package com.ho.ng.object;

import java.lang.reflect.Constructor;

public class ReflectionExample {
    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("com.ho.ng.object.ReflectionMyClass");
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            Object obj = constructor.newInstance();
            ReflectionMyClass myClass = (ReflectionMyClass) obj;
            myClass.display();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ReflectionMyClass {
    void display() {
        System.out.println("Object created using reflection.");
    }
}