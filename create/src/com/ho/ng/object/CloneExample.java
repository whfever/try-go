package com.ho.ng.object;

public class CloneExample {
    public static void main(String[] args) {
        try {
            CloneExampleMyClass obj1 = new CloneExampleMyClass();
            obj1.setValue("Original Object");
            CloneExampleMyClass obj2 = (CloneExampleMyClass) obj1.clone();
            obj2.setValue("Cloned Object");

            System.out.println("Object 1: " + obj1.getValue());
            System.out.println("Object 2: " + obj2.getValue());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}

class CloneExampleMyClass implements Cloneable {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        CloneExampleMyClass cloned = (CloneExampleMyClass) super.clone();
        cloned.value = new String(this.value); // Deep copy the value field
        return cloned;
    }
}