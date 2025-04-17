package com.ho.ng;

import com.ho.ng.spi.ServiceProvider;

public class ServiceProviderImplC implements ServiceProvider {
    @Override
    public void execute() {
        System.out.println("ServiceProviderImplC executed.");
    }
}