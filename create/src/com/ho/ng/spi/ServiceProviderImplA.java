package com.ho.ng.spi;

public class ServiceProviderImplA implements ServiceProvider {
    @Override
    public void execute() {
        System.out.println("ServiceProviderImplA executed.");
    }
}