package com.ho.ng.object;

public class FactoryMethodExample {
    public static void main(String[] args) {
        FactoryMethodMyClass obj = MyClassFactory.createInstance();
        obj.display();
    }
}

class FactoryMethodMyClass {
    void display() {
        System.out.println("Object created using factory method.");
    }
}

class MyClassFactory {
    public static FactoryMethodMyClass createInstance() {
        return new FactoryMethodMyClass();
    }
}