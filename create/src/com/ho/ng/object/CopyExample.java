package com.ho.ng.object;

// A class to demonstrate deep and shallow copy
class Address {
    String city;
    String country;

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }
}

class Person implements Cloneable {
    String name;
    int age;
    Address address;

    public Person(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    // Shallow copy
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // Deep copy
    public Person deepCopy() {
        Address newAddress = new Address(this.address.city, this.address.country);
        return new Person(this.name, this.age, newAddress);
    }
}

public class CopyExample {
    public static void main(String[] args) throws CloneNotSupportedException {
        Address address = new Address("New York", "USA");
        Person original = new Person("John", 25, address);

        // Shallow copy
        Person shallowCopy = (Person) original.clone();

        // Deep copy
        Person deepCopy = original.deepCopy();

        // Modify the original object's address
        original.address.city = "Los Angeles";

        System.out.println("Original Address: " + original.address.city);
        System.out.println("Shallow Copy Address: " + shallowCopy.address.city);
        System.out.println("Deep Copy Address: " + deepCopy.address.city);
    }
}