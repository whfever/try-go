package com.ho.ng.object;

public class NewKeywordExample {
    public static void main(String[] args) {
        NewKeywordMyClass obj = new NewKeywordMyClass();
        obj.display();
    }
}

class NewKeywordMyClass {
    void display() {
        System.out.println("Object created using new keyword.");
    }
}