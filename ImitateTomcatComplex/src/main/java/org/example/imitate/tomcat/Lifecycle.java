package org.example.imitate.tomcat;

public interface Lifecycle {
    void start() throws LifecycleException;
    void stop() throws LifecycleException;
    void addLifecycleListener(LifecycleListener listener);
    void removeLifecycleListener(LifecycleListener listener);
    LifecycleState getState();
} 