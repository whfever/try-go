package com.ho.ng;

import com.ho.ng.spi.ServiceProvider;
import java.util.ServiceLoader;

public class SPIDemo {
    public static void main(String[] args) {
        ServiceLoader<ServiceProvider> serviceLoader = ServiceLoader.load(ServiceProvider.class);
        for (ServiceProvider provider : serviceLoader) {
            provider.execute();
        }
    }
}