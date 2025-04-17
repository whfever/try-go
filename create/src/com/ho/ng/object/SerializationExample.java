package com.ho.ng.object;

import java.io.*;

public class SerializationExample {
    public static void main(String[] args) {
        try {
            // Serialize object
            SerializationMyClass obj1 = new SerializationMyClass("Serialized Object");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.dat"));
            oos.writeObject(obj1);
            oos.close();

            // Deserialize object
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.dat"));
            SerializationMyClass obj2 = (SerializationMyClass) ois.readObject();
            ois.close();

            System.out.println("Deserialized Object: " + obj2.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class SerializationMyClass implements Serializable {
    private String value;

    public SerializationMyClass(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}