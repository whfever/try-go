package org.example.imitate.tomcat;

public class LifecycleException extends Exception {
    public LifecycleException(String message) {
        super(message);
    }

    public LifecycleException(String message, Throwable cause) {
        super(message, cause);
    }
} 