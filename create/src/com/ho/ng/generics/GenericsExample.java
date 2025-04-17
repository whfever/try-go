package com.ho.ng.generics;

import java.util.ArrayList;
import java.util.List;

// Generic class
class Box<T> {
    private T item;

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }
}

// Generic interface
interface Repository<T> {
    void add(T item);
    T get(int index);
}

// Implementation of generic interface
class ListRepository<T> implements Repository<T> {
    private List<T> items = new ArrayList<>();

    @Override
    public void add(T item) {
        items.add(item);
    }

    @Override
    public T get(int index) {
        return items.get(index);
    }
}

// Generic method
class Utils {
    public static <T> void printItems(List<T> items) {
        for (T item : items) {
            System.out.println(item);
        }
    }
}

public class GenericsExample {
    public static void main(String[] args) {
        // Using a generic class
        Box<String> stringBox = new Box<>();
        stringBox.setItem("Hello Generics");
        System.out.println("Box contains: " + stringBox.getItem());

        // Using a generic interface
        Repository<Integer> repository = new ListRepository<>();
        repository.add(42);
        repository.add(100);
        System.out.println("Repository item at index 0: " + repository.get(0));

        // Using a generic method
        List<String> names = new ArrayList<>();
        names.add("Alice");
        names.add("Bob");
        Utils.printItems(names);
    }
}