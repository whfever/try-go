package com.ho.ng.annotations;

import java.lang.annotation.*;
import java.lang.reflect.*;

// Custom annotation for validation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotNull {
    String message() default "Field cannot be null";
}

// Custom annotation for string length validation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Length {
    int min() default 0;
    int max() default Integer.MAX_VALUE;
    String message() default "Field length is out of bounds";
}

// User class with validation annotations
class User {
    @NotNull(message = "Username cannot be null")
    private String username;

    @Length(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

// Validator class to process annotations
class Validator {
    public static void validate(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(obj);

            // Check @NotNull
            if (field.isAnnotationPresent(NotNull.class)) {
                NotNull notNull = field.getAnnotation(NotNull.class);
                if (value == null) {
                    throw new IllegalArgumentException(notNull.message());
                }
            }

            // Check @Length
            if (field.isAnnotationPresent(Length.class)) {
                Length length = field.getAnnotation(Length.class);
                if (value instanceof String) {
                    String strValue = (String) value;
                    if (strValue.length() < length.min() || strValue.length() > length.max()) {
                        throw new IllegalArgumentException(length.message());
                    }
                }
            }
        }
    }
}

public class ValidationExample {
    public static void main(String[] args) {
        try {
            // Valid user
            User validUser = new User("john_doe", "securePass123");
            Validator.validate(validUser);
            System.out.println("Valid user passed validation");

            // Invalid user (null username)
            User invalidUser1 = new User(null, "securePass123");
            Validator.validate(invalidUser1);
        } catch (Exception e) {
            System.err.println("Validation error: " + e.getMessage());
        }

        try {
            // Invalid user (password too short)
            User invalidUser2 = new User("john_doe", "123");
            Validator.validate(invalidUser2);
        } catch (Exception e) {
            System.err.println("Validation error: " + e.getMessage());
        }
    }
}