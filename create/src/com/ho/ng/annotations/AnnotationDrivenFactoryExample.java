package com.ho.ng.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

// Custom annotation to mark services
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Service {
    String name();
}

// Renaming the interface to avoid name conflict
interface IService {
    void execute();
}

// Updating the service implementations to use the new interface
@Service(name = "OrderService")
class OrderService implements IService {
    @Override
    public void execute() {
        System.out.println("Executing Order Service");
    }
}

@Service(name = "NotificationService")
class NotificationService implements IService {
    @Override
    public void execute() {
        System.out.println("Executing Notification Service");
    }
}

// Updating the factory to use the new interface
class ServiceFactory {
    private static final Map<String, IService> services = new HashMap<>();

    static {
        // Register services dynamically
        for (Class<?> clazz : new Class<?>[]{OrderService.class, NotificationService.class}) {
            if (clazz.isAnnotationPresent(Service.class)) {
                Service annotation = clazz.getAnnotation(Service.class);
                try {
                    services.put(annotation.name(), (IService) clazz.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    throw new RuntimeException("Failed to initialize service: " + clazz.getName(), e);
                }
            }
        }
    }

    public static IService getService(String name) {
        return services.get(name);
    }
}

public class AnnotationDrivenFactoryExample {
    public static void main(String[] args) {
        // Get and execute OrderService
        IService orderService = ServiceFactory.getService("OrderService");
        if (orderService != null) {
            orderService.execute();
        } else {
            System.out.println("OrderService not found");
        }

        // Get and execute NotificationService
        IService notificationService = ServiceFactory.getService("NotificationService");
        if (notificationService != null) {
            notificationService.execute();
        } else {
            System.out.println("NotificationService not found");
        }
    }
}